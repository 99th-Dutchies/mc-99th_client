package nl._99th_client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.*;
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
            drawCPS(mc.lastLeftClicks.size() + "", hudSetting, mc, ms);
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
            drawCPS(mc.lastRightClicks.size() + "", hudSetting, mc, ms);
        }
    }

    private static void drawCPS(String cps, HUDSetting hudSetting, Minecraft mc, MatrixStack ms) {
        IFormattableTextComponent s = new StringTextComponent(cps).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        }
    }
}
