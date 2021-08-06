package nl._99th_dutchclient.chat;

import java.util.regex.Matcher;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.AbstractChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import nl._99th_dutchclient.chat.ChatTrigger;

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
            if(!trigger.active) continue;

            Matcher matcher = trigger.match(message.getString());
            
            String resp = trigger.response;
            int matches = 0;

            while(matcher.find()) {
                matches++;
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    resp = resp.replace("$" + i, matcher.group(i));
                }
            }

            if(matches > 0) {
                this.mc.ingameGUI.getChatGUI().addToSentMessages(resp);
                this.mc.player.sendChatMessage(resp);
            }
        }
    }
}
