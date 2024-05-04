package net.jyu4.thebeetrootmod.block.beetroots.crystals;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class TopazBeetroots extends BeetrootBlock {
    public TopazBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.TOPAZ_BEETROOT_SEEDS.get();
    }
}
