package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public class FirecrackerEntity extends ThrowableItemProjectile {

    public FirecrackerEntity(EntityType<FirecrackerEntity> type, Level level) {
        super(type, level);
    }
    public FirecrackerEntity(Level level, LivingEntity owner) {
        super(EntityTypeRegistry.FIRECRACKER.get(), owner, level);
    }

    public FirecrackerEntity(Level level){
        this(EntityTypeRegistry.FIRECRACKER.get(), level);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.FIRECRACKER.get();
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if(!this.level.isClientSide){
            boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
            this.level.explode(null, this.getX(), this.getY(), this.getZ(), 2f, flag, Level.ExplosionInteraction.NONE);
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level.isClientSide()){
            this.level.addParticle(ParticleTypes.SMALL_FLAME, getX(), getY(), getZ(), 0, 0.05, 0);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
