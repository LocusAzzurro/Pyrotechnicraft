package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.FireworkSparkParticle;

public class ParticleTypeRegistry {

    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Pyrotechnicraft.MOD_ID);

    public static RegistryObject<FireworkSparkParticle.FireworkSparkParticleType> FIREWORK_SPARK =PARTICLES.register("firework_spark", FireworkSparkParticle.FireworkSparkParticleType::new);

}
