package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreativeTabs {

    public static class CreativeTab extends CreativeModeTab {

        public CreativeTab() {
            super("pyrotechnicraft");
        }
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.FIREWORK_MISSILE.get());
        }
    }
}
