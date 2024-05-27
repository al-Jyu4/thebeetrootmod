package net.jyu4.thebeetrootmod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityRepairStation;
import net.jyu4.thebeetrootmod.net.MessageRepairItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepairStationScreen extends AbstractContainerScreen<RepairStationMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(TheBeetrootMod.MODID, "textures/gui/gui_repair_station.png");

    private BlockEntityRepairStation be;
    private Player player;

    private final CyclingSlotBackground slotIcon = new CyclingSlotBackground(0);
    private static final ResourceLocation BEETROOT = new ResourceLocation("item/empty_slot_smithing_template_armor_trim");
    private static final ResourceLocation TOOLS = new ResourceLocation("item/empty_slot_smithing_template_netherite_upgrade");
    private static final Component TOOLTIP = Component.translatable("thebeetrootmod.workstation.error_tooltip");
    private static final List<ResourceLocation> EMPTY_SLOT_SMITHING_TEMPLATES = List.of(BEETROOT, TOOLS);

    Button repairButton;
    private int leftPos, topPos;


    public RepairStationScreen(RepairStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.be = pMenu.getTile();
        this.player = pPlayerInventory.player;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) /2;
        this.topPos = (this.height - this.imageHeight) /2;

        this.repairButton = addRenderableWidget(Button.builder(Component.translatable("button.thebeetrootmod.repair"), button -> {
            SimpleChannel channel = TheBeetrootMod.SIMPLE_CHANNEL;
            TheBeetrootMod.SIMPLE_CHANNEL.sendToServer(new MessageRepairItem(be.getBlockPos(), player, true));
        }).bounds(leftPos + 115, topPos + 34, 54, 19).build());

    }

    public void containerTick() {
        super.containerTick();
        this.slotIcon.tick(EMPTY_SLOT_SMITHING_TEMPLATES);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        drawEnergy(guiGraphics);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        //renderProgressArrow(guiGraphics, x, y);
        //this.renderOnboardingTooltips(guiGraphics, pMouseX, pMouseY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

    }

    private void renderOnboardingTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        Optional<Component> optional = Optional.empty();
        if (this.hoveredSlot != null && this.hoveredSlot.index == 0) {
            optional = Optional.of(TOOLTIP);
        }
        optional.ifPresent((p_280863_) -> {
            pGuiGraphics.renderTooltip(this.font, this.font.split(p_280863_, 115), pMouseX, pMouseY);
        });
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        if (mouseX >= leftPos + 122 && mouseX <= leftPos + 16 + 122) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.energy", be.getStoredEnergy()).getVisualOrderText());
                guiGraphics.renderTooltip(font, list, mouseX - leftPos, mouseY - topPos);
            }
        }
    }

    public void drawEnergy(GuiGraphics guiGraphics) {
        float perc = getEnergy();

        int texX = 176;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 122;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(TEXTURE, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public float getEnergy() {
        return ((float) be.getStoredEnergy()) / ((float) be.maxStorage);
    }
}



