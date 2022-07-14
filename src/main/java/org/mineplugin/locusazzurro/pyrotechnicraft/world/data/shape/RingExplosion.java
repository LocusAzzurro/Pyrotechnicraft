package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

/*
rotationJitter is applied after rotations
without absoluteRotation the starting angle is perpendicular to move vector, else starts from perpendicular to (0,1,0)
 */
public record RingExplosion
        (Vec3 mov, float size, int points, float jitter, float rotationJitter, boolean uniform, boolean absoluteRotation, float[] rotations)
    implements IExplosionShape
{
    @Override
    public List<Vec3> accept(IExplosionShapeVisitor visitor) {
        return visitor.visit(this);
    }
}
