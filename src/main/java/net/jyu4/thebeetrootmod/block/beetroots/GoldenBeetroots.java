package net.jyu4.thebeetrootmod.block.beetroots;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class GoldenBeetroots extends BeetrootBlock {
    public GoldenBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.GOLDEN_BEETROOT_SEEDS.get();
    }
}
