package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem;

import java.util.List;

public interface IHomingSystemEnabled {

    default void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced, boolean containsOption) {
        CompoundTag tag = pStack.getOrCreateTag();
        TranslatableComponent selectedOption = tag.contains(HomingSystem.CURRENT_OPTION) ?
                HomingSystem.SELECTED_OPTION_TEXT.getOrDefault(tag.getByte(HomingSystem.CURRENT_OPTION), HomingSystem.TEXT_NO_INFO) :
                HomingSystem.TEXT_NO_INFO;
        TranslatableComponent targetType = tag.contains(HomingSystem.TARGET_TYPE) ?
                HomingSystem.TARGET_TYPE_TEXT.getOrDefault(tag.getByte(HomingSystem.TARGET_TYPE), HomingSystem.TEXT_NO_INFO) :
                HomingSystem.TEXT_NO_INFO;
        TranslatableComponent mode = tag.contains(HomingSystem.MODE) ?
                HomingSystem.MODE_TEXT.getOrDefault(tag.getByte(HomingSystem.MODE), HomingSystem.TEXT_NO_INFO) :
                HomingSystem.TEXT_NO_INFO;
        TranslatableComponent range = tag.contains(HomingSystem.RANGE) ?
                HomingSystem.EXTENT_TEXT.getOrDefault(tag.getByte(HomingSystem.RANGE), HomingSystem.TEXT_NO_INFO) :
                HomingSystem.TEXT_NO_INFO;
        TranslatableComponent aperture = tag.contains(HomingSystem.APERTURE) ?
                HomingSystem.EXTENT_TEXT.getOrDefault(tag.getByte(HomingSystem.APERTURE), HomingSystem.TEXT_NO_INFO) :
                HomingSystem.TEXT_NO_INFO;
        if (containsOption) pTooltipComponents.add(HomingSystem.TEXT_SELECTED_OPTION_TITLE.copy().append(selectedOption).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_TARGET_TYPE_TITLE.copy().append(targetType).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_MODE_TITLE.copy().append(mode).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_RANGE_TITLE.copy().append(range).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_APERTURE_TITLE.copy().append(aperture).withStyle(ChatFormatting.GRAY));
    }

}
