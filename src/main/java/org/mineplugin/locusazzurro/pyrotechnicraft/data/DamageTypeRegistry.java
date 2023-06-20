package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class DamageTypeRegistry {

        public static final ResourceKey<DamageType> FIREWORK = register("firework");

        private static ResourceKey<DamageType> register(String name)
        {
            return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Pyrotechnicraft.MOD_ID, name));
        }
    }
