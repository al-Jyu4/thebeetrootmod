package net.jyu4.thebeetrootmod.creativetab;

import net.jyu4.thebeetrootmod.registry.ModBlocks;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MachineryTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, theBeetrootMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BEETROOT_MACHINERY = CREATIVE_MODE_TABS.register("thebeetrootmod_machinery",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WORKSTATION.get()))
                    .title(Component.translatable("thebeetrootmod.beetroot_machinery"))
                    .displayItems((pParameters, pOutput) -> {
                        ///------------------------------///
                        ///------------------------------///
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
