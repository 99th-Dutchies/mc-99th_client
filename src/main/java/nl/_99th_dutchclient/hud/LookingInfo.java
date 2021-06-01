package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class LookingInfo
{
    public static void render(Minecraft mc, MatrixStack ms, float pt) {
        // Draw reach
        renderReach(mc, ms, pt);
    }

    private static void renderReach(Minecraft mc, MatrixStack ms, float pt) {
        Entity entity = mc.getRenderViewEntity();

        if (entity != null && mc.world != null) {
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
    }
}
