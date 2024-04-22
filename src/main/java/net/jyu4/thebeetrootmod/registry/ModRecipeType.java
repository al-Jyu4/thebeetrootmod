package net.jyu4.thebeetrootmod.registry;

import net.jyu4.thebeetrootmod.recipe.WorkstationRecipe;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeType {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, theBeetrootMod.MOD_ID);

    public static final RegistryObject<RecipeType<WorkstationRecipe>> WORKSTATION = RECIPE_TYPES.register("workstation",
            () -> new RecipeType<WorkstationRecipe>() {
                @Override
                public String toString() {
                    return "workstation";
                }
            });

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}