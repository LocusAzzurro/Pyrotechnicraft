package org.mineplugin.locusazzurro.pyrotechnicraft.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.FireworkMissileModel;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

public class FireworkMissileFuseLayer<T extends FireworkMissileEntity, M extends FireworkMissileModel<T>> extends RenderLayer<T, M> {

    public static final ResourceLocation FUSE_LAYER = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/entity/firework_missile_fuse_layer.png");

    public FireworkMissileFuseLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(pEntity)));
        this.getParentModel().renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getTextureLocation(T pEntity) {
        return FUSE_LAYER;
    }
}
