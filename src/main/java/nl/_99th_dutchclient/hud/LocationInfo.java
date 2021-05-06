package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

public class LocationInfo
{
    public static void render(Minecraft mc, MatrixStack ms) {
        // Draw X coordinate
        mc.fontRenderer.drawString(ms, "[X] " + (int) mc.player.getPosX(), 1, 1, -1);

        // Draw Y coordinate
        mc.fontRenderer.drawString(ms, "[Y] " + (int) mc.player.getPosY(), 1, 11, -1);

        // Draw Z coordinate
        mc.fontRenderer.drawString(ms, "[Z] " + (int) mc.player.getPosZ(), 1, 21, -1);

        // Determine direction based on rotation
        String dir = "";
        float rot = mc.player.rotationYaw % 360.0F;
		rot = rot < 0 ? rot * -1 : rot;

        if (rot < 0) {
            dir = "?";
        } else if (rot <= 45) {
            dir = "S";
        } else if (rot <= 135) {
            dir = "W";
        } else if (rot <= 225) {
            dir = "N";
        } else if (rot <= 315) {
            dir = "E";
        } else if (rot < 360) {
            dir = "S";
        } else {
            dir = "?";
        }
        // Draw orientation/direction
        mc.fontRenderer.drawString(ms, "[" + dir + "] " + (int) rot, 1, 31, -1);

        // Draw biome
        String biomeName = mc.player.world.getBiome(mc.player.getPosition()).getCategory().getName();
        mc.fontRenderer.drawString(ms, "[B] " + biomeName, 1, 41, -1);
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
