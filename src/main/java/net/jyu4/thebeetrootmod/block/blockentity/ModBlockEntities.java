package net.jyu4.thebeetrootmod.block.blockentity;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.jyu4.thebeetrootmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TheBeetrootMod.MODID);
    ///------------------------------///
    public static final RegistryObject<BlockEntityType<BlockEntityAltar>> ALTAR_BE = BLOCK_ENTITIES.register("altar_be", () -> BlockEntityType.Builder.of(BlockEntityAltar::new, ModBlocks.ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityRepairStation>> REPAIR_STATION_BE = BLOCK_ENTITIES.register("repair_station_be", () -> BlockEntityType.Builder.of(BlockEntityRepairStation::new, ModBlocks.REPAIR_STATION.get()).build(null));
    ///------------------------------///
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
