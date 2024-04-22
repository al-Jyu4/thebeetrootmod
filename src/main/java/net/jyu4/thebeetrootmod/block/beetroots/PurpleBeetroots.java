package net.jyu4.thebeetrootmod.block.beetroots;

import net.jyu4.thebeetrootmod.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class PurpleBeetroots extends BeetrootBlock {
    public PurpleBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.PURPLE_BEETROOT_SEEDS.get();
    }
}
