package net.jyu4.thebeetrootmod.registry;

import net.jyu4.thebeetrootmod.recipe.AltarRecipe;
import net.jyu4.thebeetrootmod.recipe.WorkstationRecipe;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, theBeetrootMod.MOD_ID);
    ///------------------------------///
    public static final RegistryObject<RecipeSerializer<WorkstationRecipe>> WORKSTATION = SERIALIZERS.register("workstation", () -> WorkstationRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<AltarRecipe>> ALTAR_SERIALIZER =SERIALIZERS.register("altar", () -> AltarRecipe.Serializer.INSTANCE);




    ///------------------------------///
    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
