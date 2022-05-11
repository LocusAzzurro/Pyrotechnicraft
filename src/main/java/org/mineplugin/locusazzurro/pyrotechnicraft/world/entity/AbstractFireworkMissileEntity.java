package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;

public abstract class AbstractFireworkMissileEntity extends Projectile {

    private static final EntityDataAccessor<CompoundTag> PAYLOAD = SynchedEntityData.defineId(AbstractFireworkMissileEntity.class, EntityDataSerializers.COMPOUND_TAG);

    protected int life;
    protected int lifetime = 100;
    public AbstractFireworkMissileEntity(EntityType<? extends AbstractFireworkMissileEntity> type, Level level) {
        super(type, level);
    }
    @Override
    @NotNull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void defineSynchedData() {

    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    public boolean isSimple() {
        return true;
    }


}
