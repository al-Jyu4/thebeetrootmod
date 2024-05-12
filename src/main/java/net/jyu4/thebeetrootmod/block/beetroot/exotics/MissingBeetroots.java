package net.jyu4.thebeetrootmod.block.beetroot.exotics;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class MissingBeetroots extends BeetrootBlock {
    public MissingBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.MISSING_BEETROOT_SEEDS.get();
    }
}
