package net.jyu4.thebeetrootmod.registry;

import net.jyu4.thebeetrootmod.item.BaseBeetrootItem;
import net.jyu4.thebeetrootmod.item.EchoBeetrootItem;
import net.jyu4.thebeetrootmod.item.EndBeetrootItem;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, theBeetrootMod.MOD_ID);
    ///------------------------------///
    public static final RegistryObject<Item> CANDY_BEETROOT = ITEMS.register("candy_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> CANDY_BEETROOT_SEEDS = ITEMS.register("candy_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.CANDY_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BEETROOT = ITEMS.register("golden_beetroot",() -> new Item(new Item.Properties().food(ModFoods.GOLDEN_BEETROOT).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLDEN_BEETROOT_SEEDS = ITEMS.register("golden_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.GOLDEN_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_BEETROOT = ITEMS.register("purple_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> PURPLE_BEETROOT_SEEDS = ITEMS.register("purple_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.PURPLE_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_BEETROOT = ITEMS.register("yellow_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> YELLOW_BEETROOT_SEEDS = ITEMS.register("yellow_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.YELLOW_BEETROOTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAF_BEET = ITEMS.register("leaf_beet",() -> new BaseBeetrootItem(new Item.Properties()));
    public static final RegistryObject<Item> LEAF_BEET_SEEDS = ITEMS.register("leaf_beet_seeds",() -> new ItemNameBlockItem(ModBlocks.LEAF_BEETS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SUGAR_BEET = ITEMS.register("sugar_beet",() -> new BaseBeetrootItem(new Item.Properties()));
    public static final RegistryObject<Item> SUGAR_BEET_SEEDS = ITEMS.register("sugar_beet_seeds",() -> new ItemNameBlockItem(ModBlocks.SUGAR_BEETS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_BEETROOT = ITEMS.register("ancient_beetroot",() -> new BaseBeetrootItem(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_BEETROOT_SEEDS = ITEMS.register("ancient_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.SUGAR_BEETS.get(), new Item.Properties()));

    public static final RegistryObject<Item> NETHER_BEETROOT = ITEMS.register("nether_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> NETHER_BEETROOT_SEEDS = ITEMS.register("nether_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.NETHER_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> END_BEETROOT = ITEMS.register("end_beetroot",() -> new EndBeetrootItem(new Item.Properties().food(ModFoods.END_BEETROOT)));
    public static final RegistryObject<Item> END_BEETROOT_SEEDS = ITEMS.register("end_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.END_BEETROOTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> ECHO_BEETROOT = ITEMS.register("echo_beetroot",() -> new EchoBeetrootItem(new Item.Properties().food(ModFoods.ECHO_BEETROOT)));
    public static final RegistryObject<Item> ECHO_BEETROOT_SEEDS = ITEMS.register("echo_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.ECHO_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MISSING_BEETROOT = ITEMS.register("missing_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> MISSING_BEETROOT_SEEDS = ITEMS.register("missing_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.MISSING_BEETROOT.get(), new Item.Properties()));

    public static final RegistryObject<Item> AMETHYST_BEETROOT = ITEMS.register("amethyst_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> AMETHYST_BEETROOT_SEEDS = ITEMS.register("amethyst_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.AMETHYST_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOPAZ_BEETROOT = ITEMS.register("topaz_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> TOPAZ_BEETROOT_SEEDS = ITEMS.register("topaz_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.TOPAZ_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_BEETROOT = ITEMS.register("sapphire_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> SAPPHIRE_BEETROOT_SEEDS = ITEMS.register("sapphire_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.SAPPHIRE_BEETROOTS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUBY_BEETROOT = ITEMS.register("ruby_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> RUBY_BEETROOT_SEEDS = ITEMS.register("ruby_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.RUBY_BEETROOTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> SANGUINE_BEETROOT = ITEMS.register("sanguine_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> SANGUINE_BEETROOT_SEEDS = ITEMS.register("sanguine_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.YELLOW_BEETROOTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> STARDUST_BEETROOT = ITEMS.register("stardust_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> STARDUST_BEETROOT_SEEDS = ITEMS.register("stardust_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.YELLOW_BEETROOTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_BEETROOT = ITEMS.register("arcane_beetroot",() -> new BaseBeetrootItem(new Item.Properties().food(Foods.BEETROOT)));
    public static final RegistryObject<Item> ARCANE_BEETROOT_SEEDS = ITEMS.register("arcane_beetroot_seeds",() -> new ItemNameBlockItem(ModBlocks.YELLOW_BEETROOTS.get(), new Item.Properties()));
    ///------------------------------///
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
