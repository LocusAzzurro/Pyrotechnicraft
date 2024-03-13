package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.PacketHandler;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.ServerboundFireworkMissileCraftingTablePacket;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkMissileCraftingTableContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;

import java.util.Optional;

public class FireworkMissileCraftingTableScreen extends AbstractContainerScreen<FireworkMissileCraftingTableContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/gui/firework_missile_crafting_table.png");
    private Button craftButton;
    private static final int BG_X = 176;
    private static final int BG_Y = 222;

    public static final int HOMING_MODULE_SLOT_ID = FireworkMissileCraftingTableBlockEntity.HOMING_MODULE_SLOT_ID; //0
    public static final int FUSE_SLOT_ID = FireworkMissileCraftingTableBlockEntity.FUSE_SLOT_ID; //1
    public static final int STAR_SLOT_ID_START = FireworkMissileCraftingTableBlockEntity.STAR_SLOT_ID_START; //2
    public static final int STAR_SLOT_ID_END = FireworkMissileCraftingTableBlockEntity.STAR_SLOT_ID_END; //9
    public static final int SPEED_SLOT_ID = FireworkMissileCraftingTableBlockEntity.SPEED_SLOT_ID; //10
    public static final int FLIGHT_TIME_SLOT_ID = FireworkMissileCraftingTableBlockEntity.FLIGHT_TIME_SLOT_ID; //11
    public static final int SPARK_COLOR_SLOT_ID = FireworkMissileCraftingTableBlockEntity.SPARK_COLOR_SLOT_ID; //12
    public static final int WRAPPING_PAPER_SLOT_ID = FireworkMissileCraftingTableBlockEntity.WRAPPING_PAPER_SLOT_ID ; //13
    public static final int BASE_COLOR_SLOT_ID = FireworkMissileCraftingTableBlockEntity.BASE_COLOR_SLOT_ID; //14;
    public static final int PATTERN_COLOR_SLOT_ID = FireworkMissileCraftingTableBlockEntity.PATTERN_COLOR_SLOT_ID; //15;
    public static final int OUTPUT_SLOT_ID = FireworkMissileCraftingTableBlockEntity.OUTPUT_SLOT_ID; //16

    private static final Component HOMING_MODULE_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.homing_module_slot");
    private static final Component FUSE_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.fuse_slot");
    private static final Component STAR_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.star_slot");
    private static final Component SPEED_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.speed_slot");
    private static final Component FLIGHT_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.flight_slot");
    private static final Component SPARK_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.spark_slot");
    private static final Component WRAPPING_PAPER_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.wrapping_paper_slot");
    private static final Component BASE_COLOR_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.base_color_slot");
    private static final Component PATTERN_COLOR_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_missile_crafting_table.desc.pattern_color_slot");

    public FireworkMissileCraftingTableScreen(FireworkMissileCraftingTableContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = BG_X;
        this.imageHeight = BG_Y;
    }

    @Override
    protected void init() {
        super.init();
        this.craftButton = new CraftButton(getGuiLeft() + 36, getGuiTop() + 77, pressAction);
        this.addRenderableWidget(craftButton);
    }

    private Button.OnPress pressAction = button -> {
        BlockPos pos = FireworkMissileCraftingTableScreen.this.menu.getBlockEntity().getBlockPos();
        PacketHandler.INSTANCE.sendToServer(new ServerboundFireworkMissileCraftingTablePacket(pos));
    };

    @Override
    protected void containerTick() {
        super.containerTick();
        this.craftButton.active = menu.hasValidItemsForCrafting();
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderOnboardingTooltips(gui, mouseX, mouseY);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    private void renderOnboardingTooltips(GuiGraphics gui, int mouseX, int mouseY){
        Optional<Component> optional = Optional.empty();
        if (this.hoveredSlot != null){
            ItemStack hoveredSlotItem = this.hoveredSlot.getItem();
            if (hoveredSlotItem.isEmpty()){
                int index = hoveredSlot.index;
                optional = switch (index){
                    case HOMING_MODULE_SLOT_ID -> Optional.of(HOMING_MODULE_SLOT_DESCRIPTION);
                    case FUSE_SLOT_ID -> Optional.of(FUSE_SLOT_DESCRIPTION);
                    case SPEED_SLOT_ID -> Optional.of(SPEED_SLOT_DESCRIPTION);
                    case FLIGHT_TIME_SLOT_ID -> Optional.of(FLIGHT_SLOT_DESCRIPTION);
                    case SPARK_COLOR_SLOT_ID -> Optional.of(SPARK_SLOT_DESCRIPTION);
                    case WRAPPING_PAPER_SLOT_ID -> Optional.of(WRAPPING_PAPER_SLOT_DESCRIPTION);
                    case BASE_COLOR_SLOT_ID -> Optional.of(BASE_COLOR_SLOT_DESCRIPTION);
                    case PATTERN_COLOR_SLOT_ID -> Optional.of(PATTERN_COLOR_SLOT_DESCRIPTION);
                    default -> Optional.empty();
                };
                if (index >= STAR_SLOT_ID_START && index <= STAR_SLOT_ID_END) optional = Optional.of(STAR_SLOT_DESCRIPTION);
            }
        }
        optional.ifPresent(component -> gui.renderTooltip(this.font, this.font.split(component, 115), mouseX, mouseY));
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        gui.drawString(Minecraft.getInstance().font, this.title, titleLabelX, titleLabelY, 0xffffff);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        gui.blit(GUI, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.imageWidth, this.imageHeight);
    }
}
