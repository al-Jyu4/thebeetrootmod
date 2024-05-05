package net.jyu4.thebeetrootmod.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
/*
public class RecipeAltar implements Recipe<Container> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final Map<Ingredient, Integer> ingredients;

    public RecipeAltar(ResourceLocation id, ItemStack output, Map<Ingredient, Integer> ingredients) {
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        Map<Ingredient, Integer> required = new HashMap<>(ingredients);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
                if (entry.getKey().test(stack)) {
                    required.put(entry.getKey(), required.get(entry.getKey()) - stack.getCount());
                    if (required.get(entry.getKey()) <= 0) {
                        required.remove(entry.getKey());
                    }
                    break;
                }
            }
        }

        return required.isEmpty();
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeAltar.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeAltar.Type.INSTANCE;
    }

    public static class Type implements RecipeType<AltarRecipe> {
        public static final AltarRecipe.Type INSTANCE = new AltarRecipe.Type();
        public static final String ID = "altar";
    }

    public static class Serializer implements RecipeSerializer<RecipeAltar>{

        public static final RecipeAltar.Serializer INSTANCE = new RecipeAltar.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(TheBeetrootMod.MODID, "altar");

        @Override
        public RecipeAltar fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            Map<Ingredient, Integer> ingredients = parseIngredients(GsonHelper.getAsJsonObject(json, "ingredients"));
            return new RecipeAltar(recipeId, output, ingredients);
        }

        private Map<Ingredient, Integer> parseIngredients(JsonObject jsonObject) {
            Map<Ingredient, Integer> ingredients = new HashMap<>();
            for (Map.Entry<String, JsonElement> element : jsonObject.entrySet()) {
                ingredients.put(Ingredient.fromJson(element.getValue()), element.getValue().getAsInt());
            }
            return ingredients;
        }

        @Override
        public RecipeAltar fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ItemStack output = buffer.readItem();
            Map<Ingredient, Integer> ingredients = new HashMap<>();
            int size = buffer.readInt();
            for (int i = 0; i < size; i++) {
                ingredients.put(Ingredient.fromNetwork(buffer), buffer.readInt());
            }
            return new RecipeAltar(recipeId, output, ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RecipeAltar recipe) {
            buffer.writeItem(recipe.getResultItem());
            buffer.writeInt(recipe.ingredients.size());
            recipe.ingredients.forEach((ingredient, count) -> {
                ingredient.toNetwork(buffer);
                buffer.writeInt(count);
            });
        }
    }
}

 */