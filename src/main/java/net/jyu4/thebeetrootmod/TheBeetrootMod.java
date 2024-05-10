package net.jyu4.thebeetrootmod;

import net.jyu4.thebeetrootmod.net.Message;
import net.jyu4.thebeetrootmod.net.MessageRepairItem;
import net.jyu4.thebeetrootmod.block.ModBlocks;
import net.jyu4.thebeetrootmod.block.blockentity.ModBlockEntities;
import net.jyu4.thebeetrootmod.gui.*;
import net.jyu4.thebeetrootmod.item.ModItems;
import net.jyu4.thebeetrootmod.recipe.ModRecipeType;
import net.jyu4.thebeetrootmod.recipe.ModRecipes;
import net.jyu4.thebeetrootmod.registry.ModCreativeTabs;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import software.bernie.geckolib.GeckoLib;
import net.minecraftforge.fml.config.ModConfig;

@Mod(TheBeetrootMod.MODID)
public class TheBeetrootMod
{
    public static final String MODID = "thebeetrootmod";

    public TheBeetrootMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        ///------------------------------///
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModRecipeType.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModItems.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModCreativeTabs.register(modEventBus);

        GeckoLib.initialize();
        ///------------------------------///
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation createRL(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static String createRLString(String path) {
        return MODID + ":" + path;
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
            MenuScreens.register(ModMenuTypes.MENU_SHRINE.get(), ShrineScreen::new);
            MenuScreens.register(ModMenuTypes.ALTAR_MENU.get(), AltarScreen::new);
            MenuScreens.register(ModMenuTypes.REPAIR_STATION_MENU.get(), ScreenRepairStation::new);
        }
    }

    public static SimpleChannel SIMPLE_CHANNEL;

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        SIMPLE_CHANNEL = registerChannel(TheBeetrootMod.MODID, "default");
        registerMessage(SIMPLE_CHANNEL, 0, MessageRepairItem.class);
    }

    public static SimpleChannel registerChannel(String modId, String name) {
        return NetworkRegistry.newSimpleChannel(new ResourceLocation(modId, name), () -> "1.0.0", s -> true, s -> true);
    }


    public static <T extends Message<?>> void registerMessage(SimpleChannel channel, int id, Class<T> message) {
        channel.registerMessage(id, (Class) message, Message::toBytes, (buf) -> {
            try {
                Message<?> msg = message.getDeclaredConstructor().newInstance();
                return msg.fromBytes(buf);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, (msg, fun) -> {
            NetworkEvent.Context context = fun.get();
            if (msg.getExecutingSide().equals(Dist.CLIENT)) {
                context.enqueueWork(() -> {
                    msg.executeClientSide(context);
                    context.setPacketHandled(true);
                });
            } else if (msg.getExecutingSide().equals(Dist.DEDICATED_SERVER)) {
                context.enqueueWork(() -> {
                    msg.executeServerSide(context);
                    context.setPacketHandled(true);
                });
            }
        });
    }
}
