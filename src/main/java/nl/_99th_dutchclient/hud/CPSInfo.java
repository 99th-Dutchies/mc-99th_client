package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.optifine.Config;

import java.util.ArrayList;
import java.util.List;

public class CPSInfo
{
    public static void render(Minecraft mc, MatrixStack ms, float pt) {
        // Draw right clicks
        renderCPSright(mc, ms);

        // Draw left clicks
        renderCPSleft(mc, ms);
    }

    private static void renderCPSleft(Minecraft mc, MatrixStack ms) {
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
            if(getMainHandside(mc) == HandSide.RIGHT) {
                drawCPSRight(mc.lastLeftClicks.size() + "", mc, ms);
            } else {
                drawCPSLeft(mc.lastLeftClicks.size() + "", mc, ms);
            }
        }
    }

    private static void renderCPSright(Minecraft mc, MatrixStack ms) {
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
            if(getMainHandside(mc) == HandSide.RIGHT) {
                drawCPSLeft(mc.lastRightClicks.size() + "", mc, ms);
            } else {
                drawCPSRight(mc.lastRightClicks.size() + "", mc, ms);
            }
        }
    }

    private static void drawCPSLeft(String cps, Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(
                ms,
                cps,
                mc.getMainWindow().getScaledWidth() / 2  - 91 - (getMainHandside(mc) == HandSide.RIGHT ? 29 : 0) - 5 - mc.fontRenderer.getStringWidth(mc.lastRightClicks.size() + ""),
                mc.getMainWindow().getScaledHeight() - 15,
                -1);
    }

    private static void drawCPSRight(String cps, Minecraft mc, MatrixStack ms) {
        mc.fontRenderer.drawStringWithShadow(
                ms,
                cps,
                mc.getMainWindow().getScaledWidth() / 2  + 91 + (getMainHandside(mc) == HandSide.RIGHT ? 0 : 29) + 5,
                mc.getMainWindow().getScaledHeight() - 15,
                -1);
    }

    private static HandSide getMainHandside(Minecraft mc) {
        PlayerEntity playerentity = !(mc.getRenderViewEntity() instanceof PlayerEntity) ? null : (PlayerEntity)mc.getRenderViewEntity();

        if(playerentity == null) return HandSide.RIGHT;

        return playerentity.getPrimaryHand();
    }
}
