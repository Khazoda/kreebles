package com.khazoda.kreebles.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.khazoda.kreebles.registry.MainRegistry.*;

public class ItemGroupRegistry {
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KREEBLES_TAB = ITEM_GROUPS.register("example_tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.kreebles"))
      .withTabsBefore(CreativeModeTabs.COMBAT)
      .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
      .displayItems((parameters, output) -> {
        output.accept(EXAMPLE_ITEM.get());
      }).build());

  public static void init() {

  }
}
