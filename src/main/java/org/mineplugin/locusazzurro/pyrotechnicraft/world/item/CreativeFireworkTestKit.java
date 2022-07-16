package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class CreativeFireworkTestKit extends Item{

    public CreativeFireworkTestKit() {
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(1).rarity(Rarity.EPIC));
    }
}
