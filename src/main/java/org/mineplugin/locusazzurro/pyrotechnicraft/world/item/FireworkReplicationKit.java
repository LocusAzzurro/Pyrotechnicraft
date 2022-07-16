package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class FireworkReplicationKit extends Item{

    public FireworkReplicationKit() {
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB).stacksTo(16).rarity(Rarity.UNCOMMON));
    }
}
