package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.SoundEventRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TacticalScript extends Item{

    public TacticalScript(){
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);

        if (!level.isClientSide()){
            if (hand != InteractionHand.MAIN_HAND)
                return InteractionResultHolder.pass(item);
            ItemStack itemOffhand = player.getOffhandItem();
            if (!itemOffhand.is(ItemRegistry.FIREWORK_MISSILE.get()))
                return InteractionResultHolder.pass(item);
            CompoundTag tag = item.getOrCreateTag();
            TacticalScriptType type = TacticalScriptType.byName(tag.getString("Type"));
            if (type == TacticalScriptType.NONE)
                return InteractionResultHolder.pass(item);

            int requiredAmount = tag.getInt("RequiredMissiles");
            if (itemOffhand.getCount() < requiredAmount)
                return InteractionResultHolder.pass(item);
            int multiplier = tag.getInt("Multiplier");
            int totalMissiles = requiredAmount * multiplier;
            RandomSource random = level.getRandom();

            switch (type){
                case SCATTER -> {
                    for (int i = 0; i < totalMissiles; i++){
                        Vec3 vec = new Vec3(random.nextFloat() - 0.5f, random.nextFloat() - 0.5f, random.nextFloat() - 0.5f).normalize();
                        FireworkMissileEntity fireworkEntity = new FireworkMissileEntity(level, player.getEyePosition(), itemOffhand.getOrCreateTag());
                        fireworkEntity.setOwner(player);
                        fireworkEntity.moveTo(
                                player.getX() + vec.x() + random.nextFloat() * 0.1f,
                                player.getY() + vec.y() + random.nextFloat() * 0.1f,
                                player.getZ() + vec.z() + random.nextFloat() * 0.1f);
                        fireworkEntity.shoot(vec.x() * 2, vec.y() * 2, vec.z() * 2, 1.0f, 0.2f);
                        level.addFreshEntity(fireworkEntity);
                    }
                }
                case RING -> {
                    for (int i = 0; i < totalMissiles; i++){
                        Vec3 vec = new Vec3(random.nextFloat() - 0.5f, 0.0f, random.nextFloat() - 0.5f).normalize();
                        FireworkMissileEntity fireworkEntity = new FireworkMissileEntity(level, player.getEyePosition(), itemOffhand.getOrCreateTag());
                        fireworkEntity.setOwner(player);
                        fireworkEntity.moveTo(
                                player.getX() + vec.x() + random.nextFloat() * 0.1f,
                                player.getY() + 1.0f,
                                player.getZ() + vec.z() + random.nextFloat() * 0.1f);
                        fireworkEntity.shoot(vec.x() * 2, 0.0f, vec.z() * 2, 1.0f, 0.2f);
                        level.addFreshEntity(fireworkEntity);
                    }
                }
                case CLUSTER -> {
                    for (int i = 0; i < totalMissiles; i++){
                        FireworkMissileEntity fireworkEntity = new FireworkMissileEntity(level, player.getEyePosition(), itemOffhand.getOrCreateTag());
                        fireworkEntity.setOwner(player);
                        fireworkEntity.shootFromRotation(player,
                                player.getXRot() + (random.nextFloat() * 20 - 10), player.getYRot() + (random.nextFloat() * 20 - 10),
                                0.0f, 1.0f, 5.0f);
                        level.addFreshEntity(fireworkEntity);
                    }
                }
                case SHOWER -> {
                    for (int i = 0; i < totalMissiles; i++){
                        Vec3 vec = new Vec3(random.nextFloat() - 0.5f, random.nextFloat() - 0.5f, random.nextFloat() - 0.5f).scale(10.0);
                        FireworkMissileEntity fireworkEntity = new FireworkMissileEntity(level, player.getEyePosition(), itemOffhand.getOrCreateTag());
                        fireworkEntity.setOwner(player);
                        fireworkEntity.moveTo(
                                player.getX() + vec.x() + random.nextFloat() * 0.1f,
                                player.getY() + vec.y() + random.nextFloat() * 0.1f,
                                player.getZ() + vec.z() + random.nextFloat() * 0.1f);
                        Vec3 mov = player.getDeltaMovement();
                        double speed = Mth.absMax(mov.length(), 0.1);
                        fireworkEntity.shoot(
                                mov.x() * 3 + random.nextFloat(),
                                mov.y() * 3 + random.nextFloat(),
                                mov.z() * 3 + random.nextFloat(),
                                (float) speed, 0.1f);
                        level.addFreshEntity(fireworkEntity);
                    }
                }
            }

            level.playSound(null, player.getX(), player.getEyeY(), player.getZ(),
                    SoundEventRegistry.FIREWORK_MISSILE_LAUNCH.get(), SoundSource.PLAYERS,
                    3.0f,1.0f  + random.nextFloat() * (0.1f) - 0.05f);

            if (!player.isCreative()) {
                item.shrink(1);
                itemOffhand.shrink(requiredAmount);
                if (item.isEmpty()) {
                    player.getInventory().removeItem(item);
                }
                if (itemOffhand.isEmpty()) {
                    player.getInventory().removeItem(itemOffhand);
                }
                player.getCooldowns().addCooldown(this, 80);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.sidedSuccess(item, level.isClientSide());
    }

    @Override
    public ItemStack getDefaultInstance() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("RequiredMissiles", 4);
        tag.putInt("Multiplier", 3);
        tag.putString("Type", TacticalScriptType.SCATTER.name);
        ItemStack item = new ItemStack(ItemRegistry.TACTICAL_SCRIPT.get());
        item.setTag(tag);
        return item;
    }

    public enum TacticalScriptType{
        SCATTER("scatter"), SHOWER("shower"), RING("ring"), CLUSTER("cluster"), NONE("none");

        final String name;
        private static final Map<String, TacticalScriptType> MAP;

        TacticalScriptType(String name){
            this.name = name;
        }

        public static TacticalScriptType byName(String name){
            return MAP.getOrDefault(name, NONE);
        }

        static {
            Map<String, TacticalScriptType> map = new HashMap<>();
            for (TacticalScriptType type : TacticalScriptType.values()){
                map.put(type.name, type);
            }
            MAP = Collections.unmodifiableMap(map);
        }
    }

}
