package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;

public abstract class VanillaFireworkWrapper {
    public static CompoundTag wrapPayload(ItemStack firework){
        CompoundTag payload = new CompoundTag();
        if (firework.getItem() instanceof FireworkRocketItem){
            payload = firework.getOrCreateTagElement("Fireworks");
        }
        CompoundTag wrap = new CompoundTag();
        wrap.put("Payload", payload);
        wrap.putBoolean("IsCustom", false);
        return wrap;
    }

}
