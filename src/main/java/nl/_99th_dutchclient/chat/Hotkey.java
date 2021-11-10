package nl._99th_dutchclient.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class Hotkey {
    public KeyBinding keyBinding;
    public String response;
    public boolean active;

    public Hotkey(KeyBinding keyBinding, String response, boolean active) {
        this.keyBinding = keyBinding;
        this.response = response;
        this.active = active;
    }

    public void handle() {
        Minecraft mc = Minecraft.getInstance();

        if(this.active && this.keyBinding.isPressed()) {
            String[] split = response.split("\\|");

            for(String r : split) {
                if(r.startsWith("\\")) {
                    mc.commandManager.checkCommand(r);
                    continue;
                }

                mc.ingameGUI.getChatGUI().addToSentMessages(r);
                mc.player.sendChatMessage(r);
            }
        }
    }
}
