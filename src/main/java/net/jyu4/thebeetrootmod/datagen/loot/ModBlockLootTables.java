package net.jyu4.thebeetrootmod.datagen.loot;

import net.jyu4.thebeetrootmod.registry.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(ModBlocks.WORKSTATION.get());
        this.dropSelf(ModBlocks.ALTAR.get());
        this.dropSelf(ModBlocks.WELL.get());
        this.dropSelf(ModBlocks.SHRINE.get());
        this.dropSelf(ModBlocks.RITUAL_RING.get());
/*
        LootItemCondition.Builder CANDY_BEETROOTS = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.CANDY_BEETROOTS.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 3));
        this.add(ModBlocks.CANDY_BEETROOTS.get(), createCropDrops(ModBlocks.CANDY_BEETROOTS.get(), ModItems.CANDY_BEETROOT.get(),
                ModItems.CANDY_BEETROOT_SEEDS.get(), CANDY_BEETROOTS));

        LootItemCondition.Builder GOLDEN_BEETROOTS = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.GOLDEN_BEETROOTS.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 3));
        this.add(ModBlocks.GOLDEN_BEETROOTS.get(), createCropDrops(ModBlocks.GOLDEN_BEETROOTS.get(), ModItems.GOLDEN_BEETROOT.get(),
                ModItems.GOLDEN_BEETROOT_SEEDS.get(), GOLDEN_BEETROOTS));

        LootItemCondition.Builder LEAF_BEETS = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.LEAF_BEETS.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 3));
        this.add(ModBlocks.LEAF_BEETS.get(), createCropDrops(ModBlocks.LEAF_BEETS.get(), ModItems.LEAF_BEET.get(),
                ModItems.LEAF_BEET_SEEDS.get(), LEAF_BEETS));

        LootItemCondition.Builder PURPLE_BEETROOTS = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.PURPLE_BEETROOTS.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 3));
        this.add(ModBlocks.PURPLE_BEETROOTS.get(), createCropDrops(ModBlocks.PURPLE_BEETROOTS.get(), ModItems.PURPLE_BEETROOT.get(),
                ModItems.PURPLE_BEETROOT_SEEDS.get(), PURPLE_BEETROOTS));

        LootItemCondition.Builder SUGAR_BEETS = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.SUGAR_BEETS.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 3));
        this.add(ModBlocks.SUGAR_BEETS.get(), createCropDrops(ModBlocks.SUGAR_BEETS.get(), ModItems.SUGAR_BEET.get(),
                ModItems.SUGAR_BEET_SEEDS.get(), SUGAR_BEETS));


        this.add(ModBlocks.SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.NETHER_SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.NETHER_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.END_STONE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
         */
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
