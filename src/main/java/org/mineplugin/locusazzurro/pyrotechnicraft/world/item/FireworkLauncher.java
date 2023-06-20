package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.SoundEventRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import net.minecraft.world.item.Item.Properties;

public class FireworkLauncher extends ProjectileWeaponItem {

    public static final Predicate<ItemStack> isFireworkRocket = i -> i.is(Items.FIREWORK_ROCKET);
    public static final Predicate<ItemStack> isFireworkMissile = i -> i.is(ItemRegistry.FIREWORK_MISSILE.get());
    public FireworkLauncher() {
        super(new Properties().durability(160));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player playerIn, InteractionHand pUsedHand) {
        ItemStack item = playerIn.getItemInHand(pUsedHand);
        ItemStack firework = playerIn.getProjectile(item);

        if (!playerIn.isCreative() && firework.isEmpty()){
            return InteractionResultHolder.pass(item);
        }

        if (isFireworkRocket.or(isFireworkMissile).test(firework)) {
            FireworkMissileEntity fireworkEntity;
            if (isFireworkRocket.test(firework)) {
                fireworkEntity = new FireworkMissileEntity(pLevel, playerIn.getEyePosition(), FireworkWrapper.convertVanillaFireworkRocket(firework));
            } else {
                fireworkEntity = new FireworkMissileEntity(pLevel, playerIn.getEyePosition(), firework.getOrCreateTag());
            }

            if (pUsedHand == InteractionHand.MAIN_HAND){
                ItemStack offHand = playerIn.getOffhandItem();
                if (offHand.is(ItemRegistry.HOMING_ARRAY.get())){
                    CompoundTag tag = offHand.getOrCreateTag();
                    if (tag.getBoolean("TargetValid")) {
                        HomingSystem.getTargetById(pLevel, tag.getUUID("TargetUUID"), tag.getInt("TargetNetworkID"))
                                .ifPresent(fireworkEntity::setTarget);
                    }
                }
            }
            fireworkEntity.setOwner(playerIn);

            fireworkEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0f, 1.0f, 0.1f);
            pLevel.addFreshEntity(fireworkEntity);
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
                playerIn.getCooldowns().addCooldown(this, 20);
            }

            playerIn.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
        }



        return InteractionResultHolder.pass(item);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return isFireworkRocket.or(isFireworkMissile);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 64;
    }
}
