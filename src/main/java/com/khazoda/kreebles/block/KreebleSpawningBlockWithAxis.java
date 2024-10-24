package com.khazoda.kreebles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class KreebleSpawningBlockWithAxis extends KreebleSpawningBlock{
  public static final MapCodec<KreebleSpawningBlockWithAxis> CODEC = simpleCodec(KreebleSpawningBlockWithAxis::new);
  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

  public KreebleSpawningBlockWithAxis(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
  }

  @Override
  protected BlockState rotate(BlockState pState, Rotation pRot) {
    return rotatePillar(pState, pRot);
  }

  public static BlockState rotatePillar(BlockState pState, Rotation pRotation) {
    switch (pRotation) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
        switch (pState.getValue(AXIS)) {
          case X:
            return pState.setValue(AXIS, Direction.Axis.Z);
          case Z:
            return pState.setValue(AXIS, Direction.Axis.X);
          default:
            return pState;
        }
      default:
        return pState;
    }
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(AXIS);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    return this.defaultBlockState().setValue(AXIS, pContext.getClickedFace().getAxis());
  }
}
