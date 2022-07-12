package org.mineplugin.locusazzurro.pyrotechnicraft.client.color;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public class FireworkOrbColorer implements ItemColor {

    private static int DEFAULT_COLOR = 0x8a8a8a;

    @Override
    public int getColor(@NotNull ItemStack pStack, int pTintIndex) {
        if (pTintIndex != 1) return -1;
        else {
            CompoundTag tag = pStack.getOrCreateTag();
            if (pStack.is(ItemRegistry.FIREWORK_ORB.get()) && tag.contains("DisplayColor")){
                return tag.getInt("DisplayColor");
            }
            else return DEFAULT_COLOR;
        }
    }
}
