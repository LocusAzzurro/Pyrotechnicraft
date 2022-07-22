package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.DisplayProperties;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FlightProperties;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class FireworkMissileEntity extends AbstractHurtingProjectile {

    private static final EntityDataAccessor<CompoundTag> PAYLOAD_LIST = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Integer> FLIGHT_TIME = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> HOMING = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FUSE_DELAY = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPARK_COLOR = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private int life = 0;
    private UUID targetUUID;
    private int targetNetworkId;

    public FireworkMissileEntity(EntityType<FireworkMissileEntity> type, Level level) {
        super(type, level);
    }

    public FireworkMissileEntity(Level level, Vec3 pos, CompoundTag fireworkMissileData){
        super(EntityTypeRegistry.FIREWORK_MISSILE.get(), level);
        ListTag expList = fireworkMissileData.getList("PayloadList", ListTag.TAG_COMPOUND);
        CompoundTag expWrap = new CompoundTag();
        expWrap.put("PayloadList", expList);
        this.entityData.set(PAYLOAD_LIST, expWrap);
        FlightProperties flight = FlightProperties.deserialize(fireworkMissileData);
        this.entityData.set(FLIGHT_TIME, flight.flightTime());
        this.entityData.set(SPEED, (float)flight.speed());
        this.entityData.set(HOMING, flight.homing());
        DisplayProperties display = DisplayProperties.deserialize(fireworkMissileData);
        this.entityData.set(BASE_COLOR, display.baseColor());
        this.entityData.set(PATTERN_COLOR, display.patternColor());
        this.entityData.set(SPARK_COLOR, display.sparkColor());
        int fuseDelay = fireworkMissileData.contains("FuseDelay") ? fireworkMissileData.getInt("FuseDelay") : 1;
        this.entityData.set(FUSE_DELAY, fuseDelay);
        this.life = 0;
        this.moveTo(pos);
        this.reapplyPosition();
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(this.entityData.get(SPEED)));

        if (life > entityData.get(FLIGHT_TIME)) explode();
        life++;
    }

    @Override
    protected void onHit(HitResult pResult) {
        explode();
    }

    public void explode(){
        CompoundTag starterTag = new CompoundTag();
        starterTag.putInt("FuseDelay", entityData.get(FUSE_DELAY));
        ListTag expList = entityData.get(PAYLOAD_LIST).getList("PayloadList", ListTag.TAG_COMPOUND);
        starterTag.put("PayloadList", expList);
        FireworkStarter starter = new FireworkStarter(level, this.position(), starterTag, this.getDeltaMovement());
        level.addFreshEntity(starter);
        this.discard();
    }

    public void setTarget(Optional<Entity> target) {
        target.ifPresent(targetEntity -> {
            this.targetUUID = targetEntity.getUUID();
            this.targetNetworkId = targetEntity.getId();
        });
    }

    @Nullable
    public Entity getTarget() {
        if (this.targetUUID != null && this.level instanceof ServerLevel) {
            return ((ServerLevel)this.level).getEntity(this.targetUUID);
        } else {
            return this.targetNetworkId != 0 ? this.level.getEntity(this.targetNetworkId) : null;
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(PAYLOAD_LIST, new CompoundTag());
        FlightProperties flight = FlightProperties.createDefault();
        this.entityData.define(FLIGHT_TIME, flight.flightTime());
        this.entityData.define(SPEED, (float)flight.speed());
        this.entityData.define(HOMING, flight.homing());
        DisplayProperties display = DisplayProperties.createDefault();
        this.entityData.define(BASE_COLOR, display.baseColor());
        this.entityData.define(PATTERN_COLOR, display.patternColor());
        this.entityData.define(SPARK_COLOR, display.sparkColor());
        this.entityData.define(FUSE_DELAY, 1);
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AIR.defaultBlockState());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        SynchedEntityData data = this.entityData;
        ListTag expList = data.get(PAYLOAD_LIST).getList("PayloadList", ListTag.TAG_COMPOUND);
        compoundTag.put("PayloadList", expList);
        compoundTag.putInt("FlightTime", data.get(FLIGHT_TIME));
        compoundTag.putFloat("Speed", data.get(SPEED));
        compoundTag.putBoolean("Homing", data.get(HOMING));
        compoundTag.putInt("BaseColor", data.get(BASE_COLOR));
        compoundTag.putInt("PatternColor", data.get(PATTERN_COLOR));
        compoundTag.putInt("SparkColor", data.get(SPARK_COLOR));
        compoundTag.putInt("FuseDelay", data.get(FUSE_DELAY));
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        ListTag expList = compoundTag.getList("PayloadList", ListTag.TAG_COMPOUND);
        CompoundTag expWrap = new CompoundTag();
        expWrap.put("PayloadList", expList);
        this.entityData.set(PAYLOAD_LIST, expWrap);
        FlightProperties flight = FlightProperties.deserialize(compoundTag);
        this.entityData.set(FLIGHT_TIME, flight.flightTime());
        this.entityData.set(SPEED, (float)flight.speed());
        this.entityData.set(HOMING, flight.homing());
        DisplayProperties display = DisplayProperties.deserialize(compoundTag);
        this.entityData.set(BASE_COLOR, display.baseColor());
        this.entityData.set(PATTERN_COLOR, display.patternColor());
        this.entityData.set(SPARK_COLOR, display.sparkColor());
    }
}
