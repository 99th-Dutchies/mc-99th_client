package net.minecraft.client.gui.widget.list;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.ChatTriggersScreen;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
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
    private List<ChatTriggerEntry> entries = new ArrayList<>();

    public ChatTriggerList(ChatTriggersScreen chatTriggers, Minecraft mcIn)
    {
        super(mcIn, chatTriggers.width + 45, chatTriggers.height, 43, chatTriggers.height - 32, 20);
        this.chatTriggersScreen = chatTriggers;
        this.loadTriggers();
    }

    public void loadTriggers() {
        ChatTrigger chattrigger;
        this.clearEntries();
        for(ChatTriggerList.ChatTriggerEntry entry : this.entries) {
            this.chatTriggersScreen.children.remove(entry.btnToggleActive);
            this.chatTriggersScreen.children.remove(entry.patternField);
            this.chatTriggersScreen.children.remove(entry.responseField);
        }
        this.entries.clear();

        for(int j = 0; j < this.minecraft.gameSettings.chatTriggers.size(); j++)
        {
            chattrigger = this.minecraft.gameSettings.chatTriggers.get(j);
            ITextComponent pattern = new TranslationTextComponent(chattrigger.pattern.pattern());
            ITextComponent response = new TranslationTextComponent(chattrigger.response);

            ChatTriggerEntry entry = new ChatTriggerList.ChatTriggerEntry(j, chattrigger, pattern, response);
            this.addEntry(entry);
            this.entries.add(entry);
        }
    }

    protected int getScrollbarPosition() { return this.chatTriggersScreen.width - 10; }

    public int getRowWidth()
    {
        return 400;
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

        protected final Button btnToggleActive;
        protected final TextFieldWidget patternField;
        protected final TextFieldWidget responseField;

        private ChatTriggerEntry(int index, final ChatTrigger chatTrigger, final ITextComponent pattern, final ITextComponent response)
        {
            this.index = index;
            this.chatTrigger = chatTrigger;
            this.pattern = pattern;
            this.response = response;

            this.patternField = new TextFieldWidget(
                    ChatTriggerList.this.minecraft.fontRenderer,
                    ChatTriggerList.this.chatTriggersScreen.width / 2 - 180,
                    65 + this.index * 25,
                    150,
                    20,
                    new TranslationTextComponent("99thdc.options.chattriggers.regex"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.patternField.setMaxStringLength(256);
            this.patternField.setText(this.pattern.getString());
            this.patternField.setResponder((p_214319_1_) ->
            {
                this.chatTrigger.pattern = Pattern.compile(p_214319_1_);
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.patternField);

            this.responseField = new TextFieldWidget(
                    ChatTriggerList.this.minecraft.fontRenderer,
                    ChatTriggerList.this.chatTriggersScreen.width / 2 - 25,
                    65 + this.index * 25,
                    150,
                    20,
                    new TranslationTextComponent("99thdc.options.chattriggers.response"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.responseField.setMaxStringLength(256);
            this.responseField.setText(this.chatTrigger.response);
            this.responseField.setResponder((p_214319_1_) ->
            {
                this.chatTrigger.response = p_214319_1_;
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.responseField);

            this.btnToggleActive = new Button(ChatTriggerList.this.chatTriggersScreen.width / 2 + 130, 65 + this.index * 25, 75, 20, this.chatTrigger.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.chatTrigger.active = !this.chatTrigger.active;
                button.setMessage(this.chatTrigger.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
                ChatTriggerList.this.minecraft.gameSettings.setChatTrigger(this.index, this.chatTrigger);
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.btnToggleActive);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            this.patternField.y = p_230432_3_;
            this.patternField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.responseField.y = p_230432_3_;
            this.responseField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnToggleActive.y = p_230432_3_;
            this.btnToggleActive.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
        }

        public List <? extends IGuiEventListener > getEventListeners()
        {
            return new ArrayList();
        }
    }
}
