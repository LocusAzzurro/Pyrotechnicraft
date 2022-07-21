package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomingArrayScript extends Item{

    public static final String MESSAGE_PREFIX = "item." + Pyrotechnicraft.MOD_ID + ".homing_array_script.state_change_message.";
    public static final TranslatableComponent TEXT_SELECTED_OPTION_CHANGED = new TranslatableComponent(MESSAGE_PREFIX + "selected_option");
    public static final TranslatableComponent TEXT_TARGET_TYPE_CHANGED = new TranslatableComponent(MESSAGE_PREFIX + "target_type");
    public static final TranslatableComponent TEXT_MODE_CHANGED = new TranslatableComponent(MESSAGE_PREFIX + "mode");
    public static final TranslatableComponent TEXT_RANGE_CHANGED = new TranslatableComponent(MESSAGE_PREFIX + "range");
    public static final TranslatableComponent TEXT_APERTURE_CHANGED = new TranslatableComponent(MESSAGE_PREFIX + "aperture");
    public static Map<Byte, TranslatableComponent> CHANGED_OPTION_TEXT = new HashMap<>();
    public HomingArrayScript(){
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        populateTags(item);
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            CompoundTag tag = item.getOrCreateTag();
            boolean toggleOption = pPlayer.isCrouching();
            byte currentOption = tag.getByte(HomingSystem.CURRENT_OPTION);
            if (toggleOption) {
                byte newOption = (byte) ((currentOption + 1) & 3);
                pPlayer.displayClientMessage(TEXT_SELECTED_OPTION_CHANGED.copy()
                        .append(HomingSystem.SELECTED_OPTION_TEXT.getOrDefault(newOption, HomingSystem.TEXT_NO_INFO)), true);
                tag.putByte(HomingSystem.CURRENT_OPTION, newOption);
            } else {
                String optionTag = HomingSystem.OPTIONS[currentOption];
                byte newValue = currentOption == HomingSystem.OPTION_MODE ? (byte) ((tag.getByte(optionTag) + 1) & 1) : (byte) ((tag.getByte(optionTag) + 1) & 3);
                pPlayer.displayClientMessage(CHANGED_OPTION_TEXT.getOrDefault(currentOption, HomingSystem.TEXT_NO_INFO).copy()
                        .append(getOptionValueComponent(currentOption, newValue)),true);
                tag.putByte(optionTag, newValue);
            }
            return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
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
        pTooltipComponents.add(HomingSystem.TEXT_SELECTED_OPTION_TITLE.copy().append(selectedOption).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_TARGET_TYPE_TITLE.copy().append(targetType).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_MODE_TITLE.copy().append(mode).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_RANGE_TITLE.copy().append(range).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(HomingSystem.TEXT_APERTURE_TITLE.copy().append(aperture).withStyle(ChatFormatting.GRAY));
    }
    public TranslatableComponent getOptionValueComponent(byte option, byte value){
        return switch (option) {
            case HomingSystem.OPTION_TARGET_TYPE -> HomingSystem.TARGET_TYPE_TEXT.getOrDefault(value, HomingSystem.TEXT_NO_INFO);
            case HomingSystem.OPTION_MODE -> HomingSystem.MODE_TEXT.getOrDefault(value, HomingSystem.TEXT_NO_INFO);
            case HomingSystem.OPTION_RANGE, HomingSystem.OPTION_APERTURE -> HomingSystem.EXTENT_TEXT.getOrDefault(value, HomingSystem.TEXT_NO_INFO);
            default -> HomingSystem.TEXT_NO_INFO;
        };
    }
    private void populateTags(ItemStack stack){
        if (stack.getItem() instanceof HomingArrayScript){
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains(HomingSystem.CURRENT_OPTION)){
                tag.putByte(HomingSystem.CURRENT_OPTION, HomingSystem.OPTION_TARGET_TYPE);
                tag.putByte(HomingSystem.TARGET_TYPE, HomingSystem.TARGET_TYPE_NONE);
                tag.putByte(HomingSystem.MODE, HomingSystem.TARGET_MODE_RANGE);
                tag.putByte(HomingSystem.RANGE, (byte) 0);
                tag.putByte(HomingSystem.APERTURE, (byte) 0);
            }
        }
    }

    static {
        CHANGED_OPTION_TEXT.put(HomingSystem.OPTION_TARGET_TYPE, TEXT_TARGET_TYPE_CHANGED);
        CHANGED_OPTION_TEXT.put(HomingSystem.OPTION_MODE, TEXT_MODE_CHANGED);
        CHANGED_OPTION_TEXT.put(HomingSystem.OPTION_RANGE, TEXT_RANGE_CHANGED);
        CHANGED_OPTION_TEXT.put(HomingSystem.OPTION_APERTURE, TEXT_APERTURE_CHANGED);
    }





}
