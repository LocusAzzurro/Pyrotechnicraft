package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class FireworkMissileEntity extends Projectile {

    public FireworkMissileEntity(EntityType<FireworkMissileEntity> type, Level level) {
        super(type, level);
    }

    @Override
    @NotNull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {

    }
    public boolean isSimple() {
        return true;
    }

    public record Properties(int flightTime, float speed, float power, HomingPolicy homingPolicy) {

    }

    enum HomingPolicy {
        NONE, MANUAL_TARGET, NEAREST;
    }


}
