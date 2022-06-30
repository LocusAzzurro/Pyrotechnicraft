package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class ExplosionShape {

    abstract List<Vec3> accept(ExplosionShapeVisitor visitor);

}
