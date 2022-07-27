package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class SoundEventRegistry {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<SoundEvent> FIREWORK_MISSILE_LAUNCH = SOUNDS.register("item.firework_launcher.launch",
            () -> new SoundEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID,"item.firework_launcher.launch")));
    public static final RegistryObject<SoundEvent> FIREWORK_EXPLODE = SOUNDS.register("entity.firework_missile.explode",
            () -> new SoundEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID,"entity.firework_missile.explode")));
    public static final RegistryObject<SoundEvent> FIREWORK_STAND_LAUNCH = SOUNDS.register("block.firework_launcher_stand.launch",
            () -> new SoundEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID,"block.firework_launcher_stand.launch")));
    public static final RegistryObject<SoundEvent> FLICKER_STICK_USE = SOUNDS.register("item.flicker_stick.use",
            () -> new SoundEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID,"item.flicker_stick.use")));
    public static final RegistryObject<SoundEvent> HOMING_ARRAY_TARGET_LOCKED = SOUNDS.register("item.homing_array.lock",
            () -> new SoundEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID,"item.homing_array.lock")));
    public static final RegistryObject<SoundEvent> HOMING_ARRAY_SCRIPT_LOADED = SOUNDS.register("item.homing_array.load_script",
            () -> new SoundEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID,"item.homing_array.load_script")));
    public static final RegistryObject<SoundEvent> FIRECRACKER_THROW = SOUNDS.register("item.firecracker.throw",
            () -> new SoundEvent(new ResourceLocation(Pyrotechnicraft.MOD_ID,"item.firecracker.throw")));

}
