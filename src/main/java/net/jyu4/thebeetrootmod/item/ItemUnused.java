package net.jyu4.thebeetrootmod.item;

import net.jyu4.thebeetrootmod.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUnused extends Item {
    public ItemUnused(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        ModUtils.translatable(components, "desc.beetrootmod.unused",ChatFormatting.GRAY);

    /*
    if (Config.UNUSED_TOOLTIP.get()) {
        ModUtils.translatable(components, "desc.beetrootmod.unused",ChatFormatting.GRAY);
    }

     */
    }
}
