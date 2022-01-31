package nl._99th_client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class AFKStatus
{
    private Minecraft mc;
    private boolean isAFK;
    private Long lastMove;
    private boolean forcedAFK;

    public AFKStatus(Minecraft mc) {
        this.mc = mc;
        this.isAFK = false;
        this.lastMove = System.currentTimeMillis();
        this.forcedAFK = false;
    }

    public void moved() {
        if(this.isAFK) {
            this.mc.ingameGUI.setClientTitle(new TranslationTextComponent("99thclient.afk.deactivated").setStyle(Style.EMPTY.setColor(Color.fromHex("#AA0000")).setBold(true)));
        }

        this.isAFK = false;
        this.lastMove = System.currentTimeMillis();
        this.forcedAFK = false;
    }

    public void check() {
        if (this.mc.gameSettings._99thClientSettings.timeTillAFK == 0 && !this.forcedAFK) return;
        if (this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown() ||
                this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() ||
                this.mc.gameSettings.keyBindJump.isKeyDown() || this.mc.gameSettings.keyBindSneak.isKeyDown()) {
            this.moved();
        }

        long now = System.currentTimeMillis();

        if(this.lastMove < now - (this.mc.gameSettings._99thClientSettings.timeTillAFK * 1000) || this.forcedAFK) {
            this.isAFK = true;
        }

        if(this.isAFK) {
            this.mc.ingameGUI.setClientTitle(new TranslationTextComponent("99thclient.afk.activated").setStyle(Style.EMPTY.setColor(Color.fromHex("#00AA00")).setBold(true)));
        }
    }

    public void toggle() {
        if(this.isAFK) {
            this.moved();
        } else {
            this.forcedAFK = true;
            this.check();
        }
    }

    public boolean isAFK() {
        return this.isAFK;
    }

    public Long getLastMove() {
        return this.lastMove;
    }
}
