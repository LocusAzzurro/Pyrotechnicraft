package org.mineplugin.locusazzurro.pyrotechnicraft.client.render;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.FireworkMissileModel;

import java.util.Map;

public final class ModelLayerLocations {

    public static final ModelLayerLocation FIREWORK_MISSILE = register("firework_missile");
    public static final ResourceLocation FIREWORK_MISSILE_BASE_LAYER = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/entity/firework_missile_base_layer.png");
    public static final ResourceLocation FIREWORK_MISSILE_PATTERN_LAYER = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/entity/firework_missile_pattern_layer.png");
    public static final ResourceLocation FIREWORK_MISSILE_FUSE_LAYER = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/entity/firework_missile_fuse_layer.png");

    private static ModelLayerLocation register(String pPath){
        return new ModelLayerLocation(new ResourceLocation(Pyrotechnicraft.MOD_ID, pPath), "main");
    }

    public static Map<ModelLayerLocation, LayerDefinition> createLayerDefinitions() {
        ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();
        builder.put(FIREWORK_MISSILE, FireworkMissileModel.createBodyLayer());
        return builder.build();
    }

}
