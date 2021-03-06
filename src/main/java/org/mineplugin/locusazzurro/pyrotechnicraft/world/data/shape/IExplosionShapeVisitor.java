package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface IExplosionShapeVisitor {
    List<Vec3> visit(SphereExplosion exp);
    List<Vec3> visit(RingExplosion exp);
    List<Vec3> visit(BurstExplosion exp);
    List<Vec3> visit(PlaneExplosion exp);
    List<Vec3> visit(MatrixExplosion exp);
}
