package nl._99th_client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.optifine.Config;
import nl._99th_client.settings.HUDSetting;

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
            String fps = Config.getFpsString().split("/")[0];

            IFormattableTextComponent s = new StringTextComponent(fps + " FPS").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));

            if(hudSetting.dropShadow) {
                mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
            } else {
                mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
            }
        }
    }

    private static void renderPing(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        NetworkPlayerInfo playerInfo = mc.getConnection().getPlayerInfo(mc.player.getUniqueID());
        ServerData currentServer = mc.getCurrentServerData();
        String ping = "";

        if(playerInfo != null) {
            ping = playerInfo.getResponseTime() + " ms";
        } else if(currentServer != null) {
            ping = currentServer.pingToServer + " ms";
        }

        IFormattableTextComponent s = new StringTextComponent(ping).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY() + 30, -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY() + 30, -1);
        }
    }

    private static void renderMemory(Minecraft mc, HUDSetting hudSetting, MatrixStack ms){
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;

        IFormattableTextComponent s = new StringTextComponent(bytesToMb(l) + " MB").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY() + 10, -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY() + 10, -1);
        }
    }

    private static long bytesToMb(long bytes)
    {
        return bytes / 1024L / 1024L;
    }
}
