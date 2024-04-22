package net.jyu4.thebeetrootmod.recipe;

import com.google.gson.JsonObject;
import net.jyu4.thebeetrootmod.theBeetrootMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class AltarRecipe implements Recipe<SimpleContainer> {
    private final List<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public AltarRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    public AltarRecipe(ResourceLocation id, List<Ingredient> inputItems, ItemStack itemstack) {
        this.inputItems = inputItems;
        this.output = itemstack;
        this.id = id;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        // Check each ingredient to see if it matches the corresponding slot in the container.
        for (int i = 3; i < (this.inputItems.size() + 3); i++) {
            if (!this.inputItems.get(i).test(pContainer.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AltarRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "altar";
    }

    public static class Serializer implements RecipeSerializer<AltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(theBeetrootMod.MOD_ID, "altar");

        @Override
        public AltarRecipe fromJson(ResourceLocation p_266953_, JsonObject p_266720_) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                String key = "ingredient_" + i;
                if (p_266720_.has(key)) {
                    ingredients.add(Ingredient.fromJson(GsonHelper.getAsJsonObject(p_266720_, key)));
                }
            }
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_266720_, "result"));
            return new AltarRecipe(p_266953_, ingredients, itemstack);
        }

        @Override
        public AltarRecipe fromNetwork(ResourceLocation p_267117_, FriendlyByteBuf p_267316_) {
            List<Ingredient> ingredients = new ArrayList<>();
            int ingredientCount = p_267316_.readVarInt();
            for (int i = 0; i < ingredientCount; i++) {
                ingredients.add(Ingredient.fromNetwork(p_267316_));
            }
            ItemStack itemstack = p_267316_.readItem();
            return new AltarRecipe(p_267117_, ingredients, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_266746_, AltarRecipe p_266927_) {
            p_266746_.writeVarInt(p_266927_.inputItems.size());
            for (Ingredient ingredient : p_266927_.inputItems) {
                ingredient.toNetwork(p_266746_);
            }
            p_266746_.writeItem(p_266927_.output);
        }
    }
        /*
        @Override
        public AltarRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(5, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new AltarRecipe(inputs, output, pRecipeId);
        }

        @Override
        public @Nullable AltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new AltarRecipe(inputs, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AltarRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }

         */
}
