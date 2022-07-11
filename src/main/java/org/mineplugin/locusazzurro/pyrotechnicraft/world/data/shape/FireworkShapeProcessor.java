package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

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
        float jitter = data.contains("Jitter") ? tag.getFloat("Jitter") : 0.1f;
        boolean uniform = data.contains("Uniform") && tag.getBoolean("Uniform");
        return new SphereExplosion(size, sparks, jitter, uniform);
    };

    static {
        MAP.put(ExplosionShape.SPHERE, parseSphereExplosion);
    }
}
