package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
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
        if (firework.getItem() instanceof FireworkRocketItem){

                FireworkMissileEntity fireworkEntity = new FireworkMissileEntity(pLevel, playerIn.position(), FlightProperties.createFromVanilla(firework), FireworkWrapper.wrapPayload(firework));
                fireworkEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0f, 0.0001f, 1.0f);
                pLevel.addFreshEntity(fireworkEntity);

            /*
            FireworkPayloadData data = new VanillaFireworkPayloadData(firework);
            if (pLevel.isClientSide){
                pLevel.createFireworks(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),0,0,0,data.serialize());
            }

             */
            /*
            //FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(pLevel, firework, pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), true);
            //fireworkRocketEntity.shoot(0f, 0.5f, 0f, 1,0);
            //pLevel.addFreshEntity(fireworkRocketEntity);
            //firework.shrink(1);
            */
            return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());

        }
        return InteractionResultHolder.pass(item);
    }
}
