package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.SoundEventRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FirecrackerEntity;

public class Firecracker extends Item{

    public Firecracker()  {
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {
            FirecrackerEntity firecracker = new FirecrackerEntity(pLevel, pPlayer);
            firecracker.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(firecracker);
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getEyeY(), pPlayer.getZ(), SoundEventRegistry.FIRECRACKER_THROW.get(),
                    SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            item.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
    }
}
