package nl._99th_client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.optifine.Config;
import nl._99th_client.settings.HUDSetting;

import java.util.ArrayList;
import java.util.List;

public class SystemInfo
{
    public static void render(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt) {
        // Draw FPS
        renderFPS(mc, hudSetting, ms);

        // Draw memory usage
        renderMemory(mc, hudSetting, ms);

        // Draw ping
        renderPing(mc, hudSetting, ms);
    }

    private static void renderFPS(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        int l = mc.debug.indexOf(" fps ");

        if (l >= 0)
        {
            String s = Config.getFpsString().split("/")[0];
            mc.fontRenderer.drawStringWithShadow(ms, s + " FPS", hudSetting.x, hudSetting.y, -1);
        }
    }

    private static void renderPing(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        NetworkPlayerInfo playerInfo = mc.getConnection().getPlayerInfo(mc.player.getUniqueID());
        ServerData currentServer = mc.getCurrentServerData();

        if(playerInfo != null) {
            mc.fontRenderer.drawStringWithShadow(ms, playerInfo.getResponseTime() + " ms", hudSetting.x, hudSetting.y + 30, -1);
        } else if(currentServer != null) {
            mc.fontRenderer.drawStringWithShadow(ms, currentServer.pingToServer + " ms", hudSetting.x, hudSetting.y + 30, -1);
        }
    }

    private static void renderMemory(Minecraft mc, HUDSetting hudSetting, MatrixStack ms){
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;

        mc.fontRenderer.drawStringWithShadow(ms, bytesToMb(l) + " MB", hudSetting.x, hudSetting.y + 10, -1);
    }

    private static long bytesToMb(long bytes)
    {
        return bytes / 1024L / 1024L;
    }
}
