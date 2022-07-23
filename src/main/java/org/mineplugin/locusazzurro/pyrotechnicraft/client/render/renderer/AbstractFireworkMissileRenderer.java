package org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.FireworkMissileModel;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

import java.util.List;

public abstract class AbstractFireworkMissileRenderer<T extends FireworkMissileEntity, M extends FireworkMissileModel<T>>
        extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    protected final List<RenderLayer<T, M>> layers = Lists.newArrayList();
    private final M model;

    public AbstractFireworkMissileRenderer(EntityRendererProvider.Context context, M model) {
        super(context);
        this.model = model;
    }

    @Override
    @NotNull
    public abstract ResourceLocation getTextureLocation(T pEntity);

    public final boolean addLayer(RenderLayer<T, M> pLayer) {
        return this.layers.add(pLayer);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn){
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pushPose();
        matrixStackIn.scale(0.5f,0.5f, 0.5f);
        matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(entityIn.getYRot()));
        matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(entityIn.getXRot() - 90f));
        VertexConsumer vertexBuilder = bufferIn.getBuffer(model.renderType(getTextureLocation(entityIn)));
        this.model.renderFireworkMissile(entityIn, matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
        for(RenderLayer<T, M> renderLayer : this.layers) {
            renderLayer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, 0, 0, partialTicks, entityIn.tickCount, entityIn.getYRot(), entityIn.getXRot());
        }
        matrixStackIn.popPose();
    }

    @Override
    public boolean shouldRender(FireworkMissileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public M getModel() {
        return model;
    }

}
