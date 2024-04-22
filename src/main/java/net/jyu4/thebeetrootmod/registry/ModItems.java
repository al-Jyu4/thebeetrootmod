package net.jyu4.thebeetrootmod.registry;

import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, theBeetrootMod.MOD_ID);
    ///------------------------------///
    public static final RegistryObject<Item> CANDY_BEETROOT = ITEMS.register("candy_beetroot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANDY_BEETROOT_SEEDS = ITEMS.register("candy_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.CANDY_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BEETROOT = ITEMS.register("golden_beetroot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BEETROOT_SEEDS = ITEMS.register("golden_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.GOLDEN_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_BEETROOT = ITEMS.register("purple_beetroot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_BEETROOT_SEEDS = ITEMS.register("purple_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.PURPLE_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SUGAR_BEET = ITEMS.register("sugar_beet",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUGAR_BEET_SEEDS = ITEMS.register("sugar_beet_seeds",() -> new ItemNameBlockItem(ModBlocks.SUGAR_BEETS.get(), new Item.Properties()));

    ///------------------------------///
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
