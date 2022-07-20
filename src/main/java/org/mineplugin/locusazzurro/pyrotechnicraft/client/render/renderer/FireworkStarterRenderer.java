package org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkStarter;

public class FireworkStarterRenderer<T extends FireworkStarter> extends EntityRenderer<T> {

    public FireworkStarterRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return null;
    }
}
