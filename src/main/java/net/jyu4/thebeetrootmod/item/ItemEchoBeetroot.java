package net.jyu4.thebeetrootmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemEchoBeetroot extends ItemBase{
    public ItemEchoBeetroot(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!(pLivingEntity instanceof Player)) return pStack;

        Player player = (Player) pLivingEntity;
        InteractionHand usedHand = player.getUsedItemHand();

        if (usedHand == InteractionHand.MAIN_HAND) {
            return mainHand(pStack, pLevel, pLivingEntity);
        } else if (usedHand == InteractionHand.OFF_HAND) {
            return offHand(pStack, pLevel, pLivingEntity);
        }

        return pStack;
    }

    private ItemStack mainHand(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!(pLivingEntity instanceof ServerPlayer player) || pLevel.isClientSide() && player.getCommandSenderWorld().dimension() != Level.OVERWORLD) return pStack;

        BlockPos blockPos = player.getRespawnPosition();
        ResourceKey<Level> spawnDimension = player.getRespawnDimension();

        if (blockPos == null || spawnDimension == null) {
            displayMessage(player, "toast.beetrootmod.echo_beetroot.no_spawn");
            return pStack;
        }

        ServerLevel spawnLevelGeneric = player.getServer().getLevel(spawnDimension);
        if (spawnLevelGeneric != null) {
            ServerLevel spawnLevel = spawnLevelGeneric;

            if (spawnLevel != pLevel) {
                player.changeDimension(spawnLevel);
            }
            performTeleportation(player, blockPos);
            displayMessage(player, "toast.beetrootmod.echo_beetroot.teleport_spawn");

        }

        return pStack;
    }

    private ItemStack offHand(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!(pLivingEntity instanceof ServerPlayer player) || pLevel.isClientSide()) return pStack;

        player.getLastDeathLocation().ifPresent(globalPos -> {
            if (player.isPassenger()) {player.stopRiding();}

            if (pLevel.dimension().location().equals(globalPos.dimension().location())) {

                performTeleportation(player, globalPos.pos());
                displayMessage(player, "toast.beetrootmod.echo_beetroot.teleport_death");
            } else {
                displayMessage(player, "toast.beetrootmod.echo_beetroot.dimension_error");
            }
        });

        return pStack;
    }
}
