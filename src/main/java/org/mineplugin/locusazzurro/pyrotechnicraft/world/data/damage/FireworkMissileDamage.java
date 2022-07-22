package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.damage;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class FireworkMissileDamage extends EntityDamageSource {

    public FireworkMissileDamage(@Nullable Entity source) {
        super("fireworkMissile", source);
    }

    @Override
    public boolean isExplosion() {
        return true;
    }
}
