package nl._99th_client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.optifine.Config;
import nl._99th_client.settings.HUDSetting;

import java.util.ArrayList;
import java.util.List;

public class CPSInfo
{
    public static void renderLeft(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        List<Long> remove = new ArrayList<>();
        long now = System.currentTimeMillis();
        for(Long click : mc.lastLeftClicks) {
            if(click + 1000 < now) {
                remove.add(click);
            }
        }
        for(Long r : remove) {
            mc.lastLeftClicks.remove(r);
        }

        if(mc.lastLeftClicks.size() > 0) {
            drawCPS(mc.lastLeftClicks.size() + "", hudSetting.x, hudSetting.y, mc, ms);
        }
    }

    public static void renderRight(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        List<Long> remove = new ArrayList<>();
        long now = System.currentTimeMillis();
        for(Long click : mc.lastRightClicks) {
            if(click + 1000 < now) {
                remove.add(click);
            }
        }
        for(Long r : remove) {
            mc.lastRightClicks.remove(r);
        }

        if(mc.lastRightClicks.size() > 0) {
            drawCPS(mc.lastRightClicks.size() + "", hudSetting.x, hudSetting.y, mc, ms);
        }
    }

    private static void drawCPS(String cps, int x, int y, Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(
                ms,
                cps,
                mc.getMainWindow().getScaledWidth() / 2 + x,
                mc.getMainWindow().getScaledHeight() - y,
                -1);
    }
}
