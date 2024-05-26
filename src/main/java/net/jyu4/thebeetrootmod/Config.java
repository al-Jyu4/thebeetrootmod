package net.jyu4.thebeetrootmod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
///
    private static final ForgeConfigSpec.IntValue TELEPORT_RANGE = BUILDER
            .comment("how far can chorus beetroot teleports you")
            .defineInRange("teleport_range", 80, 0, Integer.MAX_VALUE);
    public static int teleport_range;
///
    private static final ForgeConfigSpec.BooleanValue ENCHANTMENT_REPAIR = BUILDER
            .comment("Repair unbreaking and mending tools")
            .define("enchantment_repair", false);
    public static boolean enchantment_repair;
/*


    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);





    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;



    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }


 */

    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        teleport_range = TELEPORT_RANGE.get();
        enchantment_repair = ENCHANTMENT_REPAIR.get();
                /*
        magicNumber = MAGIC_NUMBER.get();
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

        // convert the list of strings into a set of items
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());

         */
    }
}
