package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.netty.util.internal.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.LightType;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.Either;
import nl._99th_client.settings.HealthIndicator;
import nl._99th_client.util.MCStringUtils;

public abstract class EntityRenderer<T extends Entity> implements net.optifine.entity.model.IEntityRenderer
{
    protected final EntityRendererManager renderManager;
    public float shadowSize;
    protected float shadowOpaque = 1.0F;
    private EntityType entityType = null;
    private ResourceLocation locationTextureCustom = null;

    protected EntityRenderer(EntityRendererManager renderManager)
    {
        this.renderManager = renderManager;
    }

    public final int getPackedLight(T entityIn, float partialTicks)
    {
        BlockPos blockpos = new BlockPos(entityIn.func_241842_k(partialTicks));
        return LightTexture.packLight(this.getBlockLight(entityIn, blockpos), this.func_239381_b_(entityIn, blockpos));
    }

    protected int func_239381_b_(T p_239381_1_, BlockPos p_239381_2_)
    {
        return p_239381_1_.world.getLightFor(LightType.SKY, p_239381_2_);
    }

    protected int getBlockLight(T entityIn, BlockPos partialTicks)
    {
        return entityIn.isBurning() ? 15 : entityIn.world.getLightFor(LightType.BLOCK, partialTicks);
    }

    public boolean shouldRender(T livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ)
    {
        if (!livingEntityIn.isInRangeToRender3d(camX, camY, camZ))
        {
            return false;
        }
        else if (livingEntityIn.ignoreFrustumCheck)
        {
            return true;
        }
        else
        {
            AxisAlignedBB axisalignedbb = livingEntityIn.getRenderBoundingBox().grow(0.5D);

            if (axisalignedbb.hasNaN() || axisalignedbb.getAverageEdgeLength() == 0.0D)
            {
                axisalignedbb = new AxisAlignedBB(livingEntityIn.getPosX() - 2.0D, livingEntityIn.getPosY() - 2.0D, livingEntityIn.getPosZ() - 2.0D, livingEntityIn.getPosX() + 2.0D, livingEntityIn.getPosY() + 2.0D, livingEntityIn.getPosZ() + 2.0D);
            }

            return camera.isBoundingBoxInFrustum(axisalignedbb);
        }
    }

    public Vector3d getRenderOffset(T entityIn, float partialTicks)
    {
        return Vector3d.ZERO;
    }

    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        boolean isDeadmau5 = false;

        if (!Reflector.RenderNameplateEvent_Constructor.exists())
        {
            if (this.canRenderName(entityIn))
            {
                this.renderName(entityIn, entityIn.getDisplayName(), matrixStackIn, bufferIn, packedLightIn);
                isDeadmau5 = "deadmau5".equals(entityIn.getDisplayName().getString());
            }
        }
        else
        {
            Object object = Reflector.newInstance(Reflector.RenderNameplateEvent_Constructor, entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
            Reflector.postForgeBusEvent(object);
            Object object1 = Reflector.call(object, Reflector.Event_getResult);

            if (object1 != ReflectorForge.EVENT_RESULT_DENY && (object1 == ReflectorForge.EVENT_RESULT_ALLOW || this.canRenderName(entityIn)))
            {
                ITextComponent itextcomponent = (ITextComponent)Reflector.call(object, Reflector.RenderNameplateEvent_getContent);
                this.renderName(entityIn, itextcomponent, matrixStackIn, bufferIn, packedLightIn);
                isDeadmau5 = "deadmau5".equals(itextcomponent.getString());
            }
        }

        if(canRenderName(entityIn) && !StringUtil.isNullOrEmpty(MCStringUtils.strip(entityIn.getDisplayName().getString())) && (entityIn instanceof PlayerEntity || (entityIn instanceof MobEntity && Minecraft.getInstance().player.getDistance(entityIn) <= 10 && !((MobEntity) entityIn).getShouldBeDead()))) {
            HealthIndicator hi = Minecraft.getInstance().gameSettings._99thClientSettings.healthIndicator;

            if (hi != HealthIndicator.OFF) {
                float playerHealth = MathHelper.ceil(((LivingEntity) entityIn).getHealth());
                float playerGoldHealth = MathHelper.ceil(((LivingEntity) entityIn).getAbsorptionAmount());

                if (Minecraft.getInstance().gameSettings._99thClientSettings.healthIndicator == HealthIndicator.NUMBERS || playerHealth > 40 || playerGoldHealth > 20) {
                    this.renderHealthNumbers(entityIn, isDeadmau5, matrixStackIn, bufferIn, packedLightIn);
                } else if (Minecraft.getInstance().gameSettings._99thClientSettings.healthIndicator == HealthIndicator.ICONS) {
                    this.renderHealthIcons(entityIn, isDeadmau5, matrixStackIn, bufferIn, packedLightIn);
                }
            }
        }
    }

