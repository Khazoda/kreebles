package com.khazoda.kreebles.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.khazoda.kreebles.Constants.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class KreebleModel extends HierarchicalModel<KreebleEntity> {

  // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
  public static final ModelLayerLocation KREEBLE_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "kreeble"), "main");

  private final ModelPart root;
  private final ModelPart body;

  public KreebleModel(ModelPart root) {
    this.root = root;
    this.body = root.getChild("body");
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();
    PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 21.5F, 0.5F));
    return LayerDefinition.create(meshdefinition, 32, 32);
  }

  @Override
  public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
    body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
  }

  @Override
  public @NotNull ModelPart root() {
    return this.root;
  }

  @Override
  public void setupAnim(KreebleEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    this.root().getAllParts().forEach(ModelPart::resetPose);
    this.animate(pEntity.spawnAnimationState, KreebleAnimations.KREEBLE_EMERGE, pAgeInTicks, 1.0F);
    this.animate(pEntity.restAnimationState, KreebleAnimations.KREEBLE_EMERGE, pAgeInTicks, 1.0F);
  }
}
