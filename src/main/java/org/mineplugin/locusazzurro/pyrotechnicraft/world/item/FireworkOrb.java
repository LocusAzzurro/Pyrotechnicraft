package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;

import java.util.List;
import java.util.Optional;

public class FireworkOrb extends Item {

    public static final String TOOLTIP_PREFIX = "item." + Pyrotechnicraft.MOD_ID + ".firework_orb.";
    public static final TranslatableComponent TEXT_SHAPE_TITLE = new TranslatableComponent(TOOLTIP_PREFIX + "shape.title");
    public static final String TEXT_SHAPE_HANDLE = TOOLTIP_PREFIX + "shape.";
    public static final TranslatableComponent TEXT_COLOR_TITLE = new TranslatableComponent(TOOLTIP_PREFIX + "color.title");
    public static final TranslatableComponent TEXT_FADE_COLOR_TITLE = new TranslatableComponent(TOOLTIP_PREFIX + "fade_color.title");
    public static final TranslatableComponent TEXT_COLOR_SYMBOL = new TranslatableComponent(TOOLTIP_PREFIX + "color.symbol");
    public static final TranslatableComponent TEXT_FORCE = new TranslatableComponent(TOOLTIP_PREFIX + "property.force");
    public static final TranslatableComponent TEXT_SPARKS = new TranslatableComponent(TOOLTIP_PREFIX + "property.sparks");
    public static final TranslatableComponent TEXT_DAMAGE = new TranslatableComponent(TOOLTIP_PREFIX + "property.damage");
    public static final TranslatableComponent TEXT_TRAIL = new TranslatableComponent(TOOLTIP_PREFIX + "property.trail");
    public static final TranslatableComponent TEXT_SPARKLE = new TranslatableComponent(TOOLTIP_PREFIX + "property.sparkle");
    public FireworkOrb()  {
        super(new Properties().tab(Pyrotechnicraft.CREATIVE_TAB));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        appendHoverText(tag, pTooltipComponents);
    }

    public static void appendHoverText(CompoundTag tag, List<Component> pTooltipComponents){
        ExplosionShape shape = ExplosionShape.byName(tag.getString("Shape"));
        int[] colors = tag.contains("Colors") ? tag.getIntArray("Colors") : new int[0];
        int[] fadeColors = tag.contains("FadeColors") ? tag.getIntArray("FadeColors") : new int[0];
        float force = tag.getFloat("Force");
        int sparks = tag.getInt("Sparks");
        double damage = tag.getDouble("Damage");

        pTooltipComponents.add(TEXT_SHAPE_TITLE.copy().append(shapeKey(shape)).withStyle(ChatFormatting.GRAY));
        colorSymbolChain(colors).ifPresent(component -> pTooltipComponents.add(TEXT_COLOR_TITLE.copy().withStyle(ChatFormatting.GRAY).append(component)));
        colorSymbolChain(fadeColors).ifPresent(component -> pTooltipComponents.add(TEXT_FADE_COLOR_TITLE.copy().withStyle(ChatFormatting.GRAY).append(component)));
        pTooltipComponents.add(TEXT_FORCE.copy().append(new TextComponent(String.format("%.1f", force))).withStyle(ChatFormatting.GRAY));
        if (!shape.hasCostByDefault()) pTooltipComponents.add(TEXT_SPARKS.copy().append(new TextComponent(String.valueOf(sparks))).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(TEXT_DAMAGE.copy().append(new TextComponent(String.format("%.1f", damage))).withStyle(ChatFormatting.GRAY));
        if (tag.getBoolean("Trail")) pTooltipComponents.add(TEXT_TRAIL.copy().withStyle(ChatFormatting.GRAY));
        if (tag.getBoolean("Sparkle")) pTooltipComponents.add(TEXT_SPARKLE.copy().withStyle(ChatFormatting.GRAY));
    }

    private static Optional<MutableComponent> colorSymbolChain(int[] colors){
        if (colors.length > 0){
            MutableComponent colorsSymbols = TEXT_COLOR_SYMBOL.copy().withStyle(Style.EMPTY.withColor(colors[0]));
            for (int i = 1; i < colors.length; i++){
                colorsSymbols.append(TEXT_COLOR_SYMBOL.copy().withStyle(Style.EMPTY.withColor(colors[i])));
            }
            return Optional.of(colorsSymbols);
        }
        return Optional.empty();
    }

    private static TranslatableComponent shapeKey(ExplosionShape shape){
        return new TranslatableComponent(TEXT_SHAPE_HANDLE + shape.getName());
    }
}
