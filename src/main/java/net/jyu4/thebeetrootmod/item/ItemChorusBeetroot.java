package net.jyu4.thebeetrootmod.item;

import net.jyu4.thebeetrootmod.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ItemChorusBeetroot extends ItemBase{
    public ItemChorusBeetroot(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player) || pLevel.isClientSide()) return pStack;

        final double maxDistance = Config.teleport_range;

        HitResult hitResult = wherePlayerLookingAt(pLevel, player, ClipContext.Fluid.NONE, maxDistance);

        if (player.isPassenger()) {player.stopRiding();}

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockPos targetPos = blockPos.relative(blockHitResult.getDirection());

            performTeleportation(player, targetPos);
        }

        return pStack;
    }

    private static HitResult wherePlayerLookingAt(Level level, Player player, ClipContext.Fluid fluidHandling, double maxDistance) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 viewVector = player.getViewVector(1.0F);
        Vec3 traceEnd = eyePosition.add(viewVector.x * maxDistance, viewVector.y * maxDistance, viewVector.z * maxDistance);
        return level.clip(new ClipContext(eyePosition, traceEnd, ClipContext.Block.OUTLINE, fluidHandling, player));
    }
}
