package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;

public class VanillaFireworkPayloadData extends FireworkPayloadData {
    private CompoundTag payload = new CompoundTag();

    public VanillaFireworkPayloadData(ItemStack firework){
        if (firework.getItem() instanceof FireworkRocketItem){
            this.payload = firework.getOrCreateTagElement("Fireworks");
        }
    }

    @Override
    public CompoundTag serialize() {
        return payload;
    }

    @Override
    public boolean isVanillaFormat() {
        return true;
    }
}
