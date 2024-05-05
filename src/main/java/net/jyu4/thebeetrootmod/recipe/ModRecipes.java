package net.jyu4.thebeetrootmod.recipe;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.jyu4.thebeetrootmod.recipe.ModRecipeType.RECIPE_TYPES;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TheBeetrootMod.MODID);
    ///------------------------------///
    public static final RegistryObject<RecipeSerializer<WorkstationRecipe>> WORKSTATION = SERIALIZERS.register("workstation", () -> WorkstationRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<AltarRecipe>> ALTAR = SERIALIZERS.register("altar", () -> AltarRecipe.Serializer.INSTANCE);




    ///------------------------------///

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }


}
