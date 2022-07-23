package org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.ModelLayerLocations;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.layer.FireworkMissileFuseLayer;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.layer.FireworkMissilePatternLayer;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.FireworkMissileModel;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

public class PatternedFireworkMissileRenderer extends AbstractFireworkMissileRenderer<FireworkMissileEntity, FireworkMissileModel<FireworkMissileEntity>> {

    public PatternedFireworkMissileRenderer(EntityRendererProvider.Context context) {
        super(context, new FireworkMissileModel<>(context.bakeLayer(ModelLayerLocations.FIREWORK_MISSILE)));
        this.addLayer(new FireworkMissilePatternLayer<>(this));
        this.addLayer(new FireworkMissileFuseLayer<>(this));
    }

    @Override
    public void render(FireworkMissileEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(FireworkMissileEntity pEntity) {
        return ModelLayerLocations.FIREWORK_MISSILE_BASE_LAYER;
    }

}
