package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public final class HomingSystem {

    public static final Predicate<LivingEntity> none = e -> false;
    public static final Predicate<LivingEntity> isHostile = livingEntity -> !livingEntity.getType().getCategory().isFriendly();
    public static final Predicate<LivingEntity> isPlayer = livingEntity -> livingEntity instanceof Player;
    public static final String CURRENT_OPTION = "CurrentOption";
    public static final String TARGET_TYPE = "TargetType";
    public static final String MODE = "Mode";
    public static final String RANGE = "Range";
    public static final String APERTURE = "Aperture";
    public static final byte OPTION_TARGET_TYPE = 0;
    public static final byte OPTION_MODE = 1;
    public static final byte OPTION_RANGE = 2;
    public static final byte OPTION_APERTURE = 3;
    public static final byte TARGET_TYPE_NONE = 0;
    public static final byte TARGET_TYPE_HOSTILE = 1;
    public static final byte TARGET_TYPE_PLAYER = 2;
    public static final byte TARGET_MODE_RANGE = 0;
    public static final byte TARGET_MODE_LINE = 1;
    public static final String[] OPTIONS = new String[4];
    public static double[] RANGE_RADIUS = new double[]{5, 10, 20, 40};
    public static double[] LINE_RADIUS = new double[]{10, 20, 40, 80};
    public static double[] APERTURE_SIZE = new double[]{1, 3, 7, 15};
    public static final String HOMING_SYS_PREFIX = "data." + Pyrotechnicraft.MOD_ID + ".homing_system.";
    public static final Component TEXT_SELECTED_OPTION_TITLE = Component.translatable(HOMING_SYS_PREFIX + "selected_option.title");
    public static final Component TEXT_SELECTED_OPTION_TARGET_TYPE = Component.translatable(HOMING_SYS_PREFIX + "selected_option.target_type");
    public static final Component TEXT_SELECTED_OPTION_MODE = Component.translatable(HOMING_SYS_PREFIX + "selected_option.mode");
    public static final Component TEXT_SELECTED_OPTION_RANGE = Component.translatable(HOMING_SYS_PREFIX + "selected_option.range");
    public static final Component TEXT_SELECTED_OPTION_APERTURE = Component.translatable(HOMING_SYS_PREFIX + "selected_option.aperture");
    public static final Component TEXT_TARGET_TYPE_TITLE = Component.translatable(HOMING_SYS_PREFIX + "target_type.title");
    public static final Component TEXT_TARGET_TYPE_NONE = Component.translatable(HOMING_SYS_PREFIX + "target_type.none");
    public static final Component TEXT_TARGET_TYPE_HOSTILE = Component.translatable(HOMING_SYS_PREFIX + "target_type.hostile");
    public static final Component TEXT_TARGET_TYPE_PLAYER = Component.translatable(HOMING_SYS_PREFIX + "target_type.player");
    public static final Component TEXT_TARGET_TYPE_ALL = Component.translatable(HOMING_SYS_PREFIX + "target_type.all");
    public static final Component TEXT_MODE_TITLE = Component.translatable(HOMING_SYS_PREFIX + "mode.title");
    public static final Component TEXT_MODE_LINE = Component.translatable(HOMING_SYS_PREFIX + "mode.line");
    public static final Component TEXT_MODE_RANGED = Component.translatable(HOMING_SYS_PREFIX + "mode.ranged");
    public static final Component TEXT_RANGE_TITLE = Component.translatable(HOMING_SYS_PREFIX + "range.title");
    public static final Component TEXT_APERTURE_TITLE = Component.translatable(HOMING_SYS_PREFIX + "aperture.title");
    public static final Component TEXT_EXTENT_SMALL = Component.translatable(HOMING_SYS_PREFIX + "extent.small");
    public static final Component TEXT_EXTENT_MEDIUM = Component.translatable(HOMING_SYS_PREFIX + "extent.medium");
    public static final Component TEXT_EXTENT_LARGE = Component.translatable(HOMING_SYS_PREFIX + "extent.large");
    public static final Component TEXT_EXTENT_XLARGE = Component.translatable(HOMING_SYS_PREFIX + "extent.xlarge");
    public static final Component TEXT_NO_INFO = Component.translatable(HOMING_SYS_PREFIX + "no_info");
    public static Map<Byte, Component> SELECTED_OPTION_TEXT = new HashMap<>();
    public static Map<Byte, Component> TARGET_TYPE_TEXT = new HashMap<>();
    public static Map<Byte, Component> MODE_TEXT = new HashMap<>();
    public static Map<Byte, Component> EXTENT_TEXT = new HashMap<>();

    public static Optional<LivingEntity> findTarget(LivingEntity origin, byte targetType, byte mode, byte radius, byte aperture){
        Predicate<LivingEntity> predicate = none;
        if ((targetType & TARGET_TYPE_HOSTILE) > 0) predicate = predicate.or(isHostile);
        if ((targetType & TARGET_TYPE_PLAYER) > 0) predicate = predicate.or(isPlayer);
        if ((mode & TARGET_MODE_LINE) == 1) return lineTracing(origin, LINE_RADIUS[radius & 3], APERTURE_SIZE[aperture & 3], predicate);
        else return rangeFinding(origin, RANGE_RADIUS[radius & 3], predicate);
    }
    public static Optional<LivingEntity> lineTracing(LivingEntity origin, double distance, double size, Predicate<LivingEntity> predicate){
        Optional<LivingEntity> target = Optional.empty();
        Vec3 eyePos = origin.getEyePosition(1f);
        Vec3 lookVec = origin.getViewVector(1f).normalize();
        double hS = size / 2;
        double stepDist = 0.1d;
        int steps = (int) (distance / stepDist);
        AABB aabb = new AABB(eyePos.add(hS, hS, hS), eyePos.add(-hS, -hS, -hS));
        for (int i = 0; i < steps; i++){
            target = origin.level.getEntitiesOfClass(LivingEntity.class, aabb).stream()
                    .filter(targetCandidate -> targetCandidate != origin && origin.hasLineOfSight(targetCandidate)
                            && predicate.test(targetCandidate)).findFirst();
            if (target.isPresent()) return target;
            aabb = aabb.move(lookVec.scale(stepDist));
        }
        return target;
    }

    public static Optional<LivingEntity> rangeFinding(Entity origin, double radius, Predicate<LivingEntity> predicate){
        Optional<LivingEntity> target = Optional.empty();
        Vec3 pos = origin.position();
        double step = 1.0d;
        int steps = (int) (radius / step);
        AABB aabb = new AABB(step, step, step, -step, -step, -step).move(pos);
        for (int i = 0; i < steps; i++){
            target = origin.level.getEntitiesOfClass(LivingEntity.class, aabb).stream()
                    .filter(targetCandidate -> targetCandidate != origin
                            && predicate.test(targetCandidate)).findFirst();
            if (target.isPresent()) return target;
            aabb = aabb.inflate(step);
        }
        return target;
    }

    @Deprecated
    @Nullable
    public static LivingEntity rayTraceTarget(LivingEntity origin, double step, int depth, double size){
        Vec3 eyePos = origin.getEyePosition(1f);
        Vec3 lookVec = origin.getViewVector(1f);
        Vec3 checkPos = eyePos;
        double hS = size / 2;
        for (int i = 0 ; i < depth; i++){
            AABB aabb = new AABB(hS,hS,hS,-hS,-hS,-hS).move(checkPos);
            List<LivingEntity> entities = origin.level.getEntitiesOfClass(LivingEntity.class, aabb);
            for (LivingEntity entity : entities){
                if (entity != origin && origin.hasLineOfSight(entity)) return entity;
            }
            checkPos = checkPos.add(lookVec.scale(step));
        }
        return null;
    }

    static {
        OPTIONS[OPTION_TARGET_TYPE] = TARGET_TYPE;
        OPTIONS[OPTION_MODE] = MODE;
        OPTIONS[OPTION_RANGE] = RANGE;
        OPTIONS[OPTION_APERTURE] = APERTURE;
        SELECTED_OPTION_TEXT.put((byte) 0, TEXT_SELECTED_OPTION_TARGET_TYPE);
        SELECTED_OPTION_TEXT.put((byte) 1, TEXT_SELECTED_OPTION_MODE);
        SELECTED_OPTION_TEXT.put((byte) 2, TEXT_SELECTED_OPTION_RANGE);
        SELECTED_OPTION_TEXT.put((byte) 3, TEXT_SELECTED_OPTION_APERTURE);
        TARGET_TYPE_TEXT.put((byte) 0, TEXT_TARGET_TYPE_NONE);
        TARGET_TYPE_TEXT.put((byte) 1, TEXT_TARGET_TYPE_HOSTILE);
        TARGET_TYPE_TEXT.put((byte) 2, TEXT_TARGET_TYPE_PLAYER);
        TARGET_TYPE_TEXT.put((byte) 3, TEXT_TARGET_TYPE_ALL);
        MODE_TEXT.put((byte) 0, TEXT_MODE_RANGED);
        MODE_TEXT.put((byte) 1, TEXT_MODE_LINE);
        EXTENT_TEXT.put((byte) 0, TEXT_EXTENT_SMALL);
        EXTENT_TEXT.put((byte) 1, TEXT_EXTENT_MEDIUM);
        EXTENT_TEXT.put((byte) 2, TEXT_EXTENT_LARGE);
        EXTENT_TEXT.put((byte) 3, TEXT_EXTENT_XLARGE);
    }

    public static Optional<Entity> getTargetById(Level level, UUID targetUUID, int targetNetworkId) {
        if (targetUUID != null && level instanceof ServerLevel serverLevel) {
            return Optional.ofNullable(serverLevel.getEntity(targetUUID));
        } else {
            return Optional.ofNullable(level.getEntity(targetNetworkId));
        }
    }
}
