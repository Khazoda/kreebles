package com.khazoda.kreebles.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class KreeblesDataGeneration {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    PackOutput packOutput = event.getGenerator().getPackOutput();
    DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(packOutput, event.getLookupProvider());
    CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

    event.getGenerator().addProvider(
        event.includeServer(),
        new KreeblesAdvancementProvider(packOutput, lookupProvider, existingFileHelper)
    );
  }
}
