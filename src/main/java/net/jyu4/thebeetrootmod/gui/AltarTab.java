package net.jyu4.thebeetrootmod.gui;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public final class AltarTab {
    private static final String JSON_KEY = "bio_forge_tab";
    private final int sortPriority;
    private final Item iconItem;

    public AltarTab(int sortPriority, Item iconItem) {
        this.sortPriority = sortPriority;
        this.iconItem = iconItem;
    }

    public AltarTab(Item itemSupplier) {
        this(0, itemSupplier);
    }

    public static AltarTab fromJson(JsonObject json) {
        String categoryId = GsonHelper.getAsString(json, JSON_KEY);
        AltarTab category = AltarTabs.REGISTRY.get().getValue(new ResourceLocation(categoryId));
        if (category == null) {
            throw new JsonSyntaxException("Unknown tab '%s'".formatted(categoryId));
        }
        return category;
    }

    public static AltarTab fromNetwork(FriendlyByteBuf buffer) {
        AltarTab value = AltarTabs.REGISTRY.get().getValue(buffer.readResourceLocation());
        return value != null ? value : AltarTabs.MISC.get();
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(AltarTabs.REGISTRY.get().getKey(this));
    }

    public void toJson(JsonObject json) {
        json.addProperty(JSON_KEY, AltarTabs.REGISTRY.get().getKey(this).toString());
    }

    public ItemStack getIcon() {
        return new ItemStack(iconItem);
    }

    public String enumId() {
        return enumIdFrom(AltarTabs.REGISTRY.get().getKey(this));
    }

    public static String enumIdFrom(ResourceLocation key) {
        return JSON_KEY + "_" + key.getNamespace() + "_" + key.getPath();
    }

    public String translationKey() {
        return AltarTabs.REGISTRY.get().getKey(this).toLanguageKey(JSON_KEY);
    }

    public int sortPriority() {
        return sortPriority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AltarTab) obj;
        return AltarTabs.REGISTRY.get().getKey(this).equals(AltarTabs.REGISTRY.get().getKey(that)) && this.sortPriority == that.sortPriority && Objects.equals(this.iconItem, that.iconItem);
    }

	/*@Override
	public int hashCode() {
		return 31 * AltarTabs.REGISTRY.get().getKey(this).getNamespace().hashCode() + AltarTabs.REGISTRY.get().getKey(this).getPath().hashCode();
	}*/

    @Override
    public String toString() {
        return "AltarTab[" + "id=" + AltarTabs.REGISTRY.get().getKey(this) + ", " + "sortPriority=" + sortPriority + ", " + "iconSupplier=" + iconItem + ']';
    }

}
