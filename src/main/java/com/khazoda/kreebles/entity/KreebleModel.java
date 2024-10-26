package com.khazoda.kreebles.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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
  private final ModelPart leg_br;
  private final ModelPart pivot_br;
  private final ModelPart foot_br;
  private final ModelPart leg_bl;
  private final ModelPart pivot_bl;
  private final ModelPart foot_bl;
  private final ModelPart leg_fl;
  private final ModelPart pivot_fl;
  private final ModelPart foot_fl;
  private final ModelPart leg_fr;
  private final ModelPart pivot_fr;
  private final ModelPart foot_fr;
  private final ModelPart body_lower;
  private final ModelPart body_upper;
  private final ModelPart head;
  private final ModelPart eyebrow_left;
  private final ModelPart eyebrow_right;
  private final ModelPart antennae;

  public KreebleModel(ModelPart root) {
    this.root = root;
    this.body = root.getChild("body");
    this.leg_br = this.body.getChild("leg_br");
    this.pivot_br = this.leg_br.getChild("pivot_br");
    this.foot_br = this.pivot_br.getChild("foot_br");
    this.leg_bl = this.body.getChild("leg_bl");
    this.pivot_bl = this.leg_bl.getChild("pivot_bl");
    this.foot_bl = this.pivot_bl.getChild("foot_bl");
    this.leg_fl = this.body.getChild("leg_fl");
    this.pivot_fl = this.leg_fl.getChild("pivot_fl");
    this.foot_fl = this.pivot_fl.getChild("foot_fl");
    this.leg_fr = this.body.getChild("leg_fr");
    this.pivot_fr = this.leg_fr.getChild("pivot_fr");
    this.foot_fr = this.pivot_fr.getChild("foot_fr");
    this.body_lower = this.body.getChild("body_lower");
    this.body_upper = this.body_lower.getChild("body_upper");
    this.head = this.body_lower.getChild("head");
    this.eyebrow_left = this.head.getChild("eyebrow_left");
    this.eyebrow_right = this.head.getChild("eyebrow_right");
    this.antennae = this.head.getChild("antennae");
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.5F, 21.5F, 0.5F));

    PartDefinition leg_br = body.addOrReplaceChild("leg_br", CubeListBuilder.create(), PartPose.offset(-2.25F, -0.5F, 1.25F));

    PartDefinition pivot_br = leg_br.addOrReplaceChild("pivot_br", CubeListBuilder.create().texOffs(8, 13).addBox(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition foot_br = pivot_br.addOrReplaceChild("foot_br", CubeListBuilder.create().texOffs(8, 10).addBox(-0.5F, 0.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition leg_bl = body.addOrReplaceChild("leg_bl", CubeListBuilder.create(), PartPose.offset(1.25F, -0.5F, 1.25F));

    PartDefinition pivot_bl = leg_bl.addOrReplaceChild("pivot_bl", CubeListBuilder.create().texOffs(8, 13).mirror().addBox(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition foot_bl = pivot_bl.addOrReplaceChild("foot_bl", CubeListBuilder.create().texOffs(8, 10).mirror().addBox(-0.5F, 0.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition leg_fl = body.addOrReplaceChild("leg_fl", CubeListBuilder.create(), PartPose.offset(1.25F, -0.5F, -2.25F));

    PartDefinition pivot_fl = leg_fl.addOrReplaceChild("pivot_fl", CubeListBuilder.create().texOffs(8, 13).mirror().addBox(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition foot_fl = pivot_fl.addOrReplaceChild("foot_fl", CubeListBuilder.create().texOffs(8, 10).mirror().addBox(-0.5F, 0.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition leg_fr = body.addOrReplaceChild("leg_fr", CubeListBuilder.create(), PartPose.offset(-2.25F, -0.5F, -2.25F));

    PartDefinition pivot_fr = leg_fr.addOrReplaceChild("pivot_fr", CubeListBuilder.create().texOffs(8, 13).addBox(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition foot_fr = pivot_fr.addOrReplaceChild("foot_fr", CubeListBuilder.create().texOffs(8, 10).addBox(-0.5F, 0.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

    PartDefinition body_lower = body.addOrReplaceChild("body_lower", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.5F, -0.5F));

    PartDefinition cube_r1 = body_lower.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

    PartDefinition body_upper = body_lower.addOrReplaceChild("body_upper", CubeListBuilder.create().texOffs(0, 6).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 0.0F));

    PartDefinition head = body_lower.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, -2.0765F, -0.5952F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 1).addBox(-1.0F, -1.0765F, -0.7452F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.9235F, -0.4048F));

    PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-2, 16).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -2.0765F, 0.4048F, 0.0F, 0.0F, 1.0472F));

    PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-2, 16).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0765F, 1.4048F, -1.5708F, -0.4363F, 1.5708F));

    PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-2, 16).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0765F, 0.4048F, 0.0F, 0.0F, -1.0472F));

    PartDefinition eyebrow_left = head.addOrReplaceChild("eyebrow_left", CubeListBuilder.create(), PartPose.offset(0.6376F, -1.4301F, -0.7452F));

    PartDefinition eyebrow_left_r1 = eyebrow_left.addOrReplaceChild("eyebrow_left_r1", CubeListBuilder.create().texOffs(1, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.6124F, 0.3536F, 1.0F, 0.0F, 0.0F, -0.2618F));

    PartDefinition eyebrow_right = head.addOrReplaceChild("eyebrow_right", CubeListBuilder.create(), PartPose.offset(-0.6376F, -1.4301F, -0.7452F));

    PartDefinition eyebrow_right_r1 = eyebrow_right.addOrReplaceChild("eyebrow_right_r1", CubeListBuilder.create().texOffs(1, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6124F, 0.3536F, 1.0F, 0.0F, 0.0F, 0.2618F));

    PartDefinition antennae = head.addOrReplaceChild("antennae", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5765F, -0.5952F));

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
    this.animate(pEntity.restAnimationState, KreebleAnimations.KREEBLE_IDLE, pAgeInTicks, 1.0F);
    this.animate(pEntity.walkAnimationState, KreebleAnimations.KREEBLE_WALK, pAgeInTicks, 1.0F);
    this.animate(pEntity.talismanFrozenAnimationState, KreebleAnimations.KREEBLE_FROZEN, pAgeInTicks, 1.0F);
  }

  public boolean isAggressive(KreebleEntity pEntity) {
    return pEntity.isAggressive();
  }

}
