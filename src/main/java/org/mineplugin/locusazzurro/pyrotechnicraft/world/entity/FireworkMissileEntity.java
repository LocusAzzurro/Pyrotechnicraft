package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.TrailSparkParticleOption;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.DisplayProperties;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FlightProperties;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.HomingSystem;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class FireworkMissileEntity extends AbstractHurtingProjectile {

    private static final EntityDataAccessor<CompoundTag> PAYLOAD_LIST = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Integer> FLIGHT_TIME = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> HOMING = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FUSE_DELAY = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPARK_COLOR = SynchedEntityData.defineId(FireworkMissileEntity.class, EntityDataSerializers.INT);
    public static final double DELTA_MOD = 0.9;
    private int life = 0;
    private static final int HOMING_DELAY = 5;
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

        if (level().isClientSide()) {
            level().addParticle(new TrailSparkParticleOption(entityData.get(SPARK_COLOR)), getX(), getY(), getZ(), 0, 0, 0);
        }

        Vec3 mov = this.getDeltaMovement().normalize();
        Vec3 targetedMov = mov;

        if (entityData.get(HOMING) && tickCount % 4 == 0 && life > HOMING_DELAY) {
            double[] movVec = new double[]{mov.x, mov.y, mov.z};
            HomingSystem.getTargetById(level(), targetUUID, targetNetworkId).ifPresentOrElse(target -> {
                if (target instanceof LivingEntity targetLiving && this.distanceTo(targetLiving) < 256) {
                    movVec[0] = target.getX() - this.getX();
                    movVec[1] = target.getY() - this.getY();
                    movVec[2] = target.getZ() - this.getZ();
                }
            }, () -> HomingSystem.rangeFinding(this, 5, HomingSystem.isHostile.or(HomingSystem.isPlayer)).ifPresent(targetCandidate -> {
                if (!targetCandidate.equals(this.getOwner())) {
                    this.setTarget(targetCandidate);
                }
            }));
            targetedMov = new Vec3(movVec[0], movVec[1], movVec[2]).normalize();
        }

        double speed = entityData.get(SPEED);
        double delta = Mth.clamp(DELTA_MOD * (-Math.log10(speed) + 1), 0.05, 0.95);
        Vec3 resultMov = targetedMov.equals(mov) ? mov : mov.lerp(targetedMov, delta);
        this.setDeltaMovement(resultMov.scale(entityData.get(SPEED)));

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
        FireworkStarter starter = new FireworkStarter(level(), this.position(), starterTag, this.getDeltaMovement());
        starter.setOwner(this.getOwner());
        level().addFreshEntity(starter);
        this.discard();
    }

    public void setTarget(Entity target) {
        if (target != null){
            this.targetUUID = target.getUUID();
            this.targetNetworkId = target.getId();
        }
    }

    public int getBaseColor(){
        return entityData.get(BASE_COLOR);
    }

    public int getPatternColor(){
        return entityData.get(PATTERN_COLOR);
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
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
        if (this.targetUUID != null) {
            compoundTag.putUUID("TargetUUID", this.targetUUID);
        }
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
        if (compoundTag.hasUUID("TargetUUID")) {
            this.targetUUID = compoundTag.getUUID("TargetUUID");
        }
    }
}
