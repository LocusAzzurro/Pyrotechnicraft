package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
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
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkEngine;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;

public class FlickerStick extends Item{

    public FlickerStick()
    {
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).durability(200));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        item.getOrCreateTag().putBoolean("Ignited", true);

        //todo sound
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.getBoolean("Ignited")) {
            if (!pLevel.isClientSide() && pEntity instanceof LivingEntity livingEntity) {
                pStack.hurtAndBreak(1, livingEntity, entity -> {});
            }
            else {
                int color = tag.contains("SparkColor") ? tag.getInt("SparkColor") : 0xffffff;
                pLevel.addParticle(new TrailSparkParticleOption(color),
                        pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 0, 0, 0);
            }
        }
    }
}
