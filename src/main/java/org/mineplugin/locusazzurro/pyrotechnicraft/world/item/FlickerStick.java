package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.TrailSparkParticleOption;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.SoundEventRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkEngine;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;

import java.util.List;

public class FlickerStick extends Item{

    public FlickerStick()
    {
        super(new Item.Properties().durability(200));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        item.getOrCreateTag().putBoolean("Ignited", true);

        pLevel.playSound(null, pPlayer, SoundEventRegistry.FLICKER_STICK_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.getBoolean("Ignited")) {
            if (pEntity instanceof LivingEntity livingEntity) {
                int color = tag.contains("SparkColor") ? tag.getInt("SparkColor") : 0xffffff;
                pStack.hurtAndBreak(1, livingEntity, entity -> {});
                if (!pLevel.isClientSide()){
                    ServerLevel serverLevel = (ServerLevel) pLevel;
                    List<ServerPlayer> players = serverLevel.players();
                    for (ServerPlayer player : players) {
                        serverLevel.sendParticles(player, new TrailSparkParticleOption(color), false, pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 1, 0.0 ,0.0 ,0.0, 0.0);
                    }

                }

                /*
                if (pLevel.isClientSide()) {
                    pLevel.addParticle(new TrailSparkParticleOption(color),
                            pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 0, 0, 0);
                }

                 */
            }
        }
    }
}
