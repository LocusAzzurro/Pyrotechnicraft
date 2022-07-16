package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record MatrixExplosion
        (Vec3 mov, float size, List<Vec3> coords, float[] rotationJitters, float[] rotations)
        implements IExplosionShape{

    @Override
    public List<Vec3> accept(IExplosionShapeVisitor visitor) {
        return visitor.visit(this);
    }
}
