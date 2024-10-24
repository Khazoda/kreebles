package com.khazoda.kreebles;

import com.khazoda.kreebles.entity.KreebleModel;
import com.khazoda.kreebles.entity.KreebleRenderer;
import com.khazoda.kreebles.registry.MainRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.khazoda.kreebles.Constants.MOD_ID;
import static com.khazoda.kreebles.entity.KreebleModel.KREEBLE_MODEL_LAYER;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class KreeblesClientEvents {

  @SubscribeEvent
  public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(MainRegistry.KREEBLE.get(), KreebleRenderer::new);
  }

  @SubscribeEvent
  public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(KREEBLE_MODEL_LAYER, KreebleModel::createBodyLayer);
  }
}
