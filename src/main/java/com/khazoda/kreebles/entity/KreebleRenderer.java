package com.khazoda.kreebles.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static com.khazoda.kreebles.Constants.MOD_ID;
import static com.khazoda.kreebles.entity.KreebleModel.KREEBLE_MODEL_LAYER;

public class KreebleRenderer extends MobRenderer<KreebleEntity, EntityModel<KreebleEntity>> {
  private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MOD_ID,
      "textures/entity/kreeble.png");


  public KreebleRenderer(EntityRendererProvider.Context pContext) {
    super(pContext, new KreebleModel(pContext.bakeLayer(KREEBLE_MODEL_LAYER)), 0.25F);
  }

  @Nullable
  @Override
  public ResourceLocation getTextureLocation(KreebleEntity pEntity) {
    return texture;
  }
}
