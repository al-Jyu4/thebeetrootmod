package net.jyu4.thebeetrootmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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


public class EndBeetrootItem extends Item {
    public EndBeetrootItem(Item.Properties pProperties) {
        super(pProperties);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (!pLevel.isClientSide && pEntityLiving instanceof Player) {
            Player player = (Player) pEntityLiving;
            final double maxDistance = 64.0;
            HitResult hitResult = getPlayerPOVHitResult(pLevel, player, ClipContext.Fluid.NONE, maxDistance);

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockPos targetPos = blockPos.relative(blockHitResult.getDirection());

                if (player.isPassenger()) {
                    player.stopRiding();
                }

                Vec3 vec3 = player.position();
                pLevel.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));
                net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(player, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
                if (!event.isCanceled() && player.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                    SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                    pLevel.playSound(null, vec3.x, vec3.y, vec3.z, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.playSound(soundevent, 1.0F, 1.0F);
                }
            }
        }

        return itemstack;
    }

    private static HitResult getPlayerPOVHitResult(Level level, Player player, ClipContext.Fluid fluidHandling, double maxDistance) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 viewVector = player.getViewVector(1.0F);
        Vec3 traceEnd = eyePosition.add(viewVector.x * maxDistance, viewVector.y * maxDistance, viewVector.z * maxDistance);
        return level.clip(new ClipContext(eyePosition, traceEnd, ClipContext.Block.OUTLINE, fluidHandling, player));
    }
}

