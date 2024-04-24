package net.jyu4.thebeetrootmod.registry;

import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, theBeetrootMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BEETROOT_TAB = CREATIVE_MODE_TABS.register("thebeetrootmod",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.BEETROOT))
                    .title(Component.translatable("creativetab.beetroot_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        ///------------------------------///
                        pOutput.accept(ModBlocks.WORKSTATION.get());
                        pOutput.accept(Items.BEETROOT_SEEDS);
                        pOutput.accept(Items.BEETROOT);
                        pOutput.accept(ModItems.CANDY_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.CANDY_BEETROOT.get());
                        pOutput.accept(ModItems.GOLDEN_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.GOLDEN_BEETROOT.get());
                        pOutput.accept(ModItems.PURPLE_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.PURPLE_BEETROOT.get());

                        pOutput.accept(ModBlocks.ALTAR.get());
                        pOutput.accept(ModItems.SUGAR_BEET_SEEDS.get());
                        pOutput.accept(ModItems.SUGAR_BEET.get());
                        pOutput.accept(ModItems.YELLOW_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.YELLOW_BEETROOT.get());

                        pOutput.accept(ModBlocks.WELL.get());
                        pOutput.accept(ModBlocks.SHRINE.get());
                        pOutput.accept(ModBlocks.RITUAL_RING.get());
                        ///------------------------------///
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
