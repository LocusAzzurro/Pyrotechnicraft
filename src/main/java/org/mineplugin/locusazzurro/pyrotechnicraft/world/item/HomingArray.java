package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem;

import java.util.Optional;

import static org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem.CURRENT_OPTION;

public class HomingArray extends Item{

    public HomingArray(){
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).durability(100));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        if (pUsedHand == InteractionHand.MAIN_HAND && !pLevel.isClientSide()) {
            ItemStack offHand = pPlayer.getOffhandItem();
            if (offHand.is(ItemRegistry.HOMING_ARRAY_SCRIPT.get())) {
                CompoundTag scriptTag = offHand.getOrCreateTag().copy();
                scriptTag.remove(CURRENT_OPTION);
                item.getOrCreateTag().merge(scriptTag);
                pPlayer.displayClientMessage(new TextComponent("Test"), true);
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
            item.getOrCreateTag().putUUID("TargetUUID", entity.getUUID());
            item.getOrCreateTag().putInt("TargetNetworkID", entity.getId());
        });

        if (!pLevel.isClientSide()) {
            item.hurtAndBreak(1, pPlayer, (player) -> {
                player.broadcastBreakEvent(pPlayer.getUsedItemHand());
            });
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
