package com.khazoda.kreebles.datagen;

import com.khazoda.kreebles.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
  private static final RegistrySetBuilder REGISTRIES = new RegistrySetBuilder();
//      .add(Registries.DAMAGE_TYPE, KreeblesDamageTypes::bootstrap)
//      .add(Registries.BIOME, KreeblesBiomes::bootstrap)
//      .add(Registries.PROCESSOR_LIST, KreeblesStructures::bootstrapProcessors)
//      .add(Registries.STRUCTURE,KreeblesStructures::bootstrapStructures)
//      .add(Registries.STRUCTURE_SET,KreeblesStructures::bootstrapSets)
//      .add(Registries.TRIM_MATERIAL, KreeblesTrimMaterials::bootstrap)
//      .add(Registries.BANNER_PATTERN, KreeblesBannerPatterns::bootstrap)
//      .add(Registries.TEMPLATE_POOL,KreeblesStructures::bootstrapPools);

  public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, REGISTRIES, Set.of("minecraft", Constants.MOD_ID));
  }
}