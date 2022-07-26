package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraftforge.items.IItemHandler;

public interface IFireworkCraftingTableContainer {
    boolean hasValidItemsForCrafting();
    void addCraftingSlots(IItemHandler handler);
}
