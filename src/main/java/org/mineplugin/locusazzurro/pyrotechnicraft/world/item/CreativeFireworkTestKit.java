package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkEngine;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkStarter;

public class CreativeFireworkTestKit extends Item{

    public CreativeFireworkTestKit() {
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        if (pUsedHand == InteractionHand.MAIN_HAND) {
            ItemStack itemOffhand = pPlayer.getOffhandItem();
            Vec3 mov = pPlayer.getDeltaMovement();
            if (mov.length() < 0.01f) mov = pPlayer.getLookAngle();
            Vec3 pos = pPlayer.position().add(0,2,0);

            if (itemOffhand.is(ItemRegistry.FIREWORK_MISSILE.get())) {
                FireworkStarter starter = new FireworkStarter(pLevel, pos, itemOffhand.getOrCreateTag(), mov);
                pLevel.addFreshEntity(starter);
            }
            else if (itemOffhand.is(Items.FIREWORK_ROCKET)){
                FireworkStarter starter = new FireworkStarter(pLevel, pos, FireworkWrapper.convertVanillaFireworkRocket(itemOffhand), mov);
                pLevel.addFreshEntity(starter);
            }
            else if (itemOffhand.is(ItemRegistry.FIREWORK_ORB.get()) || itemOffhand.is(Items.FIREWORK_STAR)){
                CompoundTag exp = FireworkWrapper.wrapSingleFireworkExplosion(itemOffhand);
                FireworkEngine.createFirework(pLevel, pos, mov, exp);
            }
        }
        return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
    }
}
