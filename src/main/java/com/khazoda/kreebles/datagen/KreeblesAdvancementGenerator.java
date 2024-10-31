package com.khazoda.kreebles.datagen;

import com.khazoda.kreebles.Constants;
import com.khazoda.kreebles.registry.MainRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class KreeblesAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

  @SuppressWarnings("unused")
  @Override
  public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {

    Advancement gotDastardlyTalismanAdvancement = Advancement.Builder.advancement()
        .addCriterion("got_dastardly_talisman", InventoryChangeTrigger.TriggerInstance.hasItems(MainRegistry.DASTARDLY_TALISMAN))
        .rewards(AdvancementRewards.Builder
            .recipe(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dastardly_talisman_smelting"))
            .addRecipe(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "kreebledeepslate"))
            .addRecipe(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "kreeblestone")))
        .save(saver, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "got_dastardly_talisman"), existingFileHelper).value();
  }
}
