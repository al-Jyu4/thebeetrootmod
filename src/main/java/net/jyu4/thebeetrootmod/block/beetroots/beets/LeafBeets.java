package net.jyu4.thebeetrootmod.block.beetroots.beets;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class LeafBeets extends BeetrootBlock {
    public LeafBeets(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.LEAF_BEET_SEEDS.get();
    }
}
