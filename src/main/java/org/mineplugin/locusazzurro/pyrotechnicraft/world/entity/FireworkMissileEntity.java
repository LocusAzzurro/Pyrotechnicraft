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
    private static final EntityDataAccessor<CompoundTag> PROPERTIES = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.COMPOUND_TAG);

    private FlightProperties properties;
    public FireworkMissileEntity(EntityType<FireworkMissileEntity> type, Level level) {
        super(type, level);
    }

    //todo add properties
    public FireworkMissileEntity(Level level, Vec3 pos, CompoundTag properties, CompoundTag payload){
        super(EntityTypeRegistry.SIMPLE_FIREWORK_MISSILE.get(), level);
        this.entityData.set(PAYLOAD, payload);
        this.entityData.set(PROPERTIES, properties);
        this.properties = FlightProperties.deserialize(getEntityData().get(PROPERTIES));
        this.setPos(pos);
        this.reapplyPosition();
    }

    @Override
    public void tick() {
        super.tick();
        this.properties = FlightProperties.deserialize(getEntityData().get(PROPERTIES));
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(this.properties.speed()));
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
        this.entityData.define(PAYLOAD, new CompoundTag());
        this.entityData.define(PROPERTIES, FlightProperties.createDefault().serialize());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("Payload", this.entityData.get(PAYLOAD));
        compoundTag.put("Properties", this.entityData.get(PROPERTIES));

    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(PAYLOAD, compoundTag.getCompound("Payload"));
        this.entityData.set(PROPERTIES, compoundTag.getCompound("Properties"));
    }
}
