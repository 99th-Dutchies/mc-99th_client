package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

public class LocationInfo
{
    public static void render(Minecraft mc, MatrixStack ms) {
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
        mc.fontRenderer.drawString(ms, "[X] " + (int) mc.player.getPosX(), 1, 1, -1);
    }

    private static void renderY(Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawString(ms, "[Y] " + (int) mc.player.getPosY(), 1, 11, -1);
    }

    private static void renderZ(Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawString(ms, "[Z] " + (int) mc.player.getPosZ(), 1, 21, -1);
    }

    private static void renderDir(Minecraft mc, MatrixStack ms) {
        // Determine direction based on rotation
        float rot = mc.player.rotationYaw % 360.0F;
        rot = rot < 0 ? rot * -1 : rot;
        String dir = rotationToDirection(rot);

        // Draw orientation/direction
        mc.fontRenderer.drawString(ms, "[" + dir + "] " + (int) rot, 1, 31, -1);
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

    private static void renderBiome(Minecraft mc, MatrixStack ms) {
        String biomeName = mc.player.world.getBiome(mc.player.getPosition()).getCategory().getName();
        mc.fontRenderer.drawString(ms, "[B] " + biomeName, 1, 41, -1);
    }
}
