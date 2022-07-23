package org.mineplugin.locusazzurro.pyrotechnicraft.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.util.ColorUtil;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;

public class FireworkMissileModel<T extends FireworkMissileEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Pyrotechnicraft.MOD_ID, "firework_missile"), "main");
    protected final ModelPart bb_main;
    public FireworkMissileModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(0.0F, 3.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void renderFireworkMissile(T entity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        int baseColor = entity.getBaseColor();
        this.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay,
                ColorUtil.redF(baseColor), ColorUtil.greenF(baseColor), ColorUtil.blueF(baseColor), 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
