package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record PlaneExplosion
        (Vec3 mov, float size, List<Vec2> coords, float rotationJitter ,boolean absoluteRotation, float rotation)
        implements IExplosionShape{

    @Override
    public List<Vec3> accept(IExplosionShapeVisitor visitor) {
        return visitor.visit(this);
    }
}
