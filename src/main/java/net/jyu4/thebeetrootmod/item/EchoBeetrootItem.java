package net.jyu4.thebeetrootmod.item;

import net.minecraft.core.BlockPos;
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
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (pEntityLiving instanceof Player) {
            Player player = (Player) pEntityLiving;
            InteractionHand usedHand = player.getUsedItemHand();

            if (usedHand == InteractionHand.MAIN_HAND) {
                return mainHand(pStack, pLevel, player);
            } else if (usedHand == InteractionHand.OFF_HAND) {
                return offHand(pStack, pLevel, player);
            }
        }
        return itemstack;
    }

    private ItemStack mainHand(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide && player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            BlockPos spawnPoint = serverPlayer.getRespawnPosition();
            ResourceKey<Level> spawnDimensionKey = serverPlayer.getRespawnDimension();

            if (spawnPoint != null && spawnDimensionKey != null) {
                Level spawnLevelGeneric = serverPlayer.getServer().getLevel(spawnDimensionKey);
                if (spawnLevelGeneric instanceof ServerLevel) {
                    ServerLevel spawnLevel = (ServerLevel) spawnLevelGeneric;

                    if (spawnLevel == level) {
                        // Teleport within the same dimension
                        serverPlayer.teleportTo(spawnLevel, spawnPoint.getX() + 0.5, spawnPoint.getY(), spawnPoint.getZ() + 0.5, 0, 0);
                        serverPlayer.displayClientMessage(Component.literal("Teleported to your spawn point!"), true);
                    } else if (spawnLevel != null) {
                        // Handle teleportation across dimensions
                        serverPlayer.changeDimension(spawnLevel);
                        serverPlayer.teleportTo(spawnLevel, spawnPoint.getX() + 0.5, spawnPoint.getY(), spawnPoint.getZ() + 0.5, 0, 0);
                        serverPlayer.displayClientMessage(Component.literal("Teleported to your spawn point in another dimension!"), true);
                    } else {
                        serverPlayer.displayClientMessage(Component.literal("Your spawn dimension is not available!"), true);
                    }
                } else {
                    serverPlayer.displayClientMessage(Component.literal("Error: Spawn level is not a server level."), true);
                }
            } else {
                serverPlayer.displayClientMessage(Component.literal("No spawn point set!"), true);
            }
        }
        return stack;
    }

    private ItemStack offHand(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            player.getLastDeathLocation().ifPresent(globalPos -> {
                if (level.dimension().location().equals(globalPos.dimension().location())) {
                    BlockPos pos = globalPos.pos();
                    player.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    player.displayClientMessage(Component.literal("Teleported to last death location!"), true);
                } else {
                    player.displayClientMessage(Component.literal("Last death location is in another dimension!"), true);
                }
            });
        }
        return stack;
    }
}
