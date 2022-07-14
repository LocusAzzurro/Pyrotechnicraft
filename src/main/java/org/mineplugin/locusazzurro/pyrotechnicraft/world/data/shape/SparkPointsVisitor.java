package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SparkPointsVisitor implements IExplosionShapeVisitor {

    private static final float PHI = Mth.PI * (3.0f - Mth.sqrt(5.0f));
    private static final float INFINITESIMAL = 1.175494e-38f;
    private final Random random;

    public SparkPointsVisitor(Random random){
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
                        .normalize().scale(size).scale(1 + (random.nextFloat(jitter * 2) - jitter));
            } else {
                float theta, x, y, z, r;
                y = 1 - (i / (float) (exp.points() - 1)) * 2;
                r = Mth.sqrt(1 - y * y);
                theta = PHI * i;
                x = Mth.cos(theta) * r;
                z = Mth.sin(theta) * r;
                point = new Vec3(x, y, z).scale(size).scale(1 + (random.nextFloat(jitter * 2) - jitter));
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
                point = new Vec3(random.nextGaussian(), 0, random.nextGaussian())
                        .normalize().scale(size).scale(1 + (random.nextFloat(jitter * 2) - jitter));
            }
            else {
                float x,z;
                x = Mth.cos(Mth.TWO_PI / exp.points() * i);
                z = Mth.sin(Mth.TWO_PI / exp.points() * i);
                point = new Vec3(x, 0, z)
                        .normalize().scale(size).scale(1 + (random.nextFloat(jitter * 2) - jitter));
            }
            points.add(point);
        }

        float rotX = 0, rotZ = 0;
        if (rotations != null){
            rotX = rotations.length >= 1 ? rotations[0] : 0;
            rotZ = rotations.length >= 2 ? rotations[1] : 0;
        }

        List<Vec3> rotatedPoints = new ArrayList<>();
        float finalRotX = rotX;
        float finalRotZ = rotZ;
        float randRotX = random.nextFloat(rotationJitter * 2) - rotationJitter;
        float randRotZ = random.nextFloat(rotationJitter * 2) - rotationJitter;
        points.forEach(point -> {
            if (!absolute){
                point = point
                        .xRot((float) Mth.atan2(exp.mov().z(), exp.mov().y()))
                        .zRot((float) Mth.atan2(exp.mov().x(), exp.mov().y()));
            }
            point = point.xRot(finalRotX).zRot(finalRotZ)
                    .xRot(randRotX).zRot(randRotZ);
            rotatedPoints.add(point);
        });
        return rotatedPoints;
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
        float randRotX = random.nextFloat(rotationJitter * 2) - rotationJitter;
        float randRotY = random.nextFloat(rotationJitter * 2) - rotationJitter;
        float randRotZ = random.nextFloat(rotationJitter * 2) - rotationJitter;

        startVec = startVec.xRot(rotX).yRot(rotY).zRot(rotZ)
                .xRot(randRotX).yRot(randRotY).zRot(randRotZ);

        for (int i = 0; i < exp.points(); i++) {
            Vec3 point = startVec.xRot(random.nextFloat(wideness * 2) - wideness)
                    .yRot(random.nextFloat(wideness * 2) - wideness)
                    .zRot(random.nextFloat(wideness * 2) - wideness)
                    .scale(size).scale(1 + (random.nextFloat(jitter * 2) - jitter));
            points.add(point);
        }
        return points;
    }

    private static float clampJitterValue(float jitterValue){
        return Mth.clamp(jitterValue, INFINITESIMAL, 1.0f);
    }

    private static float clampRotationJitterValue(float jitterValue){
        return Mth.abs(jitterValue) > INFINITESIMAL ? jitterValue : INFINITESIMAL;
    }
}
