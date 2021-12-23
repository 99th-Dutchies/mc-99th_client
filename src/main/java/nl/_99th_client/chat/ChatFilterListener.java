package nl._99th_client.chat;

import io.netty.util.internal.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.AbstractChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

import java.util.UUID;
import java.util.regex.Matcher;

public class ChatFilterListener extends AbstractChatListener
{
    private final Minecraft mc;

    public ChatFilterListener(Minecraft minecraft)
    {
        this.mc = minecraft;
    }

    /**
     * Called whenever this listener receives a chat message, if this listener is registered to the given type in {@link
     * net.minecraft.client.gui.GuiIngame#chatListeners chatListeners}
     */
    public void say(ChatType chatTypeIn, ITextComponent message, UUID sender)
    {
        return;
    }

    public boolean shouldHide(ChatType chatTypeIn, ITextComponent message, UUID sender) {
        for(ChatFilter filter : this.mc.gameSettings.chatFilters) {
            if(!filter.activeChat) continue;

            Matcher matcher = filter.match(message.getString());

            while(matcher.find()) {
                if(StringUtil.isNullOrEmpty(matcher.group())) continue;
                return true;
            }
        }

        return false;
    }
}
