package net.jyu4.thebeetrootmod.registry;

import net.jyu4.thebeetrootmod.block.*;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.jyu4.thebeetrootmod.block.beetroots.*;
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
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, theBeetrootMod.MOD_ID);
    ///------------------------------///
    public static final RegistryObject<Block> CANDY_BEETROOTS = BLOCKS.register("candy_beetroots",() -> new CandyBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> GOLDEN_BEETROOTS = BLOCKS.register("golden_beetroots",() -> new GoldenBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> PURPLE_BEETROOTS = BLOCKS.register("purple_beetroots",() -> new PurpleBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> SUGAR_BEETS = BLOCKS.register("sugar_beets",() -> new SugarBeets(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> YELLOW_BEETROOTS = BLOCKS.register("yellow_beetroots",() -> new YellowBeetroots(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));

    public static final RegistryObject<Block> WORKSTATION = registerBlock("workstation",() -> new WorkstationBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> ALTAR = registerBlock("altar",() -> new AltarBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
    public static final RegistryObject<Block> WELL = registerBlock("well",() -> new WellBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
    public static final RegistryObject<Block> SHRINE = registerBlock("shrine",() -> new ShrineBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
    public static final RegistryObject<Block> RITUAL_RING = registerBlock("ritual_ring",() -> new RitualRingBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()));
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
