package org.mineplugin.locusazzurro.pyrotechnicraft.client.color;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public class FlickerStickColorer implements ItemColor {

    public static final int BASE_COLOR = 0xffffff;

    @Override
    public int getColor(ItemStack pStack, int pTintIndex) {
        if (pStack.is(ItemRegistry.FLICKER_STICK.get()) && pTintIndex == 1){
            CompoundTag tag = pStack.getOrCreateTag();
            return tag.contains("SparkColor") ? tag.getInt("SparkColor") : BASE_COLOR;
        }
        return -1;
    }

}
