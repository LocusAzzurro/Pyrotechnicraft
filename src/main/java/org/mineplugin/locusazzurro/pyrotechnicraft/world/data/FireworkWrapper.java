package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public abstract class FireworkWrapper {
    @Deprecated
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

    public static CompoundTag wrapSingleFireworkExplosion(ItemStack itemStack){
        CompoundTag wrap = new CompoundTag();
        if (itemStack.is(Items.FIREWORK_STAR)){
            CompoundTag exp = itemStack.getOrCreateTag().getCompound("Explosion");
            wrap.putBoolean("IsCustom", false);
            wrap.put("Payload", exp);
        }
        else if (itemStack.is(ItemRegistry.FIREWORK_ORB.get())){
            CompoundTag exp = itemStack.getOrCreateTag();
            wrap.putBoolean("IsCustom", true);
            wrap.put("Payload", exp);
        }
        return wrap;
    }

    /**
     Wraps the "Explosion" tag from a Firework Star into a Firework Rocket like structure to be processed by vanilla particle engine
     @param tag A CompoundTag corresponding to the value of getCompound("Explosion") from a Firework Star
     @return A CompoundTag corresponding to the value of getCompound("Fireworks") from a Firework Rocket
     */
    public static CompoundTag wrapSingletonFireworkStar(CompoundTag tag){
        CompoundTag wrap = new CompoundTag();
        ListTag list = new ListTag();
        list.add(tag);
        wrap.put("Explosions", list);
        return wrap;
    }

}