    protected boolean canRenderName(T entity)
    {
        return entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName();
    }

    /**
     * Returns the location of an entity's texture.
     */
    public abstract ResourceLocation getEntityTexture(T entity);

    /**
     * Returns the font renderer from the set render manager
     */
    public FontRenderer getFontRendererFromRenderManager()
    {
        return this.renderManager.getFontRenderer();
    }

    protected void renderName(T entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        double d0 = this.renderManager.squareDistanceTo(entityIn);
        boolean flag = !(d0 > 4096.0D);

        if (Reflector.ForgeHooksClient_isNameplateInRenderDistance.exists())
        {
            flag = Reflector.ForgeHooksClient_isNameplateInRenderDistance.callBoolean(entityIn, d0);
        }

        if (flag)
        {
            boolean flag1 = !entityIn.isDiscrete();
            float f = entityIn.getHeight() + 0.5F;
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, (double)f, 0.0D);
            matrixStackIn.rotate(this.renderManager.getCameraOrientation());
            matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            int j = (int)(f1 * 255.0F) << 24;
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            float f2 = (float)(-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);
            fontrenderer.func_243247_a(displayNameIn, f2, (float)i, 553648127, false, matrix4f, bufferIn, flag1, j, packedLightIn);

            if (flag1)
            {
                fontrenderer.func_243247_a(displayNameIn, f2, (float)i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }

            matrixStackIn.pop();
        }
    }

    protected void renderHealthNumbers(T entityIn, boolean isDeadmau5, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        Minecraft mci = Minecraft.getInstance();

        boolean flag1 = !entityIn.isDiscrete();

        float f = entityIn.getHeight() + 0.5F;
        int i = isDeadmau5 ? -10 : 0;
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, (double)f, 0.0D);
        matrixStackIn.rotate(this.renderManager.getCameraOrientation());
        matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f1 = mci.gameSettings.getTextBackgroundOpacity(0.25F);
        int j = (int)(f1 * 255.0F) << 24;
        FontRenderer fontrenderer = this.getFontRendererFromRenderManager();

        float health = MathHelper.ceil(((LivingEntity) entityIn).getHealth()) / 2.0F;
        float goldHealth = MathHelper.ceil(((LivingEntity) entityIn).getAbsorptionAmount()) / 2.0F;

        char hearth = '\u2764';
        IFormattableTextComponent healthText = new StringTextComponent(hearth + " " + health).setStyle(Style.EMPTY.setColor(Color.fromHex("#FF0000")));

        if(goldHealth > 0) {
            healthText.append(new StringTextComponent(" | ").setStyle(Style.EMPTY.setColor(Color.fromInt(-1))));
            healthText.append(new StringTextComponent(goldHealth + " " + hearth).setStyle(Style.EMPTY.setColor(Color.fromHex("#FFAA00"))));
        }

        matrixStackIn.translate(0.0D, (double) -10.0D, 0.0D);
        Matrix4f m4f = matrixStackIn.getLast().getMatrix();
        float f3 = (float)(-fontrenderer.getStringPropertyWidth(healthText) / 2);
        fontrenderer.func_243247_a(healthText, f3, (float) i, 553648127, false, m4f, bufferIn, flag1, j, packedLightIn);

