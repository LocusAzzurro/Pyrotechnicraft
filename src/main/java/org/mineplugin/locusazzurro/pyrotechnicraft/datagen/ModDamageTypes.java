package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.DamageTypeRegistry;

public class ModDamageTypes {
    protected static void bootstrap(BootstapContext<DamageType> context)
    {
        context.register(DamageTypeRegistry.FIREWORK, new DamageType("firework", 0.1F));
    }
}
