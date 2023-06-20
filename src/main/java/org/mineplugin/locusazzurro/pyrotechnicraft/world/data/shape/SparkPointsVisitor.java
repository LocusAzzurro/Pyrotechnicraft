package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SparkPointsVisitor implements IExplosionShapeVisitor {

    private static final float PHI = Mth.PI * (3.0f - Mth.sqrt(5.0f));
    private static final float INFINITESIMAL = 1.175494e-38f;
    private final RandomSource random;

    public SparkPointsVisitor(RandomSource random){
        this.random = random;
    }
    @Override
    public List<Vec3> visit(SphereExplosion exp) {
        List<Vec3> points = new ArrayList<>();
        boolean uniform = exp.uniform();
        float jitter = clampJitterValue(exp.jitter());
        float size = exp.size();
        for (int i = 0; i < exp.points(); i++){
            Vec3 point;
            if (!uniform) {
                point = new Vec3(random.nextGaussian(), random.nextGaussian(), random.nextGaussian())
                        .normalize().scale(size).scale(1 + (random.nextFloat() * (jitter * 2) - jitter));
            } else {
                float theta, x, y, z, r;
                y = 1 - (i / (float) (exp.points() - 1)) * 2;
                r = Mth.sqrt(1 - y * y);
                theta = PHI * i;
                x = Mth.cos(theta) * r;
                z = Mth.sin(theta) * r;
                point = new Vec3(x, y, z).scale(size).scale(1 + (random.nextFloat() * (jitter * 2) - jitter));
            }
            points.add(point);
        }
        return points;
    }

    @Override
    public List<Vec3> visit(RingExplosion exp) {
        List<Vec3> points = new ArrayList<>();
        boolean uniform = exp.uniform();
        boolean absolute = exp.absoluteRotation();
        float jitter = clampJitterValue(exp.jitter());
        float rotationJitter = clampRotationJitterValue(exp.rotationJitter());
        float size = exp.size();
        float[] rotations = exp.rotations();
        for (int i = 0; i < exp.points(); i++){
            Vec3 point;
            if (!uniform){
                point = new Vec3(random.nextGaussian(), 0, random.nextGaussian()).normalize();
            }
            else {
                float x,z;
                x = Mth.cos(Mth.TWO_PI / exp.points() * i);
                z = Mth.sin(Mth.TWO_PI / exp.points() * i);
                point = new Vec3(x, 0, z).normalize();
            }
            points.add(point);
        }

        float rotX = 0, rotY = 0;
        if (rotations != null){
            rotX = rotations.length >= 1 ? rotations[0] : 0;
            rotY = rotations.length >= 2 ? rotations[1] : 0;
        }

        float pitch = (float) Math.asin(-exp.mov().y());
        float yaw = (float) Mth.atan2(exp.mov().x(), exp.mov().z());
        float randRotX = random.nextFloat() * (rotationJitter * 2) - rotationJitter;
        float randRotY = random.nextFloat() * (rotationJitter * 2) - rotationJitter;
        float finalRotX = rotX;
        float finalRotY = rotY;

        return points.stream().map(point -> {
            if (!absolute) point = point.xRot(-pitch + Mth.HALF_PI).yRot(yaw);
            point = point.xRot(finalRotX).yRot(finalRotY)
                    .xRot(randRotX).yRot(randRotY)
                    .scale(size).scale(1 + random.nextFloat() * (jitter * 2) - jitter);
            return point;
        }).toList();
    }

    @Override
    public List<Vec3> visit(BurstExplosion exp) {
        List<Vec3> points = new ArrayList<>();
        boolean absolute = exp.absoluteRotation();
        float jitter = clampJitterValue(exp.jitter());
        float rotationJitter = clampRotationJitterValue(exp.rotationJitter());
        float size = exp.size();
        float wideness = exp.wideness();
        float[] rotations = exp.rotations();
        Vec3 startVec = new Vec3(0,1,0);

        float rotX = 0, rotY = 0, rotZ = 0;
        if (rotations != null){
            rotX = rotations.length >= 1 ? rotations[0] : 0;
            rotY = rotations.length >= 2 ? rotations[1] : 0;
            rotZ = rotations.length >= 3 ? rotations[2] : 0;
        }
        if (!absolute && exp.mov().length() > 1.0e-8f){
            startVec = exp.mov().normalize();
        }
        float randRotX = random.nextFloat() * (rotationJitter * 2) - rotationJitter;
        float randRotY = random.nextFloat() * (rotationJitter * 2) - rotationJitter;
        float randRotZ = random.nextFloat() * (rotationJitter * 2) - rotationJitter;

        startVec = startVec.xRot(rotX).yRot(rotY).zRot(rotZ)
                .xRot(randRotX).yRot(randRotY).zRot(randRotZ);

        for (int i = 0; i < exp.points(); i++) {
            Vec3 point = startVec.xRot(random.nextFloat() * (wideness * 2) - wideness)
                    .yRot(random.nextFloat() * (wideness * 2) - wideness)
                    .zRot(random.nextFloat() * (wideness * 2) - wideness)
                    .scale(size).scale(1 + (random.nextFloat() * (jitter * 2) - jitter));
            points.add(point);
        }
        return points;
    }

    @Override
    public List<Vec3> visit(PlaneExplosion exp) {
        List<Vec3> points = new ArrayList<>();
        List<Vec2> coords = exp.coords();
        boolean absolute = exp.absoluteRotation();
        float rotation = exp.rotation();
        float rotationJitter = clampRotationJitterValue(exp.rotationJitter());
        float randRot = random.nextFloat() * (rotationJitter * 2) - rotationJitter;
        coords.forEach(coord -> {
            Vec2 vec2 = clampVec2(coord);
            Vec3 vec3 = new Vec3(0, vec2.y, vec2.x);
            if (!absolute){
                vec3 = vec3.yRot((float) -Mth.atan2(exp.mov().z(), exp.mov().x()));
            }
            points.add(vec3.yRot(rotation + randRot).scale(exp.size()));
        });
        return points;
    }

    @Override
    public List<Vec3> visit(MatrixExplosion exp) {
        List<Vec3> points = new ArrayList<>();
        List<Vec3> coords = exp.coords();
        float[] rotations = exp.rotations();
        float[] rotationJitters = exp.rotationJitters();

        float rotX = 0, rotY = 0 ,rotZ = 0;
        if (rotations != null){
            rotX = rotations.length >= 1 ? rotations[0] : 0;
            rotY = rotations.length >= 2 ? rotations[1] : 0;
            rotZ = rotations.length >= 3 ? rotations[2] : 0;
        }

        float randRotX = 0, randRotY = 0, randRotZ = 0;
        if (rotationJitters != null) {
            for (int i = 0; i < rotationJitters.length; i++) {
                rotationJitters[i] = clampRotationJitterValue(rotationJitters[i]);
            }
            randRotX = rotationJitters.length >= 1 ? random.nextFloat() * (rotationJitters[0] * 2) - rotationJitters[0] : 0f;
            randRotY = rotationJitters.length >= 2 ? random.nextFloat() * (rotationJitters[1] * 2) - rotationJitters[1] : 0f;
            randRotZ = rotationJitters.length >= 3 ? random.nextFloat() * (rotationJitters[2] * 2) - rotationJitters[2] : 0f;
        }

        float finalRotX = rotX; float finalRotY = rotY; float finalRotZ = rotZ;
        float finalRandRotX = randRotX; float finalRandRotY = randRotY; float finalRandRotZ = randRotZ;
        coords.forEach(coord -> {
            Vec3 coordVec3 = clampVec3(coord);
            coordVec3 = coordVec3.xRot(finalRotX + finalRandRotX).yRot(finalRotY + finalRandRotY).zRot(finalRotZ + finalRandRotZ)
                    .scale(exp.size());
            points.add(coordVec3);
        });
        return points;
    }

    private static Vec2 clampVec2(Vec2 vec2){
        return new Vec2(Mth.clamp(vec2.x, -1, 1), Mth.clamp(vec2.y, -1, 1));
    }

    private static Vec3 clampVec3(Vec3 vec3){
        return new Vec3(Mth.clamp(vec3.x, -1, 1), Mth.clamp(vec3.y, -1, 1), Mth.clamp(vec3.z, -1, 1));
    }

    private static float clampJitterValue(float jitterValue){
        return Mth.clamp(jitterValue, INFINITESIMAL, 1.0f);
    }

    private static float clampRotationJitterValue(float jitterValue){
        return Mth.abs(jitterValue) > INFINITESIMAL ? jitterValue : INFINITESIMAL;
    }
}
