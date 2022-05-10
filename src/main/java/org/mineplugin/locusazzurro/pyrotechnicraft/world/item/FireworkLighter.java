package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class FireworkLighter extends Item {

    public FireworkLighter() {
        super(new Properties().tab(Pyrotechnicraft.CREATIVE_TAB));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        ItemStack firework = pPlayer.getOffhandItem();
        if (firework.getItem() instanceof FireworkRocketItem){

            FireworkPayloadData data = new VanillaFireworkPayloadData(firework);
            if (pLevel.isClientSide){
                pLevel.createFireworks(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),0,0,0,data.serialize());
            }

            //FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(pLevel, firework, pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), true);
            //fireworkRocketEntity.shoot(0f, 0.5f, 0f, 1,0);
            //pLevel.addFreshEntity(fireworkRocketEntity);
            //firework.shrink(1);
            return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
        }
        return InteractionResultHolder.pass(item);
    }
}
