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
    public static void renderFPS(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt) {
        int l = mc.debug.indexOf(" fps ");

        if (l >= 0)
        {
            String fps = Config.getFpsString().split("/")[0];

            IFormattableTextComponent s = new StringTextComponent(fps).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));
            s.append(new StringTextComponent( " FPS").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor))));

            if(hudSetting.dropShadow) {
                mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
            } else {
                mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
            }
        }
    }

    public static void renderPing(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt) {
        NetworkPlayerInfo playerInfo = mc.getConnection().getPlayerInfo(mc.player.getUniqueID());
        ServerData currentServer = mc.getCurrentServerData();
        String ping = "";

        if(playerInfo != null) {
            ping = playerInfo.getResponseTime() + "";
        } else if(currentServer != null) {
            ping = currentServer.pingToServer + "";
        }

        IFormattableTextComponent s = new StringTextComponent(ping).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));
        s.append(new StringTextComponent(" ms").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor))));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        }
    }

    public static void renderMemory(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt){
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;

        IFormattableTextComponent s = new StringTextComponent(bytesToMb(l) + "").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));
        s.append(new StringTextComponent(" MB").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor))));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        }
    }

    private static long bytesToMb(long bytes)
    {
        return bytes / 1024L / 1024L;
    }
}
