package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FireworkShapeProcessor {

    static Map<ExplosionShape, BiFunction<CompoundTag, Vec3, ? extends IExplosionShape>> MAP = new HashMap<>();

    public static IExplosionShape processShapeTag(CompoundTag tag, Vec3 vec){
        ExplosionShape shape = ExplosionShape.MAP.getOrDefault(tag.getString("Shape"), ExplosionShape.SPHERE);
        return MAP.getOrDefault(shape, parseSphereExplosion).apply(tag, vec);
    }

    static BiFunction<CompoundTag, Vec3, SphereExplosion> parseSphereExplosion = (tag, vec) -> {
        CompoundTag data = tag.getCompound("ShapeData");
        float size = tag.contains("Force") ? tag.getFloat("Force") : 0.2f;
        int sparks = tag.contains("Sparks") ? tag.getInt("Sparks") : ExplosionShape.SPHERE.getFireworkMixtureValue();
        float jitter = data.contains("Jitter") ? data.getFloat("Jitter") : 0.1f;
        boolean uniform = data.contains("Uniform") && data.getBoolean("Uniform");
        return new SphereExplosion(size, sparks, jitter, uniform);
    };

    static BiFunction<CompoundTag, Vec3, RingExplosion> parseCircleExplosion = (tag, vec) -> {
        CompoundTag data = tag.getCompound("ShapeData");
        float size = tag.contains("Force") ? tag.getFloat("Force") : 0.2f;
        int sparks = tag.contains("Sparks") ? tag.getInt("Sparks") : ExplosionShape.RING.getFireworkMixtureValue();
        float jitter = data.contains("Jitter") ? data.getFloat("Jitter") : 0.1f;
        float rotationJitter = data.contains("RotationJitter") ? data.getFloat("RotationJitter") : 0.1f;
        boolean uniform = data.contains("Uniform") && data.getBoolean("Uniform");
        boolean absolute = data.contains("AbsoluteRotation") && data.getBoolean("AbsoluteRotation");
        ListTag rotationsList = data.contains("Rotations") ? data.getList("Rotations", 5) : null;
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
        CompoundTag data = tag.getCompound("ShapeData");
        float size = tag.contains("Force") ? tag.getFloat("Force") : 0.2f;
        int sparks = tag.contains("Sparks") ? tag.getInt("Sparks") : ExplosionShape.BURST.getFireworkMixtureValue();
        float jitter = data.contains("Jitter") ? data.getFloat("Jitter") : 0.1f;
        float rotationJitter = data.contains("RotationJitter") ? data.getFloat("RotationJitter") : 0.1f;
        float wideness = data.contains("Wideness") ? data.getFloat("Wideness") : Mth.PI / 12;
        boolean absolute = data.contains("AbsoluteRotation") && data.getBoolean("AbsoluteRotation");
        ListTag rotationsList = data.contains("Rotations") ? data.getList("Rotations", 5) : null;
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
        CompoundTag data = tag.getCompound("ShapeData");
        float size = tag.contains("Force") ? tag.getFloat("Force") : 0.2f;
        float rotationJitter = data.contains("RotationJitter") ? data.getFloat("RotationJitter") : Mth.TWO_PI;
        float rotation = data.contains("Rotation") ? data.getFloat("Rotation") : 0.0f;
        boolean absolute = data.contains("AbsoluteRotation") && data.getBoolean("AbsoluteRotation");
        ListTag coordList = defaultPlaneCoords();
        if (data.contains("Coordinates")){
            ListTag coords = data.getList("Coordinates", 9);
            if (!coords.isEmpty()) coordList = coords;
        }
        List<Vec2> coordVecList = coordList.stream().filter(coord -> (coord instanceof ListTag coordPair && coordPair.getElementType() == 5))
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
