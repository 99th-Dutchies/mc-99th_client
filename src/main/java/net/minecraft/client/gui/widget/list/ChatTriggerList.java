package net.minecraft.client.gui.widget.list;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.ChatTriggersScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_dutchclient.chat.ChatTrigger;
import org.apache.commons.lang3.ArrayUtils;

public class ChatTriggerList extends AbstractOptionList<ChatTriggerList.Entry>
{
    private final ChatTriggersScreen chatTriggersScreen;
    private int maxListLabelWidth;


    public ChatTriggerList(ChatTriggersScreen chatTriggers, Minecraft mcIn)
    {
        super(mcIn, chatTriggers.width + 45, chatTriggers.height, 43, chatTriggers.height - 32, 20);
        this.chatTriggersScreen = chatTriggers;
        ChatTrigger chattrigger;

        for(int j = 0; j < mcIn.gameSettings.chatTriggers.size(); j++)
        {
            chattrigger = mcIn.gameSettings.chatTriggers.get(j);
            ITextComponent pattern = new TranslationTextComponent(chattrigger.pattern.pattern());
            ITextComponent response = new TranslationTextComponent(chattrigger.response);
            int ip = mcIn.fontRenderer.getStringPropertyWidth(pattern);
            int ir = mcIn.fontRenderer.getStringPropertyWidth(response);

            if (ip > this.maxListLabelWidth)
            {
                this.maxListLabelWidth = ip;
            }
            if (ir > this.maxListLabelWidth)
            {
                this.maxListLabelWidth = ir;
            }

            this.addEntry(new ChatTriggerList.ChatTriggerEntry(j, chattrigger, pattern, response));
        }
    }

    protected int getScrollbarPosition()
    {
        return super.getScrollbarPosition() + 15;
    }

    public int getRowWidth()
    {
        return super.getRowWidth() + 32;
    }

    public abstract static class Entry extends AbstractOptionList.Entry<ChatTriggerList.Entry>
    {
    }

    public class ChatTriggerEntry extends ChatTriggerList.Entry
    {
        private final ChatTrigger chatTrigger;
        private final int index;
        private final ITextComponent pattern;
        private final ITextComponent response;
        private final Button btnToggleActive;

        private ChatTriggerEntry(int index, final ChatTrigger chatTrigger, final ITextComponent pattern, final ITextComponent response)
        {
            this.index = index;
            this.chatTrigger = chatTrigger;
            this.pattern = pattern;
            this.response = response;

            this.btnToggleActive = new Button(0, 0, 75, 20, this.chatTrigger.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.chatTrigger.active = !this.chatTrigger.active;
                button.setMessage(this.chatTrigger.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
                ChatTriggerList.this.minecraft.gameSettings.setChatTrigger(this.index, this.chatTrigger);
            });

            ChatTriggerList.this.chatTriggersScreen.addButton(this.btnToggleActive);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            ChatTriggerList.this.minecraft.fontRenderer.func_243248_b(
                    p_230432_1_,
                    this.pattern,
                    (float)(p_230432_4_ + 40 - ChatTriggerList.this.maxListLabelWidth),
                    (float)(p_230432_3_ + p_230432_6_ / 2 - 9 / 2),
                    16777215);
            ChatTriggerList.this.minecraft.fontRenderer.func_243248_b(
                    p_230432_1_,
                    this.response,
                    (float)(p_230432_4_ + 90),
                    (float)(p_230432_3_ + p_230432_6_ / 2 - 9 / 2),
                    16777215);
            this.btnToggleActive.x = p_230432_4_ + 50 + ChatTriggerList.this.maxListLabelWidth;
            this.btnToggleActive.y = p_230432_3_;

            this.btnToggleActive.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
        }

        public List <? extends IGuiEventListener > getEventListeners()
        {
            return new ArrayList();
        }
    }
}
