package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.jyu4.thebeetrootmod.block.beetroots.*;
import net.jyu4.thebeetrootmod.block.beetroots.beets.*;
import net.jyu4.thebeetrootmod.block.beetroots.crystals.*;
import net.jyu4.thebeetrootmod.block.beetroots.exotics.*;
import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, TheBeetrootMod.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TheBeetrootMod.MODID);
    ///------------------------------///
    public static final RegistryObject<Block> CANDY_BEETROOTS = BLOCKS.register("candy_beetroots",() -> new CandyBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> GOLDEN_BEETROOTS = BLOCKS.register("golden_beetroots",() -> new GoldenBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> PURPLE_BEETROOTS = BLOCKS.register("purple_beetroots",() -> new PurpleBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> YELLOW_BEETROOTS = BLOCKS.register("yellow_beetroots",() -> new YellowBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));

    public static final RegistryObject<Block> LEAF_BEETS = BLOCKS.register("leaf_beets",() -> new LeafBeets(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> SUGAR_BEETS = BLOCKS.register("sugar_beets",() -> new SugarBeets(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));

    public static final RegistryObject<Block> NETHER_BEETROOTS = BLOCKS.register("nether_beetroots",() -> new NetherBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> CHORUS_BEETROOT = BLOCKS.register("end_beetroots",() -> new EndBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));

    public static final RegistryObject<Block> ECHO_BEETROOTS = BLOCKS.register("echo_beetroots",() -> new EchoBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> MISSING_BEETROOT = BLOCKS.register("lost_beetroots",() -> new MissingBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));

    public static final RegistryObject<Block> AMETHYST_BEETROOTS = BLOCKS.register("amethyst_beetroots",() -> new AmethystBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> TOPAZ_BEETROOTS = BLOCKS.register("topaz_beetroots",() -> new TopazBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> SAPPHIRE_BEETROOTS = BLOCKS.register("sapphire_beetroots",() -> new SapphireBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> RUBY_BEETROOTS = BLOCKS.register("ruby_beetroots",() -> new RubyBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));

    ///------------------------------///
    public static final RegistryObject<Block> WORKSTATION = registerBlock("workstation",() -> new BlockWorkstation(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> ALTAR = registerBlock("altar",() -> new BlockAltar(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
    public static final RegistryObject<Block> WELL = registerBlock("well",() -> new BlockWell(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
    public static final RegistryObject<Block> SHRINE = registerBlock("shrine",() -> new BlockShrine(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
    public static final RegistryObject<Block> RITUAL_RING = registerBlock("ritual_ring",() -> new BlockRitualRing(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));

    public static final RegistryObject<BlockResearchTable> Research_Table = BLOCK_REGISTER.register("research_table", () -> new BlockResearchTable(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
    ///------------------------------///
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
