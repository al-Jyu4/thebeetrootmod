package net.jyu4.thebeetrootmod.gui;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;


@OnlyIn(Dist.CLIENT)
public class WorkstationScreen extends ItemCombinerScreen<WorkstationMenu> {
    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation(TheBeetrootMod.MODID, "textures/gui/workstation_gui.png");
    private static final ResourceLocation EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM = new ResourceLocation("item/empty_slot_smithing_template_armor_trim");
    private static final ResourceLocation EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE = new ResourceLocation("item/empty_slot_smithing_template_netherite_upgrade");
    private static final Component MISSING_TEMPLATE_TOOLTIP = Component.translatable("thebeetrootmod.workstation.slot_1_tooltip");
    private static final Component ERROR_TOOLTIP = Component.translatable("thebeetrootmod.workstation.error_tooltip");
    private static final Component SLOT_2_TOOLTIP = Component.translatable("thebeetrootmod.workstation.slot_2_tooltip");
    private static final Component SLOT_3_TOOLTIP = Component.translatable("thebeetrootmod.workstation.slot_3_tooltip");
    private static final List<ResourceLocation> EMPTY_SLOT_SMITHING_TEMPLATES = List.of(EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM, EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE);
    private static final int TITLE_LABEL_X = 44;
    private static final int TITLE_LABEL_Y = 15;
    private static final int ERROR_ICON_WIDTH = 28;
    private static final int ERROR_ICON_HEIGHT = 21;
    private static final int ERROR_ICON_X = 101;
    private static final int ERROR_ICON_Y = 46;
    private static final int TOOLTIP_WIDTH = 115;
    private final CyclingSlotBackground templateIcon = new CyclingSlotBackground(0);
    private final CyclingSlotBackground baseIcon = new CyclingSlotBackground(1);
    private final CyclingSlotBackground additionalIcon = new CyclingSlotBackground(2);

    public WorkstationScreen(WorkstationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, SMITHING_LOCATION);
        this.titleLabelX = 44;
        this.titleLabelY = 15;
    }
    /*
    public void containerTick() {
        super.containerTick();
        Optional<SmithingTemplateItem> optional = this.getTemplateItem();
        this.templateIcon.tick(EMPTY_SLOT_SMITHING_TEMPLATES);
        this.baseIcon.tick(optional.map(SmithingTemplateItem::getBaseSlotEmptyIcons).orElse(List.of()));
        this.additionalIcon.tick(optional.map(SmithingTemplateItem::getAdditionalSlotEmptyIcons).orElse(List.of()));
    }

     */

    private Optional<SmithingTemplateItem> getTemplateItem() {
        ItemStack itemstack = this.menu.getSlot(0).getItem();
        if (!itemstack.isEmpty()) {
            Item item = itemstack.getItem();
            if (item instanceof SmithingTemplateItem) {
                SmithingTemplateItem smithingtemplateitem = (SmithingTemplateItem)item;
                return Optional.of(smithingtemplateitem);
            }
        }

        return Optional.empty();
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        //this.renderOnboardingTooltips(pGuiGraphics, pMouseX, pMouseY);
    }

    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        this.templateIcon.render(this.menu, pGuiGraphics, pPartialTick, this.leftPos, this.topPos);
        this.baseIcon.render(this.menu, pGuiGraphics, pPartialTick, this.leftPos, this.topPos);
        this.additionalIcon.render(this.menu, pGuiGraphics, pPartialTick, this.leftPos, this.topPos);
    }

    protected void renderErrorIcon(GuiGraphics pGuiGraphics, int pX, int pY) {
        if (this.hasRecipeError()) {
            pGuiGraphics.blit(SMITHING_LOCATION, pX + 101, pY + 46, this.imageWidth, 0, 28, 21);
        }

    }
    /*
    private void renderOnboardingTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        Optional<Component> optional = Optional.empty();
        if (this.hasRecipeError() && this.isHovering(110, 46, 28, 21, (double)pMouseX, (double)pMouseY)) {
            optional = Optional.of(ERROR_TOOLTIP);
        }

        if (this.hoveredSlot != null) {
            ItemStack itemstack = this.menu.getSlot(0).getItem();
            ItemStack itemstack1 = this.hoveredSlot.getItem();
            if (itemstack.isEmpty()) {
                if (this.hoveredSlot.index == 0) {
                    optional = Optional.of(MISSING_TEMPLATE_TOOLTIP);
                }
            } else {
                Item item = itemstack.getItem();
                if (item instanceof SmithingTemplateItem) {
                    SmithingTemplateItem smithingtemplateitem = (SmithingTemplateItem)item;
                    if (itemstack1.isEmpty()) {
                        if (this.hoveredSlot.index == 1) {
                            //optional = Optional.of(smithingtemplateitem.getBaseSlotDescription());
                            optional = Optional.of(SLOT_2_TOOLTIP);
                        } else if (this.hoveredSlot.index == 2) {
                            //optional = Optional.of(smithingtemplateitem.getAdditionSlotDescription());
                            optional = Optional.of(SLOT_3_TOOLTIP);
                        }
                    }
                }
            }
        }

        optional.ifPresent((p_280863_) -> {
            pGuiGraphics.renderTooltip(this.font, this.font.split(p_280863_, 115), pMouseX, pMouseY);
        });
    }

     */

    private boolean hasRecipeError() {
        return !this.menu.getSlot(this.menu.getResultSlot()).hasItem();
    }
}
