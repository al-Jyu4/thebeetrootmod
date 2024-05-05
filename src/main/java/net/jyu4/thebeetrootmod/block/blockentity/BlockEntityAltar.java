package net.jyu4.thebeetrootmod.block.blockentity;

import net.jyu4.thebeetrootmod.gui.AltarMenu;
import net.jyu4.thebeetrootmod.recipe.AltarRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class BlockEntityAltar extends BlockEntity implements MenuProvider {
    private static final Component CONTAINER_TITLE = Component.translatable("block.thebeetrootmod.altar");
    private final ItemStackHandler itemHandler = new ItemStackHandler(8);

    private static final int CANDLE_LEFT = 0;
    private static final int CANDLE_CENTRE = 1;
    private static final int CANDLE_RIGHT = 2;

    private static final int SLOT_1 = 3;
    private static final int SLOT_2 = 4;
    private static final int SLOT_3 = 5;
    private static final int SLOT_4 = 6;
    private static final int SLOT_5 = 7;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    private UUID owner;

    public BlockEntityAltar(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALTAR_BE.get(), pPos, pBlockState);
        this.owner = new UUID(0L, 0L);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BlockEntityAltar.this.progress;
                    case 1 -> BlockEntityAltar.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BlockEntityAltar.this.progress = pValue;
                    case 1 -> BlockEntityAltar.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 8;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        setChanged();
    }

    public void setOwner(Player player) {
        this.owner = new UUID(player.getUUID().getMostSignificantBits(), player.getUUID().getLeastSignificantBits());
        setChanged();
    }

    public boolean isOwner(Player player) {
        return player.getUUID().equals(owner);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return CONTAINER_TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AltarMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("thebeetrootmod.altar.progress", progress);
        pTag.putUUID("owner", owner);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("thebeetrootmod.altar.progress");
        if (pTag.contains("owner")) {
            owner = pTag.getUUID("owner");
        } else {
            owner = new UUID(0L, 0L);
        }
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntityAltar pBlockEntity) {
        if(hasRecipe() && this.progress == 0) {
            consumeOfferings();
        }
        if(hasRecipe() && haveCandles()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);
            pState = pState.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(pBlockEntity.isLit()));
            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
            pState = pState.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(pBlockEntity.isLit()));
        }

        pLevel.setBlock(pPos, pState, 3);
    }

    public boolean haveCandles(){
        boolean candle1 = itemHandler.isItemValid(0, Items.RED_CANDLE.getDefaultInstance());
        boolean candle2 = itemHandler.isItemValid(1, Items.RED_CANDLE.getDefaultInstance());
        boolean candle3 = itemHandler.isItemValid(2, Items.RED_CANDLE.getDefaultInstance());
        return candle1 && candle2 && candle3;
    }

    private void consumeOfferings(){
        this.itemHandler.extractItem(SLOT_1, 1, false);
        this.itemHandler.extractItem(SLOT_2, 1, false);
        this.itemHandler.extractItem(SLOT_3, 1, false);
        this.itemHandler.extractItem(SLOT_4, 1, false);
        this.itemHandler.extractItem(SLOT_5, 1, false);
    }

    private boolean isLit() {
        return this.progress > 0;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<AltarRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.setStackInSlot(SLOT_3, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(SLOT_3).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        Optional<AltarRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<AltarRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 3; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(AltarRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(SLOT_3).isEmpty() || this.itemHandler.getStackInSlot(SLOT_3).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(SLOT_3).getCount() + count <= this.itemHandler.getStackInSlot(SLOT_3).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }
}
