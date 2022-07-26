package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.PacketHandler;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.ServerboundFireworkLauncherStandTogglePacket;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.ServerboundFireworkMissileCraftingTablePacket;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkLauncherStandContainer;

public class FireworkLauncherStandScreen extends AbstractContainerScreen<FireworkLauncherStandContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/gui/firework_launcher_stand.png");

    private Button toggleButton;
    private static final int BG_X = 176;
    private static final int BG_Y = 222;

    public FireworkLauncherStandScreen(FireworkLauncherStandContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = BG_X;
        this.imageHeight = BG_Y;
    }

    private Button.OnPress pressAction = button -> {
        BlockPos pos = FireworkLauncherStandScreen.this.menu.getBlockEntity().getBlockPos();
        PacketHandler.INSTANCE.sendToServer(new ServerboundFireworkLauncherStandTogglePacket(pos, (byte) 0, (short) 5));
    };

    @Override
    protected void init() {
        super.init();
        this.toggleButton = new LauncherToggleButton(getGuiLeft() + 36, getGuiTop() + 77, pressAction);
        this.addRenderableWidget(toggleButton);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, Minecraft.getInstance().font, this.title, titleLabelX, titleLabelY, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, String.valueOf(menu.getRotation()), titleLabelX, titleLabelY + 40, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(pPoseStack, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.imageWidth, this.imageHeight);
    }
}
