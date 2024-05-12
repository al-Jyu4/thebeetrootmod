package net.jyu4.thebeetrootmod.block.beetroot;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class CandyBeetroots extends BeetrootBlock {
    public CandyBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.CANDY_BEETROOT_SEEDS.get();
    }
}
