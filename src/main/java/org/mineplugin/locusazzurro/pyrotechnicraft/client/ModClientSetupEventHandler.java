package org.mineplugin.locusazzurro.pyrotechnicraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.FireworkSparkParticle;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.FireworkMissileModel;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer.PatternedFireworkMissileRenderer;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ParticleTypeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientSetupEventHandler {


    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e){
        registerEntityRenderer(EntityTypeRegistry.FIREWORK_MISSILE.get(), PatternedFireworkMissileRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterRenderers event) {
        ForgeHooksClient.registerLayerDefinition(FireworkMissileModel.LAYER_LOCATION, FireworkMissileModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event){
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        particleEngine.register(ParticleTypeRegistry.FIREWORK_SPARK.get(), FireworkSparkParticle.FireworkSparkProvider::new);
    }

    private static <T extends Entity> void registerEntityRenderer(EntityType<T> type, EntityRendererProvider<T> renderer){
        EntityRenderers.register(type, renderer);
    }

}
