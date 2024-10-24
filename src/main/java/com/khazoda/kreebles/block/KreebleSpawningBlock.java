package com.khazoda.kreebles.block;

import com.khazoda.kreebles.entity.KreebleEntity;
import com.khazoda.kreebles.registry.MainRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class KreebleSpawningBlock extends Block {
  public static final MapCodec<KreebleSpawningBlock> CODEC = simpleCodec(KreebleSpawningBlock::new);

  public KreebleSpawningBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
    super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
    if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !EnchantmentHelper.hasTag(pStack, EnchantmentTags.PREVENTS_INFESTED_SPAWNS)) {
      this.spawnDeviousLittleKreeble(pLevel, pPos);
    }
  }

  private void spawnDeviousLittleKreeble(ServerLevel pLevel, BlockPos pPos) {
    KreebleEntity mischievousKreeble = MainRegistry.KREEBLE.get().create(pLevel);
    if (mischievousKreeble != null) {
      mischievousKreeble.moveTo((double)pPos.getX() + 0.5, pPos.getY(), (double)pPos.getZ() + 0.5, 0.0F, 0.0F);
      pLevel.addFreshEntity(mischievousKreeble);
      mischievousKreeble.spawnAnim();
    }
  }
}
