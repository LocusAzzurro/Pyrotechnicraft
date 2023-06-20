package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public class FireworkReplicationKit extends Item{

    public FireworkReplicationKit() {
        super(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        if (pUsedHand == InteractionHand.MAIN_HAND && !pLevel.isClientSide()) {
            ItemStack itemOffhand = pPlayer.getOffhandItem();
            if (itemOffhand.is(ItemRegistry.FIREWORK_ORB.get())
                    || itemOffhand.is(ItemRegistry.FIREWORK_MISSILE.get())
                    || itemOffhand.is(Items.FIREWORK_ROCKET)
                    || itemOffhand.is(Items.FIREWORK_STAR)){
                ItemStack copiedItem = itemOffhand.copy();
                int count = 1;
                if (pPlayer.isCrouching()) count = item.getCount();
                copiedItem.setCount(count);
                if (!pPlayer.isCreative()) item.shrink(count);
                ItemHandlerHelper.giveItemToPlayer(pPlayer, copiedItem);
                return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
