package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FlightProperties;

public class FireworkMissileEntity extends AbstractHurtingProjectile {

    private static final EntityDataAccessor<CompoundTag> PAYLOAD = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Integer> FLIGHT_TIME = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> POWER = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> HOMING = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.BOOLEAN);

    public FireworkMissileEntity(EntityType<FireworkMissileEntity> type, Level level) {
        super(type, level);
    }

    public FireworkMissileEntity(Level level, Vec3 pos, FlightProperties properties, CompoundTag payload){
        super(EntityTypeRegistry.SIMPLE_FIREWORK_MISSILE.get(), level);
        this.entityData.set(PAYLOAD, payload);
        this.entityData.set(FLIGHT_TIME, properties.flightTime());
        this.entityData.set(SPEED, (float)properties.speed());
        this.entityData.set(POWER, (float)properties.power());
        this.entityData.set(HOMING, properties.homing());
        this.setPos(pos);
        this.reapplyPosition();
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(this.entityData.get(SPEED)));
    }

    @Override
    protected void onHit(HitResult pResult) {
        if (this.level.isClientSide()){
            CompoundTag payload = this.entityData.get(PAYLOAD);
            if (payload.contains("IsCustom") && !payload.getBoolean("IsCustom")) {
                this.level.createFireworks(this.getX(), this.getY(), this.getZ(), 0, 0, 0, payload.getCompound("Payload"));
            }
            else {
                System.out.println("Custom Firework Check");
            }
        }
        super.onHit(pResult);
    }

    @Override
    protected void defineSynchedData() {
        FlightProperties dProperties = FlightProperties.createDefault();
        this.entityData.define(PAYLOAD, new CompoundTag());
        this.entityData.define(FLIGHT_TIME, dProperties.flightTime());
        this.entityData.define(SPEED, (float)dProperties.speed());
        this.entityData.define(POWER, (float)dProperties.power());
        this.entityData.define(HOMING, dProperties.homing());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        SynchedEntityData data = this.entityData;
        compoundTag.put("Payload", data.get(PAYLOAD));
        compoundTag.put("Properties", new FlightProperties(data.get(FLIGHT_TIME), data.get(SPEED), data.get(POWER), data.get(HOMING)).serialize());

    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(PAYLOAD, compoundTag.getCompound("Payload"));
        FlightProperties properties = FlightProperties.deserialize(compoundTag.getCompound("Properties"));
        this.entityData.set(FLIGHT_TIME, properties.flightTime());
        this.entityData.set(SPEED, (float)properties.speed());
        this.entityData.set(POWER, (float)properties.power());
        this.entityData.set(HOMING, properties.homing());
    }
}
