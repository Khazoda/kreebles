package com.khazoda.kreebles.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class KreeblesAdvancementProvider extends AdvancementProvider {
  public KreeblesAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
    super(output, registries, existingFileHelper, List.of(new KreeblesAdvancementGenerator()));
  }
}
