package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface ExplosionShapeVisitor {
    List<Vec3> visit(SphereExplosion exp);

}
