package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
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
import org.mineplugin.locusazzurro.pyrotechnicraft.network.ServerboundFireworkOrbCraftingTablePacket;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkOrbCraftingTableContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;

import java.util.Optional;

public class FireworkOrbCraftingTableScreen extends AbstractContainerScreen<FireworkOrbCraftingTableContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/gui/firework_orb_crafting_table.png");
    private Button craftButton;
    private static final int BG_X = 176;
    private static final int BG_Y = 222;

    private static final Component TEXT_COLORS = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.label.colors");
    private static final Component TEXT_FADE_COLORS = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.label.fade_colors");
    private static final Component CORE_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.core_slot");
    private static final Component PATTERN_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.pattern_slot");
    private static final Component FORCE_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.force_slot");
    private static final Component SPARK_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.spark_slot");
    private static final Component DAMAGE_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.damage_slot");
    private static final Component TRAIL_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.trail_slot");
    private static final Component SPARKLE_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.sparkle_slot");
    private static final Component COLOR_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.color_slot");
    private static final Component FADE_COLOR_SLOT_DESCRIPTION = Component.translatable("screen.pyrotechnicraft.firework_orb_crafting_table.desc.fade_color_slot");

    public static final int CORE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.CORE_SLOT_ID; //0
    public static final int PATTERN_SLOT_ID = FireworkOrbCraftingTableBlockEntity.PATTERN_SLOT_ID; //1
    public static final int FORCE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.FORCE_SLOT_ID; //2
    public static final int SPARK_SLOT_ID = FireworkOrbCraftingTableBlockEntity.SPARK_SLOT_ID; //3
    public static final int DAMAGE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.DAMAGE_SLOT_ID; //4
    public static final int TRAIL_SLOT_ID = FireworkOrbCraftingTableBlockEntity.TRAIL_SLOT_ID; //5
    public static final int SPARKLE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.SPARKLE_SLOT_ID; //6
    public static final int COLOR_SLOT_ID_START = FireworkOrbCraftingTableBlockEntity.COLOR_SLOT_ID_START; //7
    public static final int COLOR_SLOT_ID_END = FireworkOrbCraftingTableBlockEntity.COLOR_SLOT_ID_END; //14
    public static final int FADE_COLOR_SLOT_ID_START = FireworkOrbCraftingTableBlockEntity.FADE_COLOR_SLOT_ID_START; //15
    public static final int FADE_COLOR_SLOT_ID_END = FireworkOrbCraftingTableBlockEntity.FADE_COLOR_SLOT_ID_END; //22
    public static final int OUTPUT_SLOT_ID = FireworkOrbCraftingTableBlockEntity.OUTPUT_SLOT_ID; //23

    public FireworkOrbCraftingTableScreen(FireworkOrbCraftingTableContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        BlockPos pos = FireworkOrbCraftingTableScreen.this.menu.getBlockEntity().getBlockPos();
        PacketHandler.INSTANCE.sendToServer(new ServerboundFireworkOrbCraftingTablePacket(pos));
    };

    @Override
    protected void containerTick() {
        super.containerTick();
        this.craftButton.active = menu.hasValidItemsForCrafting();
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
        this.renderOnboardingTooltips(gui, mouseX, mouseY);
    }

    private void renderOnboardingTooltips(GuiGraphics gui, int mouseX, int mouseY){
        Optional<Component> optional = Optional.empty();
        if (this.hoveredSlot != null){
            ItemStack hoveredSlotItem = this.hoveredSlot.getItem();
            if (hoveredSlotItem.isEmpty()){
                int index = hoveredSlot.index;
                optional = switch (index){
                    case CORE_SLOT_ID -> Optional.of(CORE_SLOT_DESCRIPTION);
                    case PATTERN_SLOT_ID -> Optional.of(PATTERN_SLOT_DESCRIPTION);
                    case FORCE_SLOT_ID -> Optional.of(FORCE_SLOT_DESCRIPTION);
                    case SPARK_SLOT_ID -> Optional.of(SPARK_SLOT_DESCRIPTION);
                    case DAMAGE_SLOT_ID -> Optional.of(DAMAGE_SLOT_DESCRIPTION);
                    case TRAIL_SLOT_ID -> Optional.of(TRAIL_SLOT_DESCRIPTION);
                    case SPARKLE_SLOT_ID -> Optional.of(SPARKLE_SLOT_DESCRIPTION);
                    default -> Optional.empty();
                };
                if (index >= COLOR_SLOT_ID_START && index <= COLOR_SLOT_ID_END) optional = Optional.of(COLOR_SLOT_DESCRIPTION);
                if (index >= FADE_COLOR_SLOT_ID_START && index <= FADE_COLOR_SLOT_ID_END) optional = Optional.of(FADE_COLOR_SLOT_DESCRIPTION);
            }
        }
        optional.ifPresent(component -> gui.renderTooltip(this.font, this.font.split(component, 115), mouseX, mouseY));
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        gui.drawString(Minecraft.getInstance().font, this.title, titleLabelX, titleLabelY, 0xffffff);
        gui.drawString(Minecraft.getInstance().font, TEXT_COLORS, 98, 22, 0xffffff);
        gui.drawString(Minecraft.getInstance().font, TEXT_FADE_COLORS, 98, 76, 0xffffff);
    }

    @Override
    public void renderBg(GuiGraphics gui, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        gui.blit(GUI, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.imageWidth, this.imageHeight);
    }


}
