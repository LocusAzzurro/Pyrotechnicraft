package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreativeTabs {

    public static class CreativeTab extends CreativeModeTab {

        private static final ItemStack ICON_ITEM = new ItemStack(Items.FIREWORK_ROCKET);

        public CreativeTab() {
            super("pyrotechnicraft");
        }
        @Override
        public ItemStack makeIcon() {
            return ICON_ITEM;
        }
    }
}
