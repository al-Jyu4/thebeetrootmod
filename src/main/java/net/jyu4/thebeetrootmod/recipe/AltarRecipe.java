package net.jyu4.thebeetrootmod.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.jyu4.thebeetrootmod.item.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AltarRecipe implements Recipe<Container> {

    public static final byte DEFAULT_CRAFTING_COST_NUTRIENTS = 1;
    public static final int MAX_INGREDIENTS = 5;
    private final ResourceLocation registryKey;
    private final List<IngredientStack> ingredients;
    private final ItemStack result;

    private final NonNullList<Ingredient> vanillaIngredients;

    private final int cost;

    public AltarRecipe(ResourceLocation id, ItemStack result, List<IngredientStack> ingredients, int craftingCostNutrients) {
        registryKey = id;
        this.result = result;
        this.ingredients = ingredients;

        List<Ingredient> flatIngredients = RecipeUtil.flattenIngredientStacks(ingredients);
        vanillaIngredients = NonNullList.createWithCapacity(flatIngredients.size());
        vanillaIngredients.addAll(flatIngredients);

        cost = craftingCostNutrients;
    }

    public static boolean areRecipesEqual(AltarRecipe recipeA, AltarRecipe recipeB) {
        return recipeA.isRecipeEqual(recipeB);
    }

    @Override
    public ResourceLocation getId() {
        return registryKey;
    }

    public int getCraftingCostNutrients() {
        return cost;
    }

    public boolean isRecipeEqual(AltarRecipe other) {
        return registryKey.equals(other.getId());
    }

    public boolean isCraftable(StackedContents itemCounter) {
        for (IngredientStack ingredientStack : ingredients) {
            if (!ingredientStack.hasSufficientCount(itemCounter)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        int[] countedIngredients = new int[ingredients.size()];
        for (int idx = 0; idx < inv.getContainerSize(); idx++) {
            ItemStack stack = inv.getItem(idx);
            if (!stack.isEmpty()) {
                for (int i = 0; i < ingredients.size(); i++) {
                    if (ingredients.get(i).testItem(stack)) {
                        countedIngredients[i] += stack.getCount();
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < ingredients.size(); i++) {
            if (countedIngredients[i] < ingredients.get(i).count()) return false;
        }

        return true;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 0;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return vanillaIngredients;
    }

    public List<IngredientStack> getIngredientQuantities() {
        return ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WorkstationRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AltarRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "altar";
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModItems.GOLDEN_BEETROOT.get());
    }

    public static class Serializer implements RecipeSerializer<AltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public AltarRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            List<IngredientStack> ingredients = RecipeUtil.readIngredientStacks(GsonHelper.getAsJsonArray(json, "ingredients"));

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for recipe");
            }

            if (ingredients.size() > MAX_INGREDIENTS) {
                throw new JsonParseException("Too many ingredients for recipe. The maximum is " + MAX_INGREDIENTS);
            }

            ItemStack resultStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            int cost = GsonHelper.getAsInt(json, "nutrientsCost", DEFAULT_CRAFTING_COST_NUTRIENTS);


            return new AltarRecipe(recipeId, resultStack, ingredients, cost);
        }

        @Nullable
        @Override
        public AltarRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ItemStack resultStack = buffer.readItem();

            int ingredientCount = buffer.readVarInt();
            List<IngredientStack> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientCount; i++) {
                ingredients.add(IngredientStack.fromNetwork(buffer));
            }

            int craftingCost = buffer.readVarInt();

            return new AltarRecipe(recipeId, resultStack, ingredients, craftingCost);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AltarRecipe recipe) {
            buffer.writeItem(recipe.result);

            buffer.writeVarInt(recipe.ingredients.size());
            for (IngredientStack ingredientStack : recipe.ingredients) {
                ingredientStack.toNetwork(buffer);
            }

            buffer.writeVarInt(recipe.getCraftingCostNutrients());
        }

    }

}
