package net.jyu4.thebeetrootmod;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class ModMethod {
    public static void displayMessage(@NotNull Player player, String string){
        player.displayClientMessage(Component.translatable(string), true);
    }
}
