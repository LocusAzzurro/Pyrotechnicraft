package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.FireworkStarItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.DisplayProperties;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FlightProperties;

import java.util.List;

public class FireworkMissile extends Item{

    public static final String TOOLTIP_PREFIX = "item." + Pyrotechnicraft.MOD_ID + ".firework_missile.";
    public static final TranslatableComponent TEXT_FLIGHT = new TranslatableComponent(TOOLTIP_PREFIX + "flight.flight_time");
    public static final TranslatableComponent TEXT_SPEED = new TranslatableComponent(TOOLTIP_PREFIX + "flight.speed");
    public static final TranslatableComponent TEXT_HOMING = new TranslatableComponent(TOOLTIP_PREFIX + "flight.homing");
    public static final TranslatableComponent TEXT_COLORS = new TranslatableComponent(TOOLTIP_PREFIX + "display.title");
    public static final TranslatableComponent SYMBOL_BASE = new TranslatableComponent(TOOLTIP_PREFIX + "display.base_color.symbol");
    public static final TranslatableComponent SYMBOL_PATTERN = new TranslatableComponent(TOOLTIP_PREFIX + "display.pattern_color.symbol");
    public static final TranslatableComponent SYMBOL_SPARK = new TranslatableComponent(TOOLTIP_PREFIX + "display.spark_color.symbol");
    public static final TranslatableComponent TEXT_PAYLOAD_LIST = new TranslatableComponent(TOOLTIP_PREFIX + "payload_list");
    public FireworkMissile(){
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        FlightProperties flight = FlightProperties.deserialize(tag);
        DisplayProperties display = DisplayProperties.deserialize(tag);
        ListTag expList = tag.getList("PayloadList", 10);

        pTooltipComponents.add(TEXT_FLIGHT.copy().append(new TextComponent(String.valueOf(flight.flightTime()))).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(TEXT_SPEED.copy().append(new TextComponent(String.valueOf(flight.speed()))).withStyle(ChatFormatting.GRAY));
        if (flight.homing()) pTooltipComponents.add(TEXT_HOMING.copy().withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(TEXT_COLORS.copy().withStyle(ChatFormatting.GRAY)
                .append(SYMBOL_BASE.copy().withStyle(Style.EMPTY.withColor(display.baseColor())))
                .append(SYMBOL_PATTERN.copy().withStyle(Style.EMPTY.withColor(display.patternColor())))
                .append(SYMBOL_SPARK.copy().withStyle(Style.EMPTY.withColor(display.sparkColor()))));

        if (!expList.isEmpty()) {
            pTooltipComponents.add(TEXT_PAYLOAD_LIST.copy().withStyle(ChatFormatting.GRAY));
            for(int i = 0; i < expList.size(); ++i) {
                CompoundTag exp = expList.getCompound(i);
                List<Component> list = Lists.newArrayList();
                if (!exp.getBoolean("IsCustom")){
                    FireworkStarItem.appendHoverText(exp.getCompound("Payload"), list);
                    for(int j = 1; j < list.size(); ++j) {
                        list.set(j, (new TextComponent("  ")).append(list.get(j)).withStyle(ChatFormatting.GRAY));
                    }
                }
                else {
                    FireworkOrb.appendHoverText(exp.getCompound("Payload"), list);
                    for(int j = 1; j < list.size(); ++j) {
                        list.set(j, (new TextComponent("  ")).append(list.get(j)));
                    }
                }
                pTooltipComponents.addAll(list);
            }
        }
    }
}
