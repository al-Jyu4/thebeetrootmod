package net.jyu4.thebeetrootmod.block.beetroots.crystals;

import net.jyu4.thebeetrootmod.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class RubyBeetroots extends BeetrootBlock {
    public RubyBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.RUBY_BEETROOT_SEEDS.get();
    }
}
