package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SphereExplosion extends ExplosionShape{

    private float size;
    private int points;
    private float jitter;
    private boolean uniform;

    public SphereExplosion(float size, int points, float jitter, boolean uniform){
        this.size = size;
        this.points = points;
        this.jitter = jitter;
        this.uniform = uniform;
    }

    public SphereExplosion(int size, int points){
        this(size, points, 0.1f, false);
    }

    public void setJitter(float jitter){
        this.jitter = jitter;
    }

    public void setUniform(boolean uniform){
        this.uniform = uniform;
    }

    public float getSize() {return size;}
    public int getPoints() {return points;}
    public float getJitter() {return jitter;}
    public boolean isUniform() {return uniform;}

    @Override
    List<Vec3> accept(ExplosionShapeVisitor visitor) {
        return visitor.visit(this);
    }
}
