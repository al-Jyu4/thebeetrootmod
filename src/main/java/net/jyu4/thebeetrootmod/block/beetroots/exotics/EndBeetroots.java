package net.jyu4.thebeetrootmod.block.beetroots.exotics;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class EndBeetroots extends BeetrootBlock {
    public EndBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.CHORUS_BEETROOT_SEEDS.get();
    }
}
