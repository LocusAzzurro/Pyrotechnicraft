package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public sealed interface IExplosionShape permits SphereExplosion {

    List<Vec3> accept(IExplosionShapeVisitor visitor);

}
