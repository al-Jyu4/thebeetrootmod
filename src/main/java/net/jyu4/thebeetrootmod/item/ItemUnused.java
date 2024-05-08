package net.jyu4.thebeetrootmod.item;

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
        components.add(Component.translatable("desc.beetrootmod.unused").withStyle(ChatFormatting.GRAY));

    /*
    if (Config.UNUSED_TOOLTIP.get()) {
        components.add(Component.translatable("desc.beetrootmod.unused").withStyle(ChatFormatting.GRAY));
    }

     */
    }
}
