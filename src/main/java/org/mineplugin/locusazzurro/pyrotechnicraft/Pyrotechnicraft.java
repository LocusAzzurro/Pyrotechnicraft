package org.mineplugin.locusazzurro.pyrotechnicraft;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.*;

import static org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class Pyrotechnicraft {

    public static final String MOD_ID = "pyrotechnicraft";
    public static final CreativeTabs.CreativeTab CREATIVE_TAB = new CreativeTabs.CreativeTab();

    public Pyrotechnicraft() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        BlockEntityTypeRegistry.BLOCK_ENTITIES.register(bus);
        EntityTypeRegistry.ENTITIES.register(bus);
        ContainerTypeRegistry.CONTAINERS.register(bus);
        ParticleTypeRegistry.PARTICLES.register(bus);

    }

}
