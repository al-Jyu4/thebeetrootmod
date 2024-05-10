package net.jyu4.thebeetrootmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ItemBase extends Item {
    public ItemBase(Properties pProperties) {
        super(pProperties);
    }

    public void performTeleportation(Player player, BlockPos blockPos){
        player.teleportTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
    }
}
