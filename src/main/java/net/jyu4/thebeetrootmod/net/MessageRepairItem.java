package net.jyu4.thebeetrootmod.net;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityRepairStation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;

public class MessageRepairItem implements Message<MessageRepairItem> {

    private BlockPos pos;
    private UUID uuid;
    private boolean repair;

    public MessageRepairItem() {

    }

    public MessageRepairItem(BlockPos pos, Player player, boolean repair) {
        this.pos = pos;
        this.uuid = player.getUUID();
        this.repair = repair;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUUID().equals(uuid)) {
            TheBeetrootMod.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        BlockEntity be = context.getSender().level().getBlockEntity(pos);

        if (!(be instanceof BlockEntityRepairStation)) {
            return;
        }

        BlockEntityRepairStation repairStation = (BlockEntityRepairStation) be;
        repairStation.repairItem();

    }

    @Override
    public MessageRepairItem fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.uuid = buf.readUUID();
        this.repair = buf.readBoolean();

        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUUID(uuid);
        buf.writeBoolean(repair);
    }

}
