package net.jyu4.thebeetrootmod.block.blockentity;

import net.jyu4.thebeetrootmod.Config;
import net.jyu4.thebeetrootmod.util.ModEnergyStorage;
import net.jyu4.thebeetrootmod.util.ModUtils;
import net.jyu4.thebeetrootmod.gui.RepairStationMenu;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityRepairStation extends BlockEntityBase implements MenuProvider {

    private static final Component CONTAINER_TITLE = Component.translatable("block.thebeetrootmod.repair_station");
    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
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

    ///------------------------------///
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        pTag.putInt("repair_station_energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(pTag.getInt("repair_station_energy"));
    }

    ///------------------------------///
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntityRepairStation pEntity) {
        if(pLevel.isClientSide()) return;

        if(beetrootInSlot(pEntity)) {
            pEntity.itemHandler.extractItem(0, 1, false);
            //storedEnergy += 1;
            pEntity.ENERGY_STORAGE.receiveEnergy(1, false);
        }
    }

    public void repairItem(BlockEntityRepairStation pEntity){
        if (!hasEnergy(pEntity)) return;

        ItemStack itemStack = itemHandler.getStackInSlot(0);
        boolean emptySlot = itemStack.isEmpty();
        if (emptySlot || !hasDurability(itemStack)) return;

        if (hasEnchantments(itemStack)) return;

        if (!lostDurability(itemStack)) return;

        int itemDamage = itemStack.getDamageValue();
        int energyStored = ENERGY_STORAGE.getEnergyStored();
        int repairAmount = Math.min(itemDamage, energyStored);
        itemStack.setDamageValue(itemDamage - repairAmount);
        consumeEnergy(pEntity, repairAmount);

        Level pLevel = getLevel();
        BlockPos pPos = getBlockPos();
        ModUtils.playSound(pLevel, pPos, SoundEvents.VILLAGER_WORK_FLETCHER);
    }

    private boolean hasEnchantments(ItemStack itemStack) {
        int UNBREAKING = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, itemStack);
        int MENDING = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, itemStack);
        return UNBREAKING > 0 || MENDING > 0;
    }

    private static boolean beetrootInSlot(BlockEntityRepairStation pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == Items.BEETROOT;
    }

    private boolean hasEnergy(BlockEntityRepairStation pEntity){return pEntity.ENERGY_STORAGE.getEnergyStored() > 0;}

    private boolean hasDurability(ItemStack itemStack){
        return itemStack.getItem().getMaxDamage() > 0;
    }

    private boolean lostDurability(ItemStack itemStack){
        return itemStack.getDamageValue() > 0;
    }

    private static void consumeEnergy(BlockEntityRepairStation pEntity, int repairAmount) {
        pEntity.ENERGY_STORAGE.extractEnergy(repairAmount, false);
    }

    ///------------------------------///
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
        return new RepairStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY){
            return lazyEnergyHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    ///------------------------------///
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(3456,256){
        public void onEnergyChanged(){
            setChanged();
        }
    };

    public void setEnergyLevel(int energy) { //set energy level for client
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }


}
