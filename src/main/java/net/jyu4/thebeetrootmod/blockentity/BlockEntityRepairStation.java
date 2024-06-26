package net.jyu4.thebeetrootmod.blockentity;

import net.jyu4.thebeetrootmod.util.ModUtils;
import net.jyu4.thebeetrootmod.gui.RepairStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityRepairStation extends BlockEntityBase {

    private static final Component CONTAINER_TITLE = Component.translatable("block.thebeetrootmod.repair_station");

    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final JuiceStorage energy = new JuiceStorage(3456, 0, 0, 0);
    private final LazyOptional<JuiceStorage> energyOptional = LazyOptional.of(() -> this.energy);

    ///------------------------------///
    // base

    public BlockEntityRepairStation(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REPAIR_STATION_BE.get(), pPos, pBlockState);
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
            return energyOptional.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        this.energyOptional.invalidate();
    }

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex){
                case 0 -> BlockEntityRepairStation.this.energy.getEnergyStored();
                case 1 -> BlockEntityRepairStation.this.energy.getMaxEnergyStored();
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex){
                case 0 -> BlockEntityRepairStation.this.energy.setEnergy(pValue);
            };
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        inventory.setItem(0, itemHandler.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    ///------------------------------///
    // save

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("energy", this.energy.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.energy.deserializeNBT(pTag.get("energy"));
    }

    ///------------------------------///
    // functionalities

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntityRepairStation pEntity) {
        if(pLevel.isClientSide()) return;

        if(!canFillEnergy()) return;

        if (beetrootInSlot(pEntity)) {
            int itemCount = pEntity.itemHandler.getStackInSlot(0).getCount();
            int neededEnergy = this.energy.getMaxEnergyStored() - this.energy.getEnergyStored();
            int itemsToConsume = Math.min(itemCount, neededEnergy);

            pEntity.itemHandler.extractItem(0, itemsToConsume, false);

            this.energy.addEnergy(itemsToConsume);

            sendUpdate();
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
        int energyStored = energy.getEnergyStored();
        int repairAmount = Math.min(itemDamage, energyStored);
        itemStack.setDamageValue(itemDamage - repairAmount);
        consumeEnergy(pEntity, repairAmount);
        sendUpdate();

        Level pLevel = getLevel();
        BlockPos pPos = getBlockPos();
        ModUtils.playSound(pLevel, pPos, SoundEvents.VILLAGER_WORK_FLETCHER);
    }

    private boolean canFillEnergy(){
        return this.energy.getEnergyStored() + 1 < this.energy.getMaxEnergyStored();
    }

    private boolean hasEnchantments(ItemStack itemStack) {
        int UNBREAKING = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, itemStack);
        int MENDING = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, itemStack);
        return UNBREAKING > 0 || MENDING > 0;
    }

    private static boolean beetrootInSlot(BlockEntityRepairStation pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == Items.BEETROOT;
    }

    private boolean hasEnergy(BlockEntityRepairStation pEntity){return pEntity.energy.getEnergyStored() > 0;}

    private boolean hasDurability(ItemStack itemStack){
        return itemStack.getItem().getMaxDamage() > 0;
    }

    private boolean lostDurability(ItemStack itemStack){
        return itemStack.getDamageValue() > 0;
    }

    private static void consumeEnergy(BlockEntityRepairStation pEntity, int repairAmount) {
        pEntity.energy.removeEnergy(repairAmount);
    }
}
