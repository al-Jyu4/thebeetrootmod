package net.jyu4.thebeetrootmod.block.beetroots.crystals;

import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class AmethystBeetroots extends BeetrootBlock {
    public AmethystBeetroots(Properties pProperties) {
        super(pProperties);
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.AMETHYST_BEETROOT_SEEDS.get();
    }
}
