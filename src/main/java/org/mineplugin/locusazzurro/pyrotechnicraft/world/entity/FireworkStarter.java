package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkEngine;

import java.util.List;

public class FireworkStarter extends Projectile {

    private static final EntityDataAccessor<CompoundTag> PAYLOAD_LIST = SynchedEntityData.defineId(FireworkStarter.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Integer> FUSE_DELAY = SynchedEntityData.defineId(FireworkStarter.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<CompoundTag> VEC = SynchedEntityData.defineId(FireworkStarter.class, EntityDataSerializers.COMPOUND_TAG);
    private int life;
    private Vec3 explosionVec;
    private static AABB explosionRange = new AABB(-1, -1, -1, 1, 1, 1);

    public FireworkStarter(EntityType<? extends FireworkStarter> type, Level level) {
        super(type, level);
        this.life = 0;
    }

    public FireworkStarter(Level level, Vec3 pos, CompoundTag data, Vec3 explosionDirection){
        this(EntityTypeRegistry.FIREWORK_STARTER.get(), level);
        this.moveTo(pos);
        int fuseDelay = data.contains("FuseDelay") ? data.getInt("FuseDelay") : 0;
        this.entityData.set(FUSE_DELAY, fuseDelay);
        ListTag payloadList = data.contains("PayloadList") ? data.getList("PayloadList", ListTag.TAG_COMPOUND) : new ListTag();
        CompoundTag dataWrap = new CompoundTag();
        dataWrap.put("PayloadList", payloadList);
        this.entityData.set(PAYLOAD_LIST, dataWrap);
        this.explosionVec = explosionDirection;
        this.entityData.set(VEC, serializeVec(explosionDirection));
    }

    @Override
    public void tick() {

        CompoundTag payloadListWrap = entityData.get(PAYLOAD_LIST);
        ListTag payloadList = payloadListWrap.getList("PayloadList", ListTag.TAG_COMPOUND);
        int fuseDelay = entityData.get(FUSE_DELAY);
        if (!payloadList.isEmpty()){
            if (fuseDelay > 0){
                if (this.life % fuseDelay == 0 && this.life < fuseDelay * payloadList.size()) {
                    CompoundTag explosion = payloadList.getCompound(this.life / fuseDelay);
                    this.explosionVec = deserializeVec(entityData.get(VEC));
                    if (level.isClientSide()){
                        FireworkEngine.createFirework(level, position(), explosionVec, explosion);
                    }
                    double damage = calculateDamage(explosion);
                    List<LivingEntity> targets = this.level.getEntitiesOfClass(LivingEntity.class, explosionRange.move(position()));
                    targets.forEach(target -> target.hurt(DamageSource.explosion((Explosion) null), (float) damage));
                }
            }
            else {
                //todo all explosions
            }
        }

        if (this.life > 100) this.discard();
        this.life++;

        super.tick();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(PAYLOAD_LIST, new CompoundTag());
        this.entityData.define(FUSE_DELAY, 0);
        this.entityData.define(VEC, serializeVec(new Vec3(0,0,0)));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("PayloadList", entityData.get(PAYLOAD_LIST));
        pCompound.putInt("FuseDelay", entityData.get(FUSE_DELAY));
        pCompound.putInt("Life", life);
        pCompound.put("ExplosionDirection", entityData.get(VEC));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(PAYLOAD_LIST, pCompound.getCompound("PayloadList"));
        entityData.set(FUSE_DELAY, pCompound.getInt("FuseDelay"));
        entityData.set(VEC, pCompound.getCompound("ExplosionDirection"));
        this.life = pCompound.getInt("Life");
    }

    /*
    public static void createFirework(Level level, Vec3 pos, Vec3 mov, CompoundTag data){
        boolean custom = data.getBoolean("IsCustom");
        CompoundTag payload = data.getCompound("Payload");
        if (custom){
            explodeFireworkOrb(level, pos, mov, payload);
        }
        else explodeFireworkStar(level, pos, mov, payload);
    }

     */
    private double calculateDamage(CompoundTag tag){
        if (!tag.getBoolean("IsCustom")) return 3.0d;
        else {
            CompoundTag payload = tag.getCompound("Payload");
            return payload.contains("Damage") ? payload.getDouble("Damage") : 2.0d;
        }
    }

    private CompoundTag serializeVec(Vec3 vec){
        CompoundTag tag = new CompoundTag();
        tag.putDouble("X", vec.x);
        tag.putDouble("Y", vec.y);
        tag.putDouble("Z", vec.z);
          return tag;
    }

    private Vec3 deserializeVec(CompoundTag tag){
        return new Vec3(tag.getDouble("X"), tag.getDouble("Y"), tag.getDouble("Z"));
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
