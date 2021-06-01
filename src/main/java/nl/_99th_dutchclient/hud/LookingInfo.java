package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;

public class LookingInfo
{
    public static void render(Minecraft mc, MatrixStack ms, float pt) {
        Entity entity = mc.getRenderViewEntity();

        if (entity != null && mc.world != null) {
            // Draw reach
            renderReach(mc, ms, pt, entity);

            // Draw block
            renderBlock(mc, ms, pt, entity);
        }
    }

    private static void renderReach(Minecraft mc, MatrixStack ms, float pt, Entity entity) {
        double d = 10.0D * 10.0D;
        Vector3d vector3d = entity.getEyePosition(pt);
        Vector3d vector3d1 = entity.getLook(1.0F);
        Vector3d vector3d2 = vector3d.add(vector3d1.x * d, vector3d1.y * d, vector3d1.z * d);
        float f = 1.0F;
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vector3d1.scale(d)).grow(1.0D, 1.0D, 1.0D);
        EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, vector3d, vector3d2, axisalignedbb, (p_lambda$getMouseOver$0_0_) ->
        {
            return !p_lambda$getMouseOver$0_0_.isSpectator() && p_lambda$getMouseOver$0_0_.canBeCollidedWith();
        }, d);

        if (entityraytraceresult != null && mc.player != null) {
            Entity ent = entityraytraceresult.getEntity();
            String reach = ent.getDisplayName().getString() + " (" + (Math.round(ent.getDistance(mc.player) * 10.0F) / 10.0F) + " blocks away)";
            mc.fontRenderer.drawStringWithShadow(ms, reach, 1, 126, -1);
        }
    }

    private static void renderBlock(Minecraft mc, MatrixStack ms, float pt, Entity entity) {
        RayTraceResult rayTraceBlock = entity.pick(20.0D, 0.0F, false);

        if (rayTraceBlock.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockRayTraceResult)rayTraceBlock).getPos();
            BlockState blockstate = mc.world.getBlockState(blockpos);
            Block block = blockstate.getBlock();

            float ds = mc.player.getDigSpeed(blockstate);
            float hardness = blockstate.getBlockHardness(mc.world, blockpos);
            boolean canHarvest = mc.player.func_234569_d_(blockstate);

            float damage = (ds / hardness) / (canHarvest ? 30 : 100);

            if(damage > 1) {
                mc.fontRenderer.drawStringWithShadow(ms, block.getTranslatedName().getString(), 1, 136, -1);
            } else {
                double ticks = Math.ceil(1.0F / damage);
                double seconds = ticks / 20;

                mc.fontRenderer.drawStringWithShadow(ms, block.getTranslatedName().getString() + " (" + seconds + "s)", 1, 136, -1);
            }
        }
    }
}
