package nl._99th_dutchclient.chat;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.UUID;
import io.netty.util.internal.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.AbstractChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import nl._99th_dutchclient.settings.ActiveAFK;

public class ChatTriggerListener extends AbstractChatListener
{
    private final Minecraft mc;

    public ChatTriggerListener(Minecraft minecraft)
    {
        this.mc = minecraft;
    }

    /**
     * Called whenever this listener receives a chat message, if this listener is registered to the given type in {@link
     * net.minecraft.client.gui.GuiIngame#chatListeners chatListeners}
     */
    public void say(ChatType chatTypeIn, ITextComponent message, UUID sender)
    {
        if(this.mc.player == null) return;

        String msg = message.getString();

        for(ChatTrigger trigger : this.mc.gameSettings.chatTriggers) {
            if(trigger.active == ActiveAFK.OFF || (trigger.active == ActiveAFK.ACTIVEONLY && this.mc.afkStatus.isAFK()) || (trigger.active == ActiveAFK.AFKONLY && !this.mc.afkStatus.isAFK())) continue;

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
                this.schedule(this.mc, resp, trigger.delay);
            }
        }
    }

    private void schedule(final Minecraft mc, final String resp, int delay) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        exec.schedule(() -> {
            mc.ingameGUI.getChatGUI().addToSentMessages(resp);
            mc.player.sendChatMessage(resp);
        }, delay, TimeUnit.MILLISECONDS);
    }
}
