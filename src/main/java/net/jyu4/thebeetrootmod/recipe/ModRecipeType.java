package net.jyu4.thebeetrootmod.recipe;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeType {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, TheBeetrootMod.MODID);

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