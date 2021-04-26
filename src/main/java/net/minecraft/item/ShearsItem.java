package net.minecraft.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearsItem extends Item {
   public ShearsItem(Item.Properties p_i48471_1_) {
      super(p_i48471_1_);
   }

   public boolean mineBlock(ItemStack p_179218_1_, World p_179218_2_, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
      if (!p_179218_2_.isClientSide && !p_179218_3_.getBlock().is(BlockTags.FIRE)) {
         p_179218_1_.hurtAndBreak(1, p_179218_5_, (p_220036_0_) -> {
            p_220036_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
         });
      }

      return !p_179218_3_.is(BlockTags.LEAVES) && !p_179218_3_.is(Blocks.COBWEB) && !p_179218_3_.is(Blocks.GRASS) && !p_179218_3_.is(Blocks.FERN) && !p_179218_3_.is(Blocks.DEAD_BUSH) && !p_179218_3_.is(Blocks.VINE) && !p_179218_3_.is(Blocks.TRIPWIRE) && !p_179218_3_.is(BlockTags.WOOL) ? super.mineBlock(p_179218_1_, p_179218_2_, p_179218_3_, p_179218_4_, p_179218_5_) : true;
   }

   public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
      return p_150897_1_.is(Blocks.COBWEB) || p_150897_1_.is(Blocks.REDSTONE_WIRE) || p_150897_1_.is(Blocks.TRIPWIRE);
   }

   public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
      if (!p_150893_2_.is(Blocks.COBWEB) && !p_150893_2_.is(BlockTags.LEAVES)) {
         return p_150893_2_.is(BlockTags.WOOL) ? 5.0F : super.getDestroySpeed(p_150893_1_, p_150893_2_);
      } else {
         return 15.0F;
      }
   }
}