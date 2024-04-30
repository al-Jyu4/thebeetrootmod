package net.jyu4.thebeetrootmod.block.beetroots.exotics;

import net.jyu4.thebeetrootmod.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class NetherBeetroots extends BeetrootBlock {
    public NetherBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.NETHER_BEETROOT_SEEDS.get();
    }
}
