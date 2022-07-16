package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
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
        ItemStack itemOffhand = pPlayer.getOffhandItem();

        if (pLevel.isClientSide() && (itemOffhand.is(Items.FIREWORK_STAR)||itemOffhand.is(ItemRegistry.FIREWORK_ORB.get()))){
            CompoundTag exp = FireworkWrapper.wrapSingleFireworkExplosion(itemOffhand);
            FireworkEngine.createFirework(pLevel, pPlayer.position(), pPlayer.getDeltaMovement(), exp);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
