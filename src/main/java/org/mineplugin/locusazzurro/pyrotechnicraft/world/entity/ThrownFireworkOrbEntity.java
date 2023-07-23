package org.mineplugin.locusazzurro.pyrotechnicraft.world.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.TrailSparkParticleOption;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.FireworkOrb;

public class ThrownFireworkOrbEntity extends ThrowableItemProjectile {

    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrownFireworkOrbEntity.class, EntityDataSerializers.ITEM_STACK);
    private int life = 0;
    public static final int MAX_LIFE = 120;

    public ThrownFireworkOrbEntity(EntityType<ThrownFireworkOrbEntity> type, Level level) {
        super(type, level);
    }

    public ThrownFireworkOrbEntity(Level level, Vec3 pos, ItemStack item){
        super(EntityTypeRegistry.FIREWORK_ORB.get(), level);
        this.setItem(item);
        this.life = 0;
        this.moveTo(pos);
        this.reapplyPosition();
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide()) {
            level().addParticle(new TrailSparkParticleOption(0xffffff), getX(), getY(), getZ(), 0, 0, 0);
        }

        if (life > MAX_LIFE) explode();
        life++;
    }

    public void setItem(ItemStack item) {
        if (!item.is(this.getDefaultItem()) || item.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, item.copyWithCount(1));
        }

    }
    @Override
    protected void onHit(HitResult pResult) {
        explode();
    }

    public void explode(){
        CompoundTag rootTag = entityData.get(DATA_ITEM_STACK).getOrCreateTag();
        CompoundTag wrap = wrapSingleOrb(rootTag, true);
        FireworkStarter starter = new FireworkStarter(level(), this.position(), wrap, this.getDeltaMovement());
        starter.setOwner(this.getOwner());
        level().addFreshEntity(starter);
        this.discard();
    }

    private static CompoundTag wrapSingleOrb(CompoundTag explosion, boolean isCustom){
        CompoundTag wrap = new CompoundTag();
        CompoundTag singlePayload = new CompoundTag();
        ListTag listWrap = new ListTag();
        singlePayload.putBoolean("IsCustom", isCustom);
        singlePayload.put("Payload", explosion);
        listWrap.add(singlePayload);
        wrap.put("PayloadList", listWrap);
        return wrap;
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
    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }
    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.FIREWORK_ORB.get();
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            tag.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        ItemStack itemstack = ItemStack.of(tag.getCompound("Item"));
        this.setItem(itemstack);
    }
}
