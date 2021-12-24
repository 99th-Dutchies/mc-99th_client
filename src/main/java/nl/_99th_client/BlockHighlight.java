package nl._99th_client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

public class BlockHighlight {
    public static void highlightBlock(BlockRayTraceResult blockRayTraceResult, ClientWorld world, RenderTypeBuffers renderTypeTextures, MatrixStack matrixStackIn, double d0, double d1, double d2) {
        BlockPos blockpos = blockRayTraceResult.getPos();

        double d3 = (double)blockpos.getX() - d0;
        double d4 = (double)blockpos.getY() - d1;
        double d5 = (double)blockpos.getZ() - d2;

        if (!(d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D))
        {
            matrixStackIn.push();
            matrixStackIn.translate((double)blockpos.getX() - d0, (double)blockpos.getY() - d1, (double)blockpos.getZ() - d2);
            MatrixStack.Entry matrixstack$entry1 = matrixStackIn.getLast();
            IVertexBuilder ivertexbuilder1 = new MatrixApplyingVertexBuilder(renderTypeTextures.getCrumblingBufferSource().getBuffer(ModelBakery.HIGHLIGHT_OVERLAY_TYPE), matrixstack$entry1.getMatrix(), matrixstack$entry1.getNormal());
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlockDamage(world.getBlockState(blockpos), blockpos, world, matrixStackIn, ivertexbuilder1);
            matrixStackIn.pop();
        }
    }

    public static void highlightTileEntity(MatrixStack matrixStackIn, RenderTypeBuffers renderTypeTextures, IRenderTypeBuffer irendertypebuffer1, IRenderTypeBuffer.Impl irendertypebuffer$impl) {
        MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
        IVertexBuilder ivertexbuilder = new MatrixApplyingVertexBuilder(renderTypeTextures.getCrumblingBufferSource().getBuffer(ModelBakery.HIGHLIGHT_OVERLAY_TYPE), matrixstack$entry.getMatrix(), matrixstack$entry.getNormal());
        irendertypebuffer1 = (p_lambda$updateCameraAndRender$1_2_) ->
        {
            IVertexBuilder ivertexbuilder3 = irendertypebuffer$impl.getBuffer(p_lambda$updateCameraAndRender$1_2_);
            return p_lambda$updateCameraAndRender$1_2_.isUseDelegate() ? VertexBuilderUtils.newDelegate(ivertexbuilder, ivertexbuilder3) : ivertexbuilder3;
        };
    }
}