        if (flag1)
        {
            fontrenderer.func_243247_a(healthText, f3, (float)i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
        }

        matrixStackIn.pop();
    }

    protected void renderHealthIcons(T entityIn, boolean isDeadmau5, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft mci = Minecraft.getInstance();

        boolean flag1 = !entityIn.isDiscrete();

        float f = entityIn.getHeight() + 0.5F;
        int i = isDeadmau5 ? -10 : 0;
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, (double) f, 0.0D);
        matrixStackIn.rotate(this.renderManager.getCameraOrientation());
        matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f1 = mci.gameSettings.getTextBackgroundOpacity(0.25F);
        int j = (int) (f1 * 255.0F) << 24;
        FontRenderer fontrenderer = this.getFontRendererFromRenderManager();

        float health = MathHelper.ceil(((LivingEntity) entityIn).getHealth());
        float goldHealth = MathHelper.ceil(((LivingEntity) entityIn).getAbsorptionAmount());

        char hearth = '\u2764';
        matrixStackIn.translate(0.0D, (double) -10.0D, 0.0D);
        Matrix4f m4f = matrixStackIn.getLast().getMatrix();

        for(int l = 0; l < 2; l++) {
            if(health - Math.floor(health) != 0) {
                String a = "";
            }
            if(health - l * 20 <= 0) continue;

            IFormattableTextComponent healthText = new StringTextComponent("");
            for (int k = 0; k < Math.min(10.0F, Math.floor((health - l * 20.0F) / 2.0F)); k++) {
                healthText.append(new StringTextComponent(hearth + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#AA0000"))));
            }
            if (Math.min(10.0F, health - l * 20.0F) % 2 == 1) {
                healthText.append(new StringTextComponent(hearth + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#FF5555"))));
            }
            for (int k = 0; k < Math.min(10.0F, 10.0F - Math.ceil((health - l * 20.0F) / 2.0F)); k++) {
                healthText.append(new StringTextComponent(hearth + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#555555"))));
            }

            float f3 = (float) (-fontrenderer.getStringPropertyWidth(healthText) / 2);
            fontrenderer.func_243247_a(healthText, f3, (float) i - (l * 10), 553648127, false, m4f, bufferIn, flag1, j, packedLightIn);

            if (flag1) {
                fontrenderer.func_243247_a(healthText, f3, (float) i - (l * 10), -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }
        }

        if(goldHealth > 0) {
            IFormattableTextComponent healthText = new StringTextComponent("");
            for (int k = 0; k < Math.min(10.0F, Math.floor(goldHealth / 2.0F)); k++) {
                healthText.append(new StringTextComponent(hearth + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#FFAA00"))));
            }
            if (goldHealth % 2 == 1) {
                healthText.append(new StringTextComponent(hearth + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#FFFF55"))));
            }
            for (int k = 0; k < Math.min(10.0F, 10.0F - Math.ceil(goldHealth / 2.0F)); k++) {
                healthText.append(new StringTextComponent(hearth + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#555555"))));
            }

            float f3 = (float) (-fontrenderer.getStringPropertyWidth(healthText) / 2);
            fontrenderer.func_243247_a(healthText, f3, (float) i - (health > 20 ? 20 : 10), 553648127, false, m4f, bufferIn, flag1, j, packedLightIn);

            if (flag1) {
                fontrenderer.func_243247_a(healthText, f3, (float) i - (health > 20 ? 20 : 10), -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }
        }

        matrixStackIn.pop();
    }

    public EntityRendererManager getRenderManager()
    {
        return this.renderManager;
    }

    public Either<EntityType, TileEntityType> getType()
    {
        return this.entityType == null ? null : Either.makeLeft(this.entityType);
    }

    public void setType(Either<EntityType, TileEntityType> p_setType_1_)
    {
        this.entityType = p_setType_1_.getLeft().get();
    }

    public ResourceLocation getLocationTextureCustom()
    {
        return this.locationTextureCustom;
    }

    public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_)
    {
        this.locationTextureCustom = p_setLocationTextureCustom_1_;
    }
}
