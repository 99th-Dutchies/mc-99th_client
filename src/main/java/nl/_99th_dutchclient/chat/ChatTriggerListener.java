package nl._99th_dutchclient.chat;

import java.util.regex.Matcher;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import nl._99th_dutchclient.chat.ChatTrigger;

public class ChatTriggerListener implements IChatListener
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

        for(ChatTrigger trigger : this.mc.gameSettings.chatTriggers) {
            Matcher matcher = trigger.match(message.getString());

            if(matcher.matches()) {
                String resp = trigger.response;

                for(int i = 0; i < matcher.groupCount(); i++) {
                    resp = resp.replace("$" + (i+1), matcher.group(i));
                }

                this.mc.ingameGUI.getChatGUI().addToSentMessages(resp);
                this.mc.player.sendChatMessage(resp);
            }
        }
    }
}
