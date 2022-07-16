package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.world.item.Item;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class Firecracker extends Item{

    public Firecracker()  {
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(16));
    }
}
