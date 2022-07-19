package org.mineplugin.locusazzurro.pyrotechnicraft.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.color.FireworkOrbColorer;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.FireworkSparkParticle;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.FireworkMissileModel;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer.SimpleFireworkMissileRenderer;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.screen.FireworkMissileCraftingTableScreen;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.screen.FireworkOrbCraftingTableScreen;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ParticleTypeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventHandler {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerTypeRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), FireworkOrbCraftingTableScreen::new);
            MenuScreens.register(ContainerTypeRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get(), FireworkMissileCraftingTableScreen::new);
        });
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e){
        registerEntityRenderer(EntityTypeRegistry.FIREWORK_MISSILE.get(), SimpleFireworkMissileRenderer::new);
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
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event){
        ItemColors itemColors = event.getItemColors();
        itemColors.register(new FireworkOrbColorer(), ItemRegistry.FIREWORK_ORB.get());
    }
    private static <T extends Entity> void registerEntityRenderer(EntityType<T> type, EntityRendererProvider<T> renderer){
        EntityRenderers.register(type, renderer);
    }
}
