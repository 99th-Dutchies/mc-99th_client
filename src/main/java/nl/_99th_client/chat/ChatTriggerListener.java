package nl._99th_client.chat;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.UUID;
import io.netty.util.internal.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.AbstractChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import nl._99th_client.settings.ActiveAFK;

public class ChatTriggerListener extends AbstractChatListener
{
    private final Minecraft mc;

    public ChatTriggerListener(Minecraft minecraft)
    {
        this.mc = minecraft;
    }

    /**
     * Called whenever this listener receives a chat message, if this listener is registered to the given type in
     */
    public void say(ChatType chatTypeIn, ITextComponent message, UUID sender)
    {
        if(this.mc.player == null) return;

        String msg = message.getString();

        for(ChatTrigger trigger : this.mc.gameSettings._99thClientSettings.chatTriggers) {
            if(trigger.response.isEmpty() || trigger.active == ActiveAFK.OFF || (trigger.active == ActiveAFK.ACTIVEONLY && this.mc.afkStatus.isAFK()) || (trigger.active == ActiveAFK.AFKONLY && !this.mc.afkStatus.isAFK())) continue;

            Matcher matcher = trigger.match(message.getString());
            
            String resp = trigger.response;
            int matches = 0;

            while(matcher.find()) {
                if(StringUtil.isNullOrEmpty(matcher.group())) continue;
                matches++;
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    resp = resp.replace("$" + i, matcher.group(i));
                }
            }

            if(matches > 0) {
                if(trigger.checkCooldown(resp)) continue;

                this.schedule(this.mc, resp, trigger.delay);
            }
        }
    }

    private void schedule(final Minecraft mc, final String resp, int delay) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        exec.schedule(() -> {
            String[] split = resp.split("\\|");

            for(String r : split) {
                if(r.startsWith("\\")) {
                    this.mc.commandManager.checkCommand(r);
                    continue;
                }

                mc.ingameGUI.getChatGUI().addToSentMessages(r);
                mc.player.sendChatMessage(r);
            }
        }, delay, TimeUnit.MILLISECONDS);
    }
}
