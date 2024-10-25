package com.khazoda.kreebles;

import com.khazoda.kreebles.registry.ItemGroupRegistry;
import com.khazoda.kreebles.registry.KreebleSpawning;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static com.khazoda.kreebles.Constants.MOD_ID;
import static com.khazoda.kreebles.registry.MainRegistry.*;

@Mod(MOD_ID)
public class KreeblesMod {

  public KreeblesMod(IEventBus modEventBus, ModContainer modContainer) {
    modEventBus.addListener(this::setupCommon);
    BLOCKS.register(modEventBus);
    ITEMS.register(modEventBus);
    ITEM_GROUPS.register(modEventBus);
    ENTITIES.register(modEventBus);
    SOUNDS.register(modEventBus);

    ItemGroupRegistry.init();
    modEventBus.addListener(KreebleSpawning::registerSpawnPlacements);
    modEventBus.addListener(KreebleSpawning::registerEntityAttributes);

    NeoForge.EVENT_BUS.register(this);
    modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  private void setupCommon(final FMLCommonSetupEvent event) {
  }

  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
  }
}
