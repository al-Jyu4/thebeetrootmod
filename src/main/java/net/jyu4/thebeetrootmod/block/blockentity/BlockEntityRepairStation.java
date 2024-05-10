package net.jyu4.thebeetrootmod.block.blockentity;

import net.jyu4.thebeetrootmod.ModUtils;
import net.jyu4.thebeetrootmod.gui.MenuRepairStation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityRepairStation extends BlockEntityBase implements MenuProvider {

    private static final Component CONTAINER_TITLE = Component.translatable("block.beetrootmod.repair_station");
    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public BlockEntityRepairStation(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REPAIR_STATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BlockEntityRepairStation.this.progress;
                    case 1 -> BlockEntityRepairStation.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BlockEntityRepairStation.this.progress = pValue;
                    case 1 -> BlockEntityRepairStation.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
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

    public void repairItem(){ //pLevel and pPos for playSound() method.

        ItemStack itemStack = itemHandler.getStackInSlot(0);
        boolean emptySlot = itemStack.isEmpty();
        if (emptySlot || !hasDurability(itemStack)) return;

        if (!isRepairable(itemStack)) return;

        itemStack.setDamageValue(0);

        Level pLevel = getLevel();
        BlockPos pPos = getBlockPos();
        ModUtils.playSound(pLevel, pPos, SoundEvents.VILLAGER_WORK_FLETCHER);
    }

    private boolean hasDurability(ItemStack itemStack){
        return itemStack.getItem().getMaxDamage() > 0;
    }

    private boolean isRepairable(ItemStack itemStack){
        return itemStack.getDamageValue() > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        inventory.setItem(0, itemHandler.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return CONTAINER_TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MenuRepairStation(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }
}
