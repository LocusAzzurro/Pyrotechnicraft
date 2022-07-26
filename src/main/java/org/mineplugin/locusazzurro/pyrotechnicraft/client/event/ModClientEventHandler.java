package org.mineplugin.locusazzurro.pyrotechnicraft.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
import org.mineplugin.locusazzurro.pyrotechnicraft.client.color.*;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.*;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.ModelLayerLocations;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.*;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer.*;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.screen.*;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.EntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ParticleTypeRegistry;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventHandler {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerTypeRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), FireworkOrbCraftingTableScreen::new);
            MenuScreens.register(ContainerTypeRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get(), FireworkMissileCraftingTableScreen::new);
            MenuScreens.register(ContainerTypeRegistry.FIREWORK_LAUNCHER_STAND.get(), FireworkLauncherStandScreen::new);
        });
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e){
        registerEntityRenderer(EntityTypeRegistry.FIREWORK_MISSILE.get(), PatternedFireworkMissileRenderer::new);
        registerEntityRenderer(EntityTypeRegistry.FIREWORK_STARTER.get(), FireworkStarterRenderer::new);
        registerEntityRenderer(EntityTypeRegistry.FIRECRACKER.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterRenderers event) {
        Map<ModelLayerLocation, LayerDefinition> layers = ModelLayerLocations.createLayerDefinitions();
        layers.forEach((location, definition) -> ForgeHooksClient.registerLayerDefinition(location, () -> definition));
    }

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event){
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        particleEngine.register(ParticleTypeRegistry.FIREWORK_SPARK.get(), FireworkSparkParticle.FireworkSparkProvider::new);
        particleEngine.register(ParticleTypeRegistry.TRAIL_SPARK.get(), TrailSparkParticle.TrailSparkProvider::new);
    }
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event){
        ItemColors itemColors = event.getItemColors();
        itemColors.register(new FireworkOrbColorer(), ItemRegistry.FIREWORK_ORB.get());
        itemColors.register(new FireworkMissileColorer(), ItemRegistry.FIREWORK_MISSILE.get());
        itemColors.register(new FlickerStickColorer(), ItemRegistry.FLICKER_STICK.get());
    }
    private static <T extends Entity> void registerEntityRenderer(EntityType<T> type, EntityRendererProvider<T> renderer){
        EntityRenderers.register(type, renderer);
    }
}
