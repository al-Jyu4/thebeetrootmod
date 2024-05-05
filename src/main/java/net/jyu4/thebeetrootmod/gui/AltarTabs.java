package net.jyu4.thebeetrootmod.gui;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class AltarTabs {

    public static final ResourceLocation REGISTRY_KEY = TheBeetrootMod.createRL("bio_forge_tab");
    public static final DeferredRegister<AltarTab> BIO_FORGE_TABS = DeferredRegister.create(REGISTRY_KEY, TheBeetrootMod.MODID);
    public static final Supplier<IForgeRegistry<AltarTab>> REGISTRY = BIO_FORGE_TABS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<AltarTab> SEARCH = register("search", 99, () -> Items.COMPASS);
    public static final RegistryObject<AltarTab> BUILDING_BLOCKS = register("blocks", 10, ModItems.GOLDEN_BEETROOT);
    public static final RegistryObject<AltarTab> MACHINES = register("machines", 9, ModItems.GOLDEN_BEETROOT);
    public static final RegistryObject<AltarTab> TOOLS = register("tools", 8, ModItems.GOLDEN_BEETROOT);
    public static final RegistryObject<AltarTab> COMPONENTS = register("components", 7, ModItems.GOLDEN_BEETROOT);
    public static final RegistryObject<AltarTab> MISC = register("misc", -99, ModItems.GOLDEN_BEETROOT);

    private AltarTabs() {}

    private static RegistryObject<AltarTab> register(String name, Supplier<? extends Item> itemSupplier) {
        return BIO_FORGE_TABS.register(name, () -> new AltarTab(itemSupplier.get()));
    }

    private static RegistryObject<AltarTab> register(String name, int sortPriority, Supplier<? extends Item> itemSupplier) {
        return BIO_FORGE_TABS.register(name, () -> new AltarTab(sortPriority, itemSupplier.get()));
    }

}
