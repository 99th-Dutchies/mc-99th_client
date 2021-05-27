package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.optifine.Config;

import java.util.ArrayList;
import java.util.List;

public class SystemInfo
{
    public static void render(Minecraft mc, MatrixStack ms) {
        // Draw FPS
        renderFPS(mc, ms);

        // Draw memory usage
        renderMemory(mc, ms);

        // Draw ping
        renderPing(mc, ms);
    }

    private static void renderFPS(Minecraft mc, MatrixStack ms) {
        int l = mc.debug.indexOf(" fps ");

        if (l >= 0)
        {
            String s = Config.getFpsString().split("/")[0];
            mc.fontRenderer.drawStringWithShadow(ms, s + " FPS", 91, 1, -1);
        }
    }

    private static void renderPing(Minecraft mc, MatrixStack ms) {
        NetworkPlayerInfo playerInfo = mc.getConnection().getPlayerInfo(mc.player.getUniqueID());
        ServerData currentServer = mc.getCurrentServerData();

        if(playerInfo != null) {
            mc.fontRenderer.drawStringWithShadow(ms, playerInfo.getResponseTime() + " ms", 91, 31, -1);
        } else if(currentServer != null) {
            mc.fontRenderer.drawStringWithShadow(ms, currentServer.pingToServer + " ms", 91, 31, -1);
        }
    }

    private static void renderMemory(Minecraft mc, MatrixStack ms){
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;

        mc.fontRenderer.drawStringWithShadow(ms, bytesToMb(l) + " MB", 91, 11, -1);
    }

    private static long bytesToMb(long bytes)
    {
        return bytes / 1024L / 1024L;
    }
}
