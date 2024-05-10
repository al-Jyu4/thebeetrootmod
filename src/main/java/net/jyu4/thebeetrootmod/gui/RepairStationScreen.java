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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.simple.SimpleChannel;

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
            SimpleChannel channel = TheBeetrootMod.SIMPLE_CHANNEL; // Your network channel
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

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        //renderProgressArrow(guiGraphics, x, y);
        this.renderOnboardingTooltips(guiGraphics, pMouseX, pMouseY);
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
}
