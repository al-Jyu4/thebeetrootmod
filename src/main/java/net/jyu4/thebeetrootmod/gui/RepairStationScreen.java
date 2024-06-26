package net.jyu4.thebeetrootmod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.jyu4.thebeetrootmod.blockentity.BlockEntityRepairStation;
import net.jyu4.thebeetrootmod.net.MessageRepairItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;

public class RepairStationScreen extends AbstractContainerScreen<RepairStationMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(TheBeetrootMod.MODID, "textures/gui/gui_repair_station.png");

    private BlockEntityRepairStation be;
    private Player player;

    private int leftPos, topPos;
    Button repairButton;

    public RepairStationScreen(RepairStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.be = pMenu.getBlockEntity();
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

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);



        int energy =  this.menu.getEnergyStoredScaled();

        // foreground
        guiGraphics.fill(
                this.leftPos + 26,
                this.topPos + 17 + (52-energy),
                this.leftPos + 42,
                this.topPos + 69,
                0xFFCC2222
        );

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        if(isHovering(26, 17, 16, 52, mouseX, mouseY)){
            int energyStored = this.menu.getEnergy();
            int maxEnergy = this.menu.getMaxEnergy();
            Component text = Component.translatable("thebeetrootmod.juice_level").append(energyStored + " / " + maxEnergy);
            guiGraphics.renderTooltip(this.font, text, mouseX, mouseY); // render position mouseX and mouseY
        }
    }
}



