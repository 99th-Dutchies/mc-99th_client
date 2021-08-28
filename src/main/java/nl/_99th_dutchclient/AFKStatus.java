package nl._99th_dutchclient;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class AFKStatus
{
    private Minecraft mc;
    private boolean isAFK;
    private Long lastMove;

    public AFKStatus(Minecraft mc) {
        this.mc = mc;
        this.isAFK = false;
        this.lastMove = System.currentTimeMillis();
    }

    public void moved() {
        if(this.isAFK) {
            this.mc.ingameGUI.setOverlayMessage(new TranslationTextComponent("99thdc.afk.deactivated").setStyle(Style.EMPTY.setColor(Color.fromHex("#AA0000")).setBold(true)), false);
        }

        this.isAFK = false;
        this.lastMove = System.currentTimeMillis();
    }

    public void check() {
        if (this.mc.gameSettings.timeTillAFK == 0) return;

        long now = System.currentTimeMillis();

        if(this.lastMove < now - (this.mc.gameSettings.timeTillAFK * 1000)) {
            this.isAFK = true;
        }

        if(this.isAFK) {
            this.mc.ingameGUI.setOverlayMessage(new TranslationTextComponent("99thdc.afk.activated").setStyle(Style.EMPTY.setColor(Color.fromHex("#00AA00")).setBold(true)), false);
        }
    }

    public boolean isAFK() {
        return this.isAFK;
    }

    public Long getLastMove() {
        return this.lastMove;
    }
}
