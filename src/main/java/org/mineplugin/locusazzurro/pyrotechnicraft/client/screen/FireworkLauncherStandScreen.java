package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.PacketHandler;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.ServerboundFireworkLauncherStandTogglePacket;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkLauncherStandContainer;

public class FireworkLauncherStandScreen extends AbstractContainerScreen<FireworkLauncherStandContainer> {

    public static final Font FONT = Minecraft.getInstance().font;
    private final ResourceLocation GUI = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/gui/firework_launcher_stand.png");

    private Button rotationDecreaseButton;
    private Button rotationIncreaseButton;
    private Button angleDecreaseButton;
    private Button angleIncreaseButton;
    private static final int BG_X = 176;
    private static final int BG_Y = 222;

    private static final Component TEXT_ROTATION = Component.translatable("screen.pyrotechnicraft.firework_launcher_stand.label.rotation");
    private static final Component TEXT_ANGLE = Component.translatable("screen.pyrotechnicraft.firework_launcher_stand.label.angle");
    public FireworkLauncherStandScreen(FireworkLauncherStandContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = BG_X;
        this.imageHeight = BG_Y;
    }

    private Button.OnPress createTogglePacket(byte data, short value){
        return pButton -> {
            BlockPos pos = FireworkLauncherStandScreen.this.menu.getBlockEntity().getBlockPos();
            PacketHandler.INSTANCE.sendToServer(new ServerboundFireworkLauncherStandTogglePacket(pos, data, value));
        };
    }

    @Override
    protected void init() {
        super.init();
        this.rotationDecreaseButton = new LauncherToggleButton(getGuiLeft() + 25, getGuiTop() + 60, false, createTogglePacket((byte) 0, (short) -5));
        this.rotationIncreaseButton = new LauncherToggleButton(getGuiLeft() + 119, getGuiTop() + 60, true, createTogglePacket((byte) 0, (short) 5));
        this.angleDecreaseButton = new LauncherToggleButton(getGuiLeft() + 25, getGuiTop() + 104, false, createTogglePacket((byte) 1, (short) 5));
        this.angleIncreaseButton = new LauncherToggleButton(getGuiLeft() + 119, getGuiTop() + 104, true, createTogglePacket((byte) 1, (short) -5));
        this.addRenderableWidget(rotationDecreaseButton);
        this.addRenderableWidget(rotationIncreaseButton);
        this.addRenderableWidget(angleDecreaseButton);
        this.addRenderableWidget(angleIncreaseButton);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, FONT, this.title, titleLabelX, titleLabelY, 0xffffff);
        drawCenteredString(matrixStack, FONT, String.valueOf(menu.getRotation()), 88, 66, 0xffffff);
        drawCenteredString(matrixStack, FONT, String.valueOf(-menu.getAngle()), 88, 110, 0xffffff);
        drawCenteredString(matrixStack, FONT, TEXT_ROTATION, 88, 46, 0xffffff);
        drawCenteredString(matrixStack, FONT, TEXT_ANGLE, 88, 90, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(pPoseStack, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.imageWidth, this.imageHeight);
    }
}
