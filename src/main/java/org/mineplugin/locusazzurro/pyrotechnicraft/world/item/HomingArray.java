package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.SoundEventRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem.CURRENT_OPTION;

public class HomingArray extends Item implements IHomingSystemEnabled{

    public static final String MESSAGE_PREFIX = "item." + Pyrotechnicraft.MOD_ID + ".homing_array.";

    public static final TranslatableComponent TEXT_LOAD_FROM_HOMING_SCRIPT = new TranslatableComponent(MESSAGE_PREFIX + "homing_script_loaded");
    public static final TranslatableComponent TEXT_TARGET_LOCKED = new TranslatableComponent(MESSAGE_PREFIX + "target_locked");
    public static final TranslatableComponent TEXT_CURRENT_TARGET = new TranslatableComponent(MESSAGE_PREFIX + "current_target");
    public HomingArray(){
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).durability(100));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        if (pUsedHand == InteractionHand.MAIN_HAND) {
            ItemStack offHand = pPlayer.getOffhandItem();
            if (offHand.is(ItemRegistry.HOMING_ARRAY_SCRIPT.get())) {
                if (!pLevel.isClientSide()) {
                    CompoundTag scriptTag = offHand.getOrCreateTag().copy();
                    scriptTag.remove(CURRENT_OPTION);
                    item.getOrCreateTag().merge(scriptTag);
                    pPlayer.displayClientMessage(TEXT_LOAD_FROM_HOMING_SCRIPT, true);
                }
                else pLevel.playLocalSound(pPlayer.getX(), pPlayer.getEyeY(), pPlayer.getZ(), SoundEventRegistry.HOMING_ARRAY_SCRIPT_LOADED.get(),
                        SoundSource.PLAYERS, 1.0f, 1.0f, false);
                return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
            }
        }

        CompoundTag tag = item.getOrCreateTag();
        Optional<LivingEntity> target = HomingSystem.findTarget(pPlayer,
                tag.getByte(HomingSystem.TARGET_TYPE),
                tag.getByte(HomingSystem.MODE),
                tag.getByte(HomingSystem.RANGE),
                tag.getByte(HomingSystem.APERTURE));

        target.ifPresent(entity -> {
            tag.putUUID("TargetUUID", entity.getUUID());
            tag.putInt("TargetNetworkID", entity.getId());
            tag.putBoolean("TargetValid", true);
            pPlayer.displayClientMessage(TEXT_TARGET_LOCKED.copy().append(entity.getName()), true);
            pLevel.playLocalSound(pPlayer.getX(), pPlayer.getEyeY(), pPlayer.getZ(), SoundEventRegistry.HOMING_ARRAY_TARGET_LOCKED.get(),
                    SoundSource.PLAYERS, 1.0f, 1.0f, false);
        });

        if (!pLevel.isClientSide()) {
            item.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pPlayer.getUsedItemHand()));
        }

        if (!pPlayer.isCreative()) {
            pPlayer.getCooldowns().addCooldown(this, 40);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.getBoolean("TargetValid")) {
            HomingSystem.getTargetById(pLevel, tag.getUUID("TargetUUID"), tag.getInt("TargetNetworkID")).ifPresent(target ->
                    pTooltipComponents.add(TEXT_CURRENT_TARGET.copy().append(target.getName()).withStyle(ChatFormatting.AQUA)));
        }
        IHomingSystemEnabled.super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced, false);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (((pSlotId >= 0 && pSlotId <= 8) || pSlotId == 98 || pSlotId == 99) && pEntity.tickCount % 10 == 0){
            updateTargetStillValid(pStack, pLevel, pEntity);
        }
    }

    public boolean updateTargetStillValid(ItemStack stack, Level level, Entity entity){
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean("TargetValid")) return false;
        AtomicBoolean flag = new AtomicBoolean(false);
        HomingSystem.getTargetById(level, tag.getUUID("TargetUUID"), tag.getInt("TargetNetworkID")).ifPresentOrElse(
            target -> flag.set(entity.distanceTo(target) < 256),
            () -> {
            tag.remove("TargetUUID");
            tag.remove("TargetNetworkID");
        });
        tag.putBoolean("TargetValid", flag.get());
        return flag.get();
    }
}
