package net.jyu4.thebeetrootmod;

import net.jyu4.thebeetrootmod.block.ModBlocks;
import net.jyu4.thebeetrootmod.block.blockentity.ModBlockEntities;
import net.jyu4.thebeetrootmod.gui.*;
import net.jyu4.thebeetrootmod.item.ModItems;
import net.jyu4.thebeetrootmod.recipe.ModRecipeType;
import net.jyu4.thebeetrootmod.recipe.ModRecipes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import software.bernie.geckolib.GeckoLib;

@Mod(TheBeetrootMod.MODID)
public class TheBeetrootMod
{
    public static final String MODID = "thebeetrootmod";

    public static SimpleChannel SIMPLE_CHANNEL;

    public TheBeetrootMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ///------------------------------///
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModRecipeType.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModItems.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModCreativeTabs.register(modEventBus);
        //MachineryTab.register(modEventBus);

        GeckoLib.initialize();
        ///------------------------------///
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.WORKSTATION.get(), WorkstationScreen::new);
            MenuScreens.register(ModMenuTypes.ALTAR_MENU.get(), AltarScreen::new);
        }
    }
}
