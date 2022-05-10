package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.FireworkLighter;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.FlickerStick;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<Item> FIREWORK_LIGHTER = ITEMS.register("firework_lighter", FireworkLighter::new);
    public static final RegistryObject<Item> FLICKER_STICK = ITEMS.register("flicker_stick", FlickerStick::new);

}
