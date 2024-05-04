package net.jyu4.thebeetrootmod.block.beetroots.exotics;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class EchoBeetroots extends BeetrootBlock {
    public EchoBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.ECHO_BEETROOT_SEEDS.get();
    }
}
