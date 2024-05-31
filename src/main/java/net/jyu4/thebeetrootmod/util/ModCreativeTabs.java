package net.jyu4.thebeetrootmod.util;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.jyu4.thebeetrootmod.block.ModBlocks;
import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheBeetrootMod.MODID);

    public static final RegistryObject<CreativeModeTab> BEETROOT_INGREDIENTS = CREATIVE_MODE_TABS.register("tab.thebeetrootmod_ingredients",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.BEETROOT))
                    .title(Component.translatable("tab.thebeetrootmod.beetroot_ingredients"))
                    .displayItems((pParameters, pOutput) -> {
                        ///------------------------------///
                        pOutput.accept(ModBlocks.REPAIR_STATION.get());
                        pOutput.accept(Items.BEETROOT_SEEDS);
                        pOutput.accept(Items.BEETROOT);
                        pOutput.accept(ModItems.CANDY_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.CANDY_BEETROOT.get());
                        pOutput.accept(ModItems.PURPLE_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.PURPLE_BEETROOT.get());
                        pOutput.accept(ModItems.YELLOW_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.YELLOW_BEETROOT.get());

                        pOutput.accept(ModBlocks.ALTAR.get());
                        pOutput.accept(ModItems.LEAF_BEET_SEEDS.get());
                        pOutput.accept(ModItems.LEAF_BEET.get());
                        pOutput.accept(ModItems.SUGAR_BEET_SEEDS.get());
                        pOutput.accept(ModItems.SUGAR_BEET.get());
                        pOutput.accept(ModItems.ANCIENT_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.ANCIENT_BEETROOT.get());
                        pOutput.accept(ModItems.GOLDEN_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.GOLDEN_BEETROOT.get());

                        pOutput.accept(ModBlocks.WELL.get());
                        pOutput.accept(ModItems.MISSING_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.MISSING_BEETROOT.get());
                        pOutput.accept(ModItems.ECHO_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.ECHO_BEETROOT.get());
                        pOutput.accept(ModItems.NETHER_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.NETHER_BEETROOT.get());
                        pOutput.accept(ModItems.CHORUS_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.CHORUS_BEETROOT.get());

                        pOutput.accept(ModBlocks.SHRINE.get());
                        pOutput.accept(ModItems.AMETHYST_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.AMETHYST_BEETROOT.get());
                        pOutput.accept(ModItems.TOPAZ_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.TOPAZ_BEETROOT.get());
                        pOutput.accept(ModItems.SAPPHIRE_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.SAPPHIRE_BEETROOT.get());
                        pOutput.accept(ModItems.RUBY_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.RUBY_BEETROOT.get());

                        pOutput.accept(ModBlocks.RITUAL_RING.get());
                        pOutput.accept(ModItems.ARCANE_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.ARCANE_BEETROOT.get());
                        pOutput.accept(ModItems.STARDUST_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.STARDUST_BEETROOT.get());
                        pOutput.accept(ModItems.SANGUINE_BEETROOT_SEEDS.get());
                        pOutput.accept(ModItems.SANGUINE_BEETROOT.get());
                        ///------------------------------///
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
