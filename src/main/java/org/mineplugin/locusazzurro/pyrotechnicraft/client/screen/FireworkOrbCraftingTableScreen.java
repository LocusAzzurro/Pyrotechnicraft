package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkOrbCraftingTableContainer;

public class FireworkOrbCraftingTableScreen extends AbstractContainerScreen<FireworkOrbCraftingTableContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/gui/firework_orb_crafting_table.png");
    private Button craftButton;
    private static final int BG_X = 176;
    private static final int BG_Y = 222;

    public FireworkOrbCraftingTableScreen(FireworkOrbCraftingTableContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = BG_X;
        this.imageHeight = BG_Y;
    }

    @Override
    protected void init() {
        super.init();
        this.craftButton = new CraftButton(getGuiLeft() + 10, getGuiTop() + 20);
        this.addRenderableWidget(craftButton);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.craftButton.active = menu.hasValidItems();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, Minecraft.getInstance().font, "Crafting", 10, 10, 0xffffff);
    }

    @Override
    public void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(pPoseStack, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.imageWidth, this.imageHeight);
    }

    private void testItem(){
        System.out.println(this.menu.getSlot(0).getItem());
    }
    class CraftButton extends Button {
        public CraftButton(int x, int y) {
            super(x, y, 20, 20, TextComponent.EMPTY, (button) -> FireworkOrbCraftingTableScreen.this.testItem());
        }
    }
}
