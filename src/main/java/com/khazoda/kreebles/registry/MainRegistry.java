package com.khazoda.kreebles.registry;

import com.khazoda.kreebles.block.KreebleSpawningBlock;
import com.khazoda.kreebles.block.KreebleSpawningBlockWithAxis;
import com.khazoda.kreebles.entity.KreebleEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.khazoda.kreebles.Constants.MOD_ID;

public class MainRegistry {
  /* Deferred Registries */
  public static final DeferredRegister<CreativeModeTab> ITEM_GROUPS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MOD_ID);
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
  public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MOD_ID);

  public static final DeferredHolder<EntityType<?>, EntityType<KreebleEntity>> KREEBLE = ENTITIES.register("kreeble", () -> EntityType.Builder.of(KreebleEntity::new, MobCategory.CREATURE).sized(0.5F, 0.8F).clientTrackingRange(10).build(MOD_ID + "kreeble"));

  public static final DeferredHolder<Block, KreebleSpawningBlock> KREEBLE_STONE = BLOCKS.register("kreeblestone", () -> new KreebleSpawningBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.6F, 6.0F)));
  public static final DeferredHolder<Block, KreebleSpawningBlock> KREEBLE_DEEPSLATE = BLOCKS.register("kreebledeepslate", () -> new KreebleSpawningBlockWithAxis(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.1F, 6.0F).sound(SoundType.DEEPSLATE)));

  public static final DeferredItem<BlockItem> KREEBLE_STONE_ITEM = ITEMS.registerSimpleBlockItem("kreeblestone", KREEBLE_STONE);
  public static final DeferredItem<BlockItem> KREEBLE_DEEPSLATE_ITEM = ITEMS.registerSimpleBlockItem("kreebledeepslate", KREEBLE_DEEPSLATE);

//  public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2f).build()));
}
