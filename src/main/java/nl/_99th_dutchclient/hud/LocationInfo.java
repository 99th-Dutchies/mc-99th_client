package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

public class LocationInfo
{
    public static void render(Minecraft mc, MatrixStack ms, float pt) {
        // Draw X, Y and Z coordinates
        renderX(mc, ms);
        renderY(mc, ms);
        renderZ(mc, ms);

        // Draw direction/oritentation
        renderDir(mc, ms);

        // Draw biome
        renderBiome(mc, ms);
    }

    private static void renderX(Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(ms, "[X] " + (int) mc.player.getPosX(), 1, 1, -1);
    }

    private static void renderY(Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(ms, "[Y] " + (int) mc.player.getPosY(), 1, 11, -1);
    }

    private static void renderZ(Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(ms, "[Z] " + (int) mc.player.getPosZ(), 1, 21, -1);
    }

    private static void renderDir(Minecraft mc, MatrixStack ms) {
        // Determine direction based on rotation
        float rot = ((mc.player.rotationYaw % 360.0F) + 360.0F) % 360.0F;

        String dir = rotationToDirection(rot);
        String deltaX = rotationToDeltaX(rot);
        String deltaY = rotationToDeltaZ(rot);

        // Draw orientation/direction
        mc.fontRenderer.drawStringWithShadow(ms, deltaX, 61, 1, -1);
        mc.fontRenderer.drawStringWithShadow(ms, dir, 61, 11, -1);
        mc.fontRenderer.drawStringWithShadow(ms, deltaY, 61, 21, -1);
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

    private static void renderBiome(Minecraft mc, MatrixStack ms) {
        String biomeName = mc.player.world.getBiome(mc.player.getPosition()).getCategory().getName().replaceAll("_", " ");
        biomeName = biomeName.substring(0, 1).toUpperCase() + biomeName.substring(1);
        mc.fontRenderer.drawStringWithShadow(ms, biomeName, 1, 31, -1);
    }
}
