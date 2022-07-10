package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public record SphereExplosion(float size, int points, float jitter, boolean uniform) implements IExplosionShape {

    public SphereExplosion(float size, int points){
        this(size, points, 0.1f, false);
    }
    @Override
    public List<Vec3> accept(IExplosionShapeVisitor visitor) {
        return visitor.visit(this);
    }
}
