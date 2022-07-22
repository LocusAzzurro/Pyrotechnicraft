package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.damage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public final class DamageSources {

    public static DamageSource fireworkMissile(Entity source){
        return new FireworkMissileDamage(source);
    }
}
