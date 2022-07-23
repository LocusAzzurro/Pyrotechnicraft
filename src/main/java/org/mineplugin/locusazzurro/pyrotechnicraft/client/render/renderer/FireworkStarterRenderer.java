package org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkStarter;

public class FireworkStarterRenderer<T extends FireworkStarter> extends EntityRenderer<T> {

    public FireworkStarterRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return null;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
    }
}
