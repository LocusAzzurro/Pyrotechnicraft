package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class SoundEventRegistry {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<SoundEvent> FIREWORK_MISSILE_LAUNCH = register("item.firework_launcher.launch");
    public static final RegistryObject<SoundEvent> FIREWORK_EXPLODE = register("entity.firework_missile.explode");
    public static final RegistryObject<SoundEvent> FIREWORK_STAND_LAUNCH = register("block.firework_launcher_stand.launch");
    public static final RegistryObject<SoundEvent> FLICKER_STICK_USE = register("item.flicker_stick.use");
    public static final RegistryObject<SoundEvent> HOMING_ARRAY_TARGET_LOCKED = register("item.homing_array.lock");
    public static final RegistryObject<SoundEvent> HOMING_ARRAY_SCRIPT_LOADED = register("item.homing_array.load_script");
    public static final RegistryObject<SoundEvent> FIRECRACKER_THROW = register("item.firecracker.throw");

    private static RegistryObject<SoundEvent> register(String name){
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID, name)));
    }

}
