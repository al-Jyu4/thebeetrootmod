package net.jyu4.thebeetrootmod.recipe;

import com.google.gson.JsonObject;
import net.jyu4.thebeetrootmod.registry.ModRecipeType;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WorkstationRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    final List<Ingredient> ingredients = new ArrayList<>();
    final ItemStack result;

    public RecipeType<?> getType() {
        return ModRecipeType.WORKSTATION.get();
    }

    public WorkstationRecipe(ResourceLocation pId, List<Ingredient> ingredients, ItemStack pResult) {
        this.id = pId;
        this.ingredients.addAll(ingredients);
        this.result = pResult;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */

    public boolean matches(Container pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        // Check each ingredient to see if it matches the corresponding slot in the container.
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (!this.ingredients.get(i).test(pContainer.getItem(i))) {
                return false;
            }
        }

        // If there are fewer ingredients than slots, ensure the remaining slots are empty.
        for (int i = this.ingredients.size(); i < pContainer.getContainerSize(); i++) {
            if (!pContainer.getItem(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }


    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = pContainer.getItem(1).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 3 && pHeight >= 1;
    }

    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public boolean isIncomplete() {
        return this.ingredients.stream().anyMatch(Ingredient::isEmpty);
    }


    public static class Serializer implements RecipeSerializer<WorkstationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        // ... (other fields and methods)

        @Override
        public WorkstationRecipe fromJson(ResourceLocation p_266953_, JsonObject p_266720_) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                String key = "ingredient_" + i;
                if (p_266720_.has(key)) {
                    ingredients.add(Ingredient.fromJson(GsonHelper.getAsJsonObject(p_266720_, key)));
                }
            }
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_266720_, "result"));
            return new WorkstationRecipe(p_266953_, ingredients, itemstack);
        }

        @Override
        public WorkstationRecipe fromNetwork(ResourceLocation p_267117_, FriendlyByteBuf p_267316_) {
            List<Ingredient> ingredients = new ArrayList<>();
            int ingredientCount = p_267316_.readVarInt();
            for (int i = 0; i < ingredientCount; i++) {
                ingredients.add(Ingredient.fromNetwork(p_267316_));
            }
            ItemStack itemstack = p_267316_.readItem();
            return new WorkstationRecipe(p_267117_, ingredients, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_266746_, WorkstationRecipe p_266927_) {
            p_266746_.writeVarInt(p_266927_.ingredients.size());
            for (Ingredient ingredient : p_266927_.ingredients) {
                ingredient.toNetwork(p_266746_);
            }
            p_266746_.writeItem(p_266927_.result);
        }
    }
    /*
    public static class Serializer implements RecipeSerializer<WorkstationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        //public static final ResourceLocation ID = new ResourceLocation(BeetrootMod.MOD_ID, "workstation");

        @Override
        public WorkstationRecipe fromJson(ResourceLocation p_266953_, JsonObject p_266720_) {
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getNonNull(p_266720_, "ingredient_1"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getNonNull(p_266720_, "ingredient_2"));
            Ingredient ingredient3 = Ingredient.fromJson(GsonHelper.getNonNull(p_266720_, "ingredient_3"));
            Ingredient ingredient4 = Ingredient.fromJson(GsonHelper.getNonNull(p_266720_, "ingredient_4"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_266720_, "result"));
            return new WorkstationRecipe(p_266953_, ingredient1, ingredient2, ingredient3, ingredient4, itemstack);
        }

        @Override
        public WorkstationRecipe fromNetwork(ResourceLocation p_267117_, FriendlyByteBuf p_267316_) {
            Ingredient ingredient1 = Ingredient.fromNetwork(p_267316_);
            Ingredient ingredient2 = Ingredient.fromNetwork(p_267316_);
            Ingredient ingredient3 = Ingredient.fromNetwork(p_267316_);
            Ingredient ingredient4 = Ingredient.fromNetwork(p_267316_);
            ItemStack itemstack = p_267316_.readItem();
            return new WorkstationRecipe(p_267117_, ingredient1, ingredient2, ingredient3, ingredient4, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_266746_, WorkstationRecipe p_266927_) {
            p_266927_.ingredient_1.toNetwork(p_266746_);
            p_266927_.ingredient_2.toNetwork(p_266746_);
            p_266927_.ingredient_3.toNetwork(p_266746_);
            p_266927_.ingredient_4.toNetwork(p_266746_);
            p_266746_.writeItem(p_266927_.result);
        }
    }

     */
}

