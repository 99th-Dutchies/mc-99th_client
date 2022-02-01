package nl._99th_client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import nl._99th_client.settings.HUDSetting;

public class LocationInfo
{
    public static void render(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt) {
        // Draw X, Y and Z coordinates
        renderX(mc, hudSetting, ms);
        renderY(mc, hudSetting, ms);
        renderZ(mc, hudSetting, ms);

        // Draw direction/orientation
        renderDir(mc, hudSetting, ms);

        // Draw biome
        renderBiome(mc, hudSetting, ms);
    }

    private static void renderX(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(ms, "[X] " + (int) mc.player.getPosX(), hudSetting.x, hudSetting.y, -1);
    }

    private static void renderY(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(ms, "[Y] " + (int) mc.player.getPosY(), hudSetting.x, hudSetting.y + 10, -1);
    }

    private static void renderZ(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(ms, "[Z] " + (int) mc.player.getPosZ(), hudSetting.x, hudSetting.y + 20, -1);
    }

    private static void renderDir(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        // Determine direction based on rotation
        float rot = ((mc.player.rotationYaw % 360.0F) + 360.0F) % 360.0F;

        String dir = rotationToDirection(rot);
        String deltaX = rotationToDeltaX(rot);
        String deltaY = rotationToDeltaZ(rot);

        // Draw orientation/direction
        mc.fontRenderer.drawStringWithShadow(ms, deltaX, hudSetting.x + 60, hudSetting.y, -1);
        mc.fontRenderer.drawStringWithShadow(ms, dir, hudSetting.x + 60, hudSetting.y + 10, -1);
        mc.fontRenderer.drawStringWithShadow(ms, deltaY, hudSetting.x + 60, hudSetting.y + 20, -1);
    }

    private static String rotationToDirection(float rotation) {
        if (rotation < 0) {
            return "?";
        } else if (rotation <= 45) {
            return "S";
        } else if (rotation <= 135) {
            return "W";
        } else if (rotation <= 225) {
            return "N";
        } else if (rotation <= 315) {
            return "E";
        } else if (rotation < 360) {
            return "S";
        } else {
            return "?";
        }
    }

    private static String rotationToDeltaX(float rotation) {
        if (rotation < 0) {
            return "?";
        } else if (rotation <= 22.5) {
            return "";
        } else if (rotation <= 67.5) {
            return "-";
        } else if (rotation <= 112.5) {
            return "--";
        } else if (rotation <= 157.5) {
            return "-";
        } else if (rotation <= 202.5) {
            return "";
        } else if (rotation <= 247.5) {
            return "+";
        } else if (rotation <= 292.5) {
            return "++";
        } else if (rotation <= 337.5) {
            return "+";
        } else if (rotation < 360) {
            return "";
        } else {
            return "?";
        }
    }

    private static String rotationToDeltaZ(float rotation) {
        if (rotation < 0) {
            return "?";
        } else if (rotation <= 22.5) {
            return "++";
        } else if (rotation <= 67.5) {
            return "+";
        } else if (rotation <= 112.5) {
            return "";
        } else if (rotation <= 157.5) {
            return "-";
        } else if (rotation <= 202.5) {
            return "--";
        } else if (rotation <= 247.5) {
            return "-";
        } else if (rotation <= 292.5) {
            return "";
        } else if (rotation <= 337.5) {
            return "+";
        } else if (rotation < 360) {
            return "++";
        } else {
            return "?";
        }
    }

    private static void renderBiome(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        String biomeName = mc.player.world.getBiome(mc.player.getPosition()).getCategory().getName().replaceAll("_", " ");
        biomeName = biomeName.substring(0, 1).toUpperCase() + biomeName.substring(1);
        mc.fontRenderer.drawStringWithShadow(ms, biomeName, hudSetting.x, hudSetting.y + 30, -1);
    }
}
