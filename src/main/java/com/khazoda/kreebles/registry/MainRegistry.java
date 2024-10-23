package com.khazoda.kreebles.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.khazoda.kreebles.Constants.MOD_ID;

public class MainRegistry {
  /* Deferred Registries */
  public static final DeferredRegister<CreativeModeTab> ITEM_GROUPS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

  public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
  public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
  public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
      .alwaysEdible().nutrition(1).saturationModifier(2f).build()));


}
