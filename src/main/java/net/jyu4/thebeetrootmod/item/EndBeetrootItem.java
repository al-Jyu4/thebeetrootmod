package net.jyu4.thebeetrootmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class EndBeetrootItem extends Item {
    public EndBeetrootItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity livingEntity) {
        ItemStack itemStack = super.finishUsingItem(pStack, pLevel, livingEntity);

        if (!(livingEntity instanceof ServerPlayer player) || pLevel.isClientSide()) return itemStack;
        final double maxDistance = 80.0;
        HitResult hitResult = wherePlayerLookingAt(pLevel, player, ClipContext.Fluid.NONE, maxDistance);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockPos targetPos = blockPos.relative(blockHitResult.getDirection());

            if (player.isPassenger()) {
                player.stopRiding();
            }

            /*
            Vec3 vec3 = player.position();
            pLevel.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));

             */
            performTeleportation(player, targetPos);
            /*net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(player, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
            if (!event.isCanceled() && player.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                pLevel.playSound(null, vec3.x, vec3.y, vec3.z, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.playSound(soundevent, 1.0F, 1.0F);
            }

             */
        }

        return itemStack;
    }

    private static HitResult wherePlayerLookingAt(Level level, Player player, ClipContext.Fluid fluidHandling, double maxDistance) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 viewVector = player.getViewVector(1.0F);
        Vec3 traceEnd = eyePosition.add(viewVector.x * maxDistance, viewVector.y * maxDistance, viewVector.z * maxDistance);
        return level.clip(new ClipContext(eyePosition, traceEnd, ClipContext.Block.OUTLINE, fluidHandling, player));
    }

    private void performTeleportation(Player player, BlockPos blockPos){
        player.teleportTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
    }
}

