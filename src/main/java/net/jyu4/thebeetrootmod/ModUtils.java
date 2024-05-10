package net.jyu4.thebeetrootmod;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class ModUtils {
    public static void displayMessage(Player player, String translatable){
        player.displayClientMessage(Component.translatable(translatable), true);
    }

    public static void translatable(List<Component> components, String translatable, ChatFormatting colour){
        components.add(Component.translatable(translatable).withStyle(colour));
    }

    public static void playSound(Level pLevel, BlockPos pPos, SoundEvent soundEvent){
        pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

}
