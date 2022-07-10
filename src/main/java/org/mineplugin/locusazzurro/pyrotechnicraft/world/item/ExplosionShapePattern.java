package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.world.item.Item;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;

public class ExplosionShapePattern extends Item {

    private final ExplosionShape shape;

    public ExplosionShapePattern(ExplosionShape shape) {
        super(new Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(1));
        this.shape = shape;
    }

    public ExplosionShape getShape(){return shape;}

}
