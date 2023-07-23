package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.SoundEventRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.ThrownFireworkOrbEntity;

import java.util.function.Predicate;

public class FireworkOrbShooter extends ProjectileWeaponItem {

    public static final Predicate<ItemStack> isFireworkStar = i -> i.is(Items.FIREWORK_STAR);
    public static final Predicate<ItemStack> isFireworkOrb = i -> i.is(ItemRegistry.FIREWORK_ORB.get());
    public FireworkOrbShooter() {
        super(new Item.Properties().durability(40));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player playerIn, InteractionHand pUsedHand) {
        ItemStack item = playerIn.getItemInHand(pUsedHand);
        ItemStack firework = playerIn.getProjectile(item);

        if (!playerIn.isCreative() && firework.isEmpty()){
            return InteractionResultHolder.pass(item);
        }

        if (isFireworkOrb.test(firework)) {

            ThrownFireworkOrbEntity fireworkOrbEntity = new ThrownFireworkOrbEntity(pLevel, playerIn.getEyePosition(), firework);
            fireworkOrbEntity.setOwner(playerIn);

            fireworkOrbEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0f, 1.0f, 0.1f);
            pLevel.addFreshEntity(fireworkOrbEntity);
            pLevel.playSound(null, playerIn.getX(), playerIn.getEyeY(), playerIn.getZ(),
                    SoundEventRegistry.FIREWORK_MISSILE_LAUNCH.get(), SoundSource.PLAYERS,
                    3.0f,1.0f  + pLevel.random.nextFloat() * (0.1f) - 0.05f);

            if (!pLevel.isClientSide()) {
                item.hurtAndBreak(1, playerIn, (player) -> {
                    player.broadcastBreakEvent(playerIn.getUsedItemHand());
                });
            }

            if (!playerIn.isCreative()) {
                firework.shrink(1);
                if (firework.isEmpty()) {
                    playerIn.getInventory().removeItem(firework);
                }
                playerIn.getCooldowns().addCooldown(this, 10);
            }

            playerIn.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
        }

        return InteractionResultHolder.pass(item);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return isFireworkOrb;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 64;
    }
}
