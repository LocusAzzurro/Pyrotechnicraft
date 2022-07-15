package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class FireworkShapeProcessor {

    private static final float FORCE_DEFAULT = 0.2f;
    private static final float JITTER_DEFAULT = 0.1f;
    public static final String SHAPE = "Shape";
    public static final String SHAPE_DATA = "ShapeData";
    public static final String FORCE = "Force";
    public static final String SPARKS = "Sparks";
    public static final String JITTER = "Jitter";
    public static final String UNIFORM = "Uniform";
    public static final String ROTATION_JITTER = "RotationJitter";
    public static final String WIDENESS = "Wideness";
    public static final String ABSOLUTE_ROTATION = "AbsoluteRotation";
    public static final String ROTATIONS_LIST = "Rotations";
    public static final String ROTATION_SINGLE = "Rotation";
    public static final String COORDINATES = "Coordinates";

    static Map<ExplosionShape, BiFunction<CompoundTag, Vec3, ? extends IExplosionShape>> MAP = new HashMap<>();

    public static IExplosionShape processShapeTag(CompoundTag tag, Vec3 vec){
        ExplosionShape shape = ExplosionShape.MAP.getOrDefault(tag.getString(SHAPE), ExplosionShape.SPHERE);
        return MAP.getOrDefault(shape, parseSphereExplosion).apply(tag, vec);
    }
    static BiFunction<CompoundTag, Vec3, SphereExplosion> parseSphereExplosion = (tag, vec) -> {
        CompoundTag data = tag.getCompound(SHAPE_DATA);
        float size = tag.contains(FORCE) ? tag.getFloat(FORCE) : FORCE_DEFAULT;
        int sparks = tag.contains(SPARKS) ? tag.getInt(SPARKS) : ExplosionShape.SPHERE.getFireworkMixtureValue();
        float jitter = data.contains(JITTER) ? data.getFloat(JITTER) : JITTER_DEFAULT;
        boolean uniform = data.contains(UNIFORM) && data.getBoolean(UNIFORM);
        return new SphereExplosion(size, sparks, jitter, uniform);
    };

    static BiFunction<CompoundTag, Vec3, RingExplosion> parseCircleExplosion = (tag, vec) -> {
        CompoundTag data = tag.getCompound(SHAPE_DATA);
        float size = tag.contains(FORCE) ? tag.getFloat(FORCE) : FORCE_DEFAULT;
        int sparks = tag.contains(SPARKS) ? tag.getInt(SPARKS) : ExplosionShape.RING.getFireworkMixtureValue();
        float jitter = data.contains(JITTER) ? data.getFloat(JITTER) : JITTER_DEFAULT;
        float rotationJitter = data.contains(ROTATION_JITTER) ? data.getFloat(ROTATION_JITTER) : 0.1f;
        boolean uniform = data.contains(UNIFORM) && data.getBoolean(UNIFORM);
        boolean absolute = data.contains(ABSOLUTE_ROTATION) && data.getBoolean(ABSOLUTE_ROTATION);
        ListTag rotationsList = data.contains(ROTATIONS_LIST) ? data.getList(ROTATIONS_LIST, ListTag.TAG_FLOAT) : null;
        float[] rotations;
        if (rotationsList == null) rotations = new float[]{0, 0};
        else {
            rotations = new float[rotationsList.size()];
            for (int i = 0; i < rotationsList.size(); i++){
                rotations[i] = rotationsList.getFloat(i);
            }
        }
        return new RingExplosion(vec, size, sparks, jitter, rotationJitter, uniform, absolute, rotations);
    };

    static BiFunction<CompoundTag, Vec3, BurstExplosion> parseBurstExplosion = (tag, vec) -> {
        CompoundTag data = tag.getCompound(SHAPE_DATA);
        float size = tag.contains(FORCE) ? tag.getFloat(FORCE) : FORCE_DEFAULT;
        int sparks = tag.contains(SPARKS) ? tag.getInt(SPARKS) : ExplosionShape.BURST.getFireworkMixtureValue();
        float jitter = data.contains(JITTER) ? data.getFloat(JITTER) : JITTER_DEFAULT;
        float rotationJitter = data.contains(ROTATION_JITTER) ? data.getFloat(ROTATION_JITTER) : 0.1f;
        float wideness = data.contains(WIDENESS) ? data.getFloat(WIDENESS) : Mth.PI / 12;
        boolean absolute = data.contains(ABSOLUTE_ROTATION) && data.getBoolean(ABSOLUTE_ROTATION);
        ListTag rotationsList = data.contains(ROTATIONS_LIST) ? data.getList(ROTATIONS_LIST, ListTag.TAG_FLOAT) : null;
        float[] rotations;
        if (rotationsList == null) rotations = new float[]{0, 0};
        else {
            rotations = new float[rotationsList.size()];
            for (int i = 0; i < rotationsList.size(); i++){
                rotations[i] = rotationsList.getFloat(i);
            }
        }
        return new BurstExplosion(vec, size, sparks, jitter, wideness, rotationJitter, absolute, rotations);
    };

    static BiFunction<CompoundTag, Vec3, PlaneExplosion> parsePlaneExplosion = (tag, vec) -> {
        CompoundTag data = tag.getCompound(SHAPE_DATA);
        float size = tag.contains(FORCE) ? tag.getFloat(FORCE) : FORCE_DEFAULT;
        float rotationJitter = data.contains(ROTATION_JITTER) ? data.getFloat(ROTATION_JITTER) : Mth.TWO_PI;
        float rotation = data.contains(ROTATION_SINGLE) ? data.getFloat(ROTATION_SINGLE) : 0.0f;
        boolean absolute = data.contains(ABSOLUTE_ROTATION) && data.getBoolean(ABSOLUTE_ROTATION);
        ListTag coordList = defaultPlaneCoords();
        if (data.contains(COORDINATES)){
            ListTag coords = data.getList(COORDINATES, ListTag.TAG_LIST);
            if (!coords.isEmpty()) coordList = coords;
        }
        List<Vec2> coordVecList = coordList.stream().filter(coord -> (coord instanceof ListTag coordPair && coordPair.getElementType() == ListTag.TAG_FLOAT))
                .map(coord -> new Vec2(((ListTag) coord).getFloat(0), ((ListTag) coord).getFloat(1))).toList();
        if (coordVecList.isEmpty()) {
            coordVecList = defaultPlaneCoords().stream().map(coord -> new Vec2(((ListTag) coord).getFloat(0), ((ListTag) coord).getFloat(1))).toList();
        }
        return new PlaneExplosion(vec, size, coordVecList, rotationJitter, absolute, rotation);
    };

    private static ListTag defaultPlaneCoords(){
        ListTag tag = new ListTag();
        ListTag p1 = new ListTag();
        ListTag p2 = new ListTag();
        ListTag p3 = new ListTag();
        ListTag p4 = new ListTag();
        p1.addAll(List.of(FloatTag.valueOf(1f), FloatTag.valueOf(1f)));
        p2.addAll(List.of(FloatTag.valueOf(1f), FloatTag.valueOf(-1f)));
        p3.addAll(List.of(FloatTag.valueOf(-1f), FloatTag.valueOf(-1f)));
        p4.addAll(List.of(FloatTag.valueOf(-1f), FloatTag.valueOf(1f)));
        tag.addAll(List.of(p1,p2,p3,p4));
        return tag;
    }

    static {
        MAP.put(ExplosionShape.SPHERE, parseSphereExplosion);
        MAP.put(ExplosionShape.RING, parseCircleExplosion);
        MAP.put(ExplosionShape.BURST, parseBurstExplosion);
        MAP.put(ExplosionShape.PLANE, parsePlaneExplosion);
    }
}
