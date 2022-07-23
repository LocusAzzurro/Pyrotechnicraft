package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;

import java.util.List;

public class ExplosionShapePattern extends Item {

    public static final TranslatableComponent TEXT_PATTERN_POINTS = new TranslatableComponent("item." + Pyrotechnicraft.MOD_ID + ".explosion_pattern.points");
    public static final TranslatableComponent TEXT_PATTERN_COST = new TranslatableComponent("item." + Pyrotechnicraft.MOD_ID + ".explosion_pattern.cost");
    private final ExplosionShape shape;

    public ExplosionShapePattern(ExplosionShape shape) {
        super(new Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(1));
        this.shape = shape;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getItem() instanceof ExplosionShapePattern pattern && pattern.getShape().hasCostByDefault()) {
            CompoundTag tag = pStack.getOrCreateTag();
            if (tag.contains("Coordinates")) {
                ListTag coordList = tag.getList("Coordinates", ListTag.TAG_LIST);
                pTooltipComponents.add(TEXT_PATTERN_POINTS.copy().append(String.valueOf(coordList.size())).withStyle(ChatFormatting.GRAY));
            }
            if (tag.contains("Cost")) {
                pTooltipComponents.add(TEXT_PATTERN_COST.copy().append(String.valueOf(tag.getInt("Cost"))).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public ExplosionShape getShape(){return shape;}

}
