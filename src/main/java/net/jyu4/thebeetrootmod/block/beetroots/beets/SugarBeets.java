package net.jyu4.thebeetrootmod.block.beetroots.beets;

import net.jyu4.thebeetrootmod.registry.ModItems;
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
