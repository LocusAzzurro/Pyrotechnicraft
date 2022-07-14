package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public record BurstExplosion
        (Vec3 mov, float size, int points, float jitter, float wideness, float rotationJitter, boolean absoluteRotation, float[] rotations)
        implements IExplosionShape{

    @Override
    public List<Vec3> accept(IExplosionShapeVisitor visitor) {
        return visitor.visit(this);
    }
}
