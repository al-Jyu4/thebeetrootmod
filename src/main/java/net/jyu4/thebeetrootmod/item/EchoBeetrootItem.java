package net.jyu4.thebeetrootmod.item;

import net.jyu4.thebeetrootmod.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EchoBeetrootItem extends Item {
    public EchoBeetrootItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity livingEntity) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, livingEntity);
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            InteractionHand usedHand = player.getUsedItemHand();

            if (usedHand == InteractionHand.MAIN_HAND) {
                return mainHand(pStack, pLevel, livingEntity);
            } else if (usedHand == InteractionHand.OFF_HAND) {
                return offHand(pStack, pLevel, livingEntity);
            }
        }
        return itemstack;
    }

    private ItemStack mainHand(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player) || level.isClientSide() && player.getCommandSenderWorld().dimension() != Level.OVERWORLD) return itemStack;

        BlockPos blockPos = player.getRespawnPosition();
        ResourceKey<Level> spawnDimension = player.getRespawnDimension();

        if (blockPos != null && spawnDimension != null) {
            Level spawnLevelGeneric = player.getServer().getLevel(spawnDimension);
            if (spawnLevelGeneric instanceof ServerLevel) {
                ServerLevel spawnLevel = (ServerLevel) spawnLevelGeneric;

                if (spawnLevel == level) {
                    performTeleportation(player, blockPos);
                    displayMessage(player, "thebeetrootmod.teleport.death.dimension_error"); //Teleported to your spawn point!
                }/* else if (blockPos != null&& spawnLevel != null) {
                    player.changeDimension(spawnLevel);
                    performTeleportation(player, blockPos);
                    displayMessage(player, "thebeetrootmod.teleport.death.dimension_error"); //Teleported to your spawn point in another dimension!
                }*/
            }
        } else {
            displayMessage(player, "thebeetrootmod.teleport.death.dimension_error"); //No spawn point set!
        }

        return itemStack;
    }

    private ItemStack offHand(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player) || level.isClientSide()) return itemStack;

        player.getLastDeathLocation().ifPresent(globalPos -> {
            if (player.isPassenger()) {
                player.stopRiding();
            }

            if (level.dimension().location().equals(globalPos.dimension().location())) {

                performTeleportation(player, globalPos.pos());
                displayMessage(player, "thebeetrootmod.teleport.death");
            } else {
                displayMessage(player, "thebeetrootmod.teleport.death.dimension_error");
            }
        });

        return itemStack;
    }

    private void performTeleportation(Player player, BlockPos blockPos){
        player.teleportTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
    }

    private void displayMessage(Player player, String translatable){
        player.displayClientMessage(Component.translatable(translatable), true);
    }
}
