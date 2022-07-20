package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public abstract class FireworkWrapper {

    public static final String FIREWORKS = "Fireworks";
    public static final String PAYLOAD = "Payload";
    public static final String IS_CUSTOM = "IsCustom";
    public static final String EXPLOSION_SINGLE = "Explosion";
    public static final String EXPLOSIONS_LIST = "Explosions";

    @Deprecated
    public static CompoundTag wrapPayload(ItemStack firework){
        CompoundTag payload = new CompoundTag();
        if (firework.getItem() instanceof FireworkRocketItem){
            payload = firework.getOrCreateTagElement(FIREWORKS);
        }
        CompoundTag wrap = new CompoundTag();
        wrap.put(PAYLOAD, payload);
        wrap.putBoolean(IS_CUSTOM, false);
        return wrap;
    }

    /**
     * Wraps the tag from a Firework Star or Firework Orb ItemStack into format used in Firework Missile explosions list
     * @param itemStack a Firework Star or Firework Orb with explosion info tag
     * @return wrapped Tag ready to be written into Firework Missile
     */
    public static CompoundTag wrapSingleFireworkExplosion(ItemStack itemStack){
        CompoundTag wrap = new CompoundTag();
        if (itemStack.is(Items.FIREWORK_STAR)){
            CompoundTag exp = itemStack.getOrCreateTag().getCompound(EXPLOSION_SINGLE);
            wrap.putBoolean(IS_CUSTOM, false);
            wrap.put(PAYLOAD, exp);
        }
        else if (itemStack.is(ItemRegistry.FIREWORK_ORB.get())){
            CompoundTag exp = itemStack.getOrCreateTag();
            wrap.putBoolean(IS_CUSTOM, true);
            wrap.put(PAYLOAD, exp);
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
        wrap.put(EXPLOSIONS_LIST, list);
        return wrap;
    }

    /**
     * Converts the "Explosions" list from a vanilla Firework Rocket into Firework Missile "PayloadList" format.
     * @param expList A ListTag corresponding to the value of getList("Explosions", 10) from a Firework Rocket
     * @return A CompoundTag corresponding to the value of getList("PayloadList", 10) from a Firework Rocket
     */
    public static ListTag convertVanillaFireworkExplosions(ListTag expList){
        ListTag list = new ListTag();
        if (!expList.isEmpty() && expList.getElementType() == ListTag.TAG_COMPOUND){
            expList.forEach(exp -> {
                CompoundTag wrap = new CompoundTag();
                wrap.putBoolean(IS_CUSTOM, false);
                wrap.put(PAYLOAD, exp);
                list.add(wrap);
            });
        }
        return list;
    }

    public static CompoundTag convertVanillaFireworkRocket(ItemStack stack){
        CompoundTag wrap = new CompoundTag();
        CompoundTag fireworkCompound = stack.getOrCreateTag().getCompound(FIREWORKS);
        FlightProperties flight = FlightProperties.createFromVanilla(stack);
        DisplayProperties display = DisplayProperties.createDefault();
        ListTag exp = fireworkCompound.getList("Explosions",10);
        wrap = wrap.merge(flight.serialize()).merge(display.serialize());
        wrap.putInt("FuseDelay", 2);
        wrap.put("PayloadList", convertVanillaFireworkExplosions(exp));
        return wrap;
    }

}
