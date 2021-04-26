package net.minecraft.block;

import java.util.Random;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class ChorusPlantBlock extends SixWayBlock {
   protected ChorusPlantBlock(AbstractBlock.Properties p_i48428_1_) {
      super(0.3125F, p_i48428_1_);
      this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false)));
   }

   public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
      return this.getStateForPlacement(p_196258_1_.getLevel(), p_196258_1_.getClickedPos());
   }

   public BlockState getStateForPlacement(IBlockReader p_196497_1_, BlockPos p_196497_2_) {
      Block block = p_196497_1_.getBlockState(p_196497_2_.below()).getBlock();
      Block block1 = p_196497_1_.getBlockState(p_196497_2_.above()).getBlock();
      Block block2 = p_196497_1_.getBlockState(p_196497_2_.north()).getBlock();
      Block block3 = p_196497_1_.getBlockState(p_196497_2_.east()).getBlock();
      Block block4 = p_196497_1_.getBlockState(p_196497_2_.south()).getBlock();
      Block block5 = p_196497_1_.getBlockState(p_196497_2_.west()).getBlock();
      return this.defaultBlockState().setValue(DOWN, Boolean.valueOf(block == this || block == Blocks.CHORUS_FLOWER || block == Blocks.END_STONE)).setValue(UP, Boolean.valueOf(block1 == this || block1 == Blocks.CHORUS_FLOWER)).setValue(NORTH, Boolean.valueOf(block2 == this || block2 == Blocks.CHORUS_FLOWER)).setValue(EAST, Boolean.valueOf(block3 == this || block3 == Blocks.CHORUS_FLOWER)).setValue(SOUTH, Boolean.valueOf(block4 == this || block4 == Blocks.CHORUS_FLOWER)).setValue(WEST, Boolean.valueOf(block5 == this || block5 == Blocks.CHORUS_FLOWER));
   }

   public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
      if (!p_196271_1_.canSurvive(p_196271_4_, p_196271_5_)) {
         p_196271_4_.getBlockTicks().scheduleTick(p_196271_5_, this, 1);
         return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
      } else {
         boolean flag = p_196271_3_.getBlock() == this || p_196271_3_.is(Blocks.CHORUS_FLOWER) || p_196271_2_ == Direction.DOWN && p_196271_3_.is(Blocks.END_STONE);
         return p_196271_1_.setValue(PROPERTY_BY_DIRECTION.get(p_196271_2_), Boolean.valueOf(flag));
      }
   }

   public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
      if (!p_225534_1_.canSurvive(p_225534_2_, p_225534_3_)) {
         p_225534_2_.destroyBlock(p_225534_3_, true);
      }

   }

   public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
      BlockState blockstate = p_196260_2_.getBlockState(p_196260_3_.below());
      boolean flag = !p_196260_2_.getBlockState(p_196260_3_.above()).isAir() && !blockstate.isAir();

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockPos blockpos = p_196260_3_.relative(direction);
         Block block = p_196260_2_.getBlockState(blockpos).getBlock();
         if (block == this) {
            if (flag) {
               return false;
            }

            Block block1 = p_196260_2_.getBlockState(blockpos.below()).getBlock();
            if (block1 == this || block1 == Blocks.END_STONE) {
               return true;
            }
         }
      }

      Block block2 = blockstate.getBlock();
      return block2 == this || block2 == Blocks.END_STONE;
   }

   protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
      p_206840_1_.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
   }

   public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
      return false;
   }
}