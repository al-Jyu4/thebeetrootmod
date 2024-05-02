package net.jyu4.thebeetrootmod.creativetab;

import net.jyu4.thebeetrootmod.registry.ModItems;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class IngredientsTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, theBeetrootMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BEETROOT_INGREDIENTS = CREATIVE_MODE_TABS.register("thebeetrootmod_ingredients",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.BEETROOT))
                    .title(Component.translatable("thebeetrootmod.beetroot_ingredients"))
                    .displayItems((pParameters, pOutput) -> {
                        ///------------------------------///
                        pOutput.accept(Items.BEETROOT_SEEDS);
                        pOutput.accept(Items.BEETROOT);
                        pOutput.accept(ModItems.CANDY_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.CANDY_BEETROOT.get());
                        pOutput.accept(ModItems.PURPLE_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.PURPLE_BEETROOT.get());
                        pOutput.accept(ModItems.YELLOW_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.YELLOW_BEETROOT.get());

                        pOutput.accept(ModItems.LEAF_BEET_SEEDS.get());
                        pOutput.accept(ModItems.LEAF_BEET.get());
                        pOutput.accept(ModItems.SUGAR_BEET_SEEDS.get());
                        pOutput.accept(ModItems.SUGAR_BEET.get());
                        pOutput.accept(ModItems.ANCIENT_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.ANCIENT_BEETROOT.get());
                        pOutput.accept(ModItems.GOLDEN_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.GOLDEN_BEETROOT.get());

                        pOutput.accept(ModItems.MISSING_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.MISSING_BEETROOT.get());
                        pOutput.accept(ModItems.ECHO_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.ECHO_BEETROOT.get());
                        pOutput.accept(ModItems.NETHER_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.NETHER_BEETROOT.get());
                        pOutput.accept(ModItems.END_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.END_BEETROOT.get());

                        pOutput.accept(ModItems.AMETHYST_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.AMETHYST_BEETROOT.get());
                        pOutput.accept(ModItems.TOPAZ_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.TOPAZ_BEETROOT.get());
                        pOutput.accept(ModItems.SAPPHIRE_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.SAPPHIRE_BEETROOT.get());
                        pOutput.accept(ModItems.RUBY_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.RUBY_BEETROOT.get());
                        ///------------------------------///
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}