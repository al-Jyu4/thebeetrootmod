package net.jyu4.thebeetrootmod.block.beetroot.beets;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class SugarBeets extends BeetrootBlock {
    public SugarBeets(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.SUGAR_BEET_SEEDS.get();
    }
}
