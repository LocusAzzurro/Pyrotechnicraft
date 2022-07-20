package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FlightProperties;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

public class FireworkLauncher extends Item {

    public FireworkLauncher() {
        super(new Properties().tab(Pyrotechnicraft.CREATIVE_TAB).durability(160));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player playerIn, InteractionHand pUsedHand) {
        ItemStack item = playerIn.getItemInHand(pUsedHand);
        ItemStack firework = playerIn.getOffhandItem();
        if (firework.is(Items.FIREWORK_ROCKET) || firework.is(ItemRegistry.FIREWORK_MISSILE.get())) {
            FireworkMissileEntity fireworkEntity;
            if (firework.is(Items.FIREWORK_ROCKET)) {
                fireworkEntity = new FireworkMissileEntity(pLevel, playerIn.position(), FireworkWrapper.convertVanillaFireworkRocket(firework));
            } else {
                fireworkEntity = new FireworkMissileEntity(pLevel, playerIn.position(), firework.getOrCreateTag());
            }
            fireworkEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0f, 0.0f, 0.1f);
            pLevel.addFreshEntity(fireworkEntity);
            return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
        }
        return InteractionResultHolder.pass(item);
    }
}
