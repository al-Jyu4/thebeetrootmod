package net.jyu4.thebeetrootmod.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class BlockEntityBase extends BlockEntity implements Nameable {

    private Component name;
    private CompoundTag compoundLast;

    public BlockEntityBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag updateTag = super.getUpdateTag();
        saveAdditional(updateTag);
        return updateTag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public abstract Component getTranslatedName();

    public void setCustomName(Component name) {
        this.name = name;
    }

    @Override
    public Component getName() {
        return name != null ? name : getTranslatedName();
    }

    @Override
    @Nullable
    public Component getCustomName() {
        return name;
    }

    public abstract ContainerData getFields();

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        if (name != null) {
            compound.putString("CustomName", Component.Serializer.toJson(name));
        }
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("CustomName")) {
            name = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
        super.load(compound);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
        if ((this instanceof IFluidHandler && cap.equals(ForgeCapabilities.FLUID_HANDLER)) ||
                (this instanceof IEnergyStorage && cap.equals(ForgeCapabilities.ENERGY))) {
            return LazyOptional.of(() -> (T) this);
        }
        return LazyOptional.empty();
    }
}