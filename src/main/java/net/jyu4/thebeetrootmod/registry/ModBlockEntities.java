package net.jyu4.thebeetrootmod.registry;

import net.jyu4.thebeetrootmod.block.entity.AltarEntity;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, theBeetrootMod.MOD_ID);
    ///------------------------------///
    public static final RegistryObject<BlockEntityType<AltarEntity>> ALTAR_BE = BLOCK_ENTITIES.register("altar_be", () -> BlockEntityType.Builder.of(AltarEntity::new,ModBlocks.ALTAR.get()).build(null));
    ///------------------------------///
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
