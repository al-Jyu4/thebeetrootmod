package net.jyu4.thebeetrootmod;

import net.jyu4.thebeetrootmod.gui.*;
import com.mojang.logging.LogUtils;
import net.jyu4.thebeetrootmod.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(net.jyu4.thebeetrootmod.theBeetrootMod.MOD_ID)
public class theBeetrootMod
{
    public static final String MOD_ID = "thebeetrootmod";

    public theBeetrootMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ///------------------------------///
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModRecipeType.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModItems.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModCreativeModTabs.register(modEventBus);

        GeckoLib.initialize();
        ///------------------------------///
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.WORKSTATION.get(), WorkstationScreen::new);
            MenuScreens.register(ModMenuTypes.ALTAR_MENU.get(), AltarScreen::new);
        }
    }
}
