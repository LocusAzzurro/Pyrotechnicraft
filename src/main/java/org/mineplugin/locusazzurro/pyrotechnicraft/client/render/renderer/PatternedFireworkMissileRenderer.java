package org.mineplugin.locusazzurro.pyrotechnicraft.client.render.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.layer.FireworkMissilePatternLayer;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model.FireworkMissileModel;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

public class PatternedFireworkMissileRenderer extends FireworkMissileRenderer<FireworkMissileEntity, FireworkMissileModel<FireworkMissileEntity>>{

    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(Pyrotechnicraft.MOD_ID, "textures/entity/firework_missile.png");
    public PatternedFireworkMissileRenderer(EntityRendererProvider.Context context) {
        super(context, new FireworkMissileModel<>(context.bakeLayer(FireworkMissileModel.LAYER_LOCATION)));
        this.addLayer(new FireworkMissilePatternLayer<>(this));
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(FireworkMissileEntity pEntity) {
        return BASE_TEXTURE;
    }

}
