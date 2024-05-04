package net.jyu4.thebeetrootmod.datagen;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheBeetrootMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        /*
        simpleItem(ModItems.CANDY_BEETROOT);
        simpleItem(ModItems.CANDY_BEETROOT_SEEDS);
        simpleItem(ModItems.GOLDEN_BEETROOT);
        simpleItem(ModItems.GOLDEN_BEETROOT_SEEDS);
        simpleItem(ModItems.LEAF_BEET);
        simpleItem(ModItems.LEAF_BEET_SEEDS);
        simpleItem(ModItems.PURPLE_BEETROOT);
        simpleItem(ModItems.PURPLE_BEETROOT_SEEDS);
        simpleItem(ModItems.SUGAR_BEET);
        simpleItem(ModItems.SUGAR_BEET_SEEDS);

        simpleItem(ModItems.BEETROOT_FIBER);
        simpleItem(ModItems.BEETROOT_CLOTH);

        simpleItem(ModItems.CRUSHED_BEETROOT_LEAVES);
        simpleItem(ModItems.BEETROOT_LEAVES);

        simpleItem(ModItems.VERDANT_NUGGET);
        simpleItem(ModItems.VERDANT_INGOT);

        evenSimplerBlockItem(ModBlocks.WORKSTATION);
        evenSimplerBlockItem(ModBlocks.ALTAR);
        evenSimplerBlockItem(ModBlocks.TOTEM);
        evenSimplerBlockItem(ModBlocks.GATEWAY);

        handheldItem(ModItems.AMETHYST_PICKAXE);
        handheldItem(ModItems.AMETHYST_AXE);
        handheldItem(ModItems.AMETHYST_SHOVEL);
        handheldItem(ModItems.AMETHYST_HOE);
        handheldItem(ModItems.BEETROOT_WAND);
        handheldItem(ModItems.TOOLS_WAND);
        

        evenSimplerBlockItem(ModBlocks.NEXUS);
        evenSimplerBlockItem(ModBlocks.GATEWAY);
        //evenSimplerBlockItem(ModBlocks.CORE);
        evenSimplerBlockItem(ModBlocks.COUNCIL);
        evenSimplerBlockItem(ModBlocks.ROBOTICS);
        evenSimplerBlockItem(ModBlocks.ANCHOR);
                 */
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheBeetrootMod.MODID,"item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(TheBeetrootMod.MODID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(TheBeetrootMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(TheBeetrootMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(TheBeetrootMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(TheBeetrootMod.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheBeetrootMod.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheBeetrootMod.MODID,"block/" + item.getId().getPath()));
    }
}
