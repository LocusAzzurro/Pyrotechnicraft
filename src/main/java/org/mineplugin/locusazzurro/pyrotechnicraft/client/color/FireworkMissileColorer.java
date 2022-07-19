package org.mineplugin.locusazzurro.pyrotechnicraft.client.color;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public class FireworkMissileColorer implements ItemColor {

    private static int DEFAULT_BASE_COLOR = 0xB92929;
    private static int DEFAULT_PATTERN_COLOR = 0xF4F4F4;

    @Override
    public int getColor(ItemStack pStack, int pTintIndex) {
        if (!pStack.is(ItemRegistry.FIREWORK_MISSILE.get())) return -1;
        CompoundTag tag = pStack.getOrCreateTag();
        if (pTintIndex == 0) return tag.contains("BaseColor") ? tag.getInt("BaseColor") : DEFAULT_BASE_COLOR;
        else if (pTintIndex == 1) return tag.contains("PatternColor") ? tag.getInt("PatternColor") : DEFAULT_PATTERN_COLOR;
        else return -1;
    }
}
