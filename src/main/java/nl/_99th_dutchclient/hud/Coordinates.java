package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

public class Coordinates
{
    public static void render(Minecraft mc, MatrixStack ms) {
        // Draw X coordinate
        mc.fontRenderer.drawString(ms, "[X] " + (int) mc.player.getPosX(), 0, 0, -1);

        // Draw Y coordinate
        mc.fontRenderer.drawString(ms, "[Y] " + (int) mc.player.getPosY(), 0, 10, -1);

        // Draw Z coordinate
        mc.fontRenderer.drawString(ms, "[Z] " + (int) mc.player.getPosZ(), 0, 20, -1);

        // Determine direction based on rotation
        String dir = "";
        float rot = mc.player.rotationYaw % 360.0F;

        if (rot < 0) {
            dir = "?";
        } else if (rot <= 45) {
            dir = "N";
        } else if (rot <= 135) {
            dir = "E";
        } else if (rot <= 225) {
            dir = "S";
        } else if (rot <= 315) {
            dir = "W";
        } else if (rot < 360) {
            dir = "N";
        } else {
            dir = "?";
        }
        // Draw orientation/direction
        mc.fontRenderer.drawString(ms, "[" + dir + "] " + (int) rot, 0, 30, -1);

        // Draw biome
        String biomeName = mc.player.world.getBiome(mc.player.getPosition()).getCategory().getName();
        mc.fontRenderer.drawString(ms, "[B] " + biomeName, 0, 40, -1);
    }

    private static void renderX(Minecraft mc, MatrixStack ms) {

    }

    private static void renderY(Minecraft mc, MatrixStack ms) {

    }

    private static void renderZ(Minecraft mc, MatrixStack ms) {

    }

    private static void renderOrientation(Minecraft mc, MatrixStack ms) {

    }
}
