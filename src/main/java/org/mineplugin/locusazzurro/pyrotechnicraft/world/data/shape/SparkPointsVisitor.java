package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SparkPointsVisitor implements ExplosionShapeVisitor{

    private Random random;

    public SparkPointsVisitor(Random random){
        this.random = random;
    }
    @Override
    public List<Vec3> visit(SphereExplosion exp) {
        List<Vec3> points = new ArrayList<>();
        boolean uniform = exp.isUniform();
        float jitter = exp.getJitter();
        float size = exp.getSize();
        for (int i = 0; i < exp.getPoints(); i++){
            Vec3 point = Vec3.ZERO;
            if (!uniform) {
                point = new Vec3(random.nextGaussian(), random.nextGaussian(), random.nextGaussian())
                        .normalize().scale(size).scale(1 + (random.nextFloat(jitter * 2) - jitter));
            }
            else {
                //todo uniform points
            }
            points.add(point);
        }
        return null; //todo
    }
}
