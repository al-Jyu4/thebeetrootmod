package net.jyu4.thebeetrootmod.block.beetroots.exotics;

import net.jyu4.thebeetrootmod.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class EndBeetroots extends BeetrootBlock {
    public EndBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.END_BEETROOT_SEEDS.get();
    }
}
