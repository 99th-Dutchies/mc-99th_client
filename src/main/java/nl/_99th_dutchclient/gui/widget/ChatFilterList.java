package nl._99th_dutchclient.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_dutchclient.chat.ChatFilter;
import nl._99th_dutchclient.gui.screen.OptionsChatFiltersScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChatFilterList extends AbstractOptionList<ChatFilterList.Entry>
{
    private final OptionsChatFiltersScreen chatFiltersScreen;
    private List<ChatFilterEntry> entries = new ArrayList<>();

    public ChatFilterList(OptionsChatFiltersScreen chatFilters, Minecraft mcIn)
    {
        super(mcIn, chatFilters.width + 45, chatFilters.height, 43, chatFilters.height - 43, 25);
        this.chatFiltersScreen = chatFilters;
        this.loadFilters();
    }

    public void loadFilters() {
        ChatFilter chatfilter;
        this.clearEntries();
        for(ChatFilterEntry entry : this.entries) {
            this.chatFiltersScreen.children.remove(entry.btnToggleActivePlayer);
            this.chatFiltersScreen.children.remove(entry.btnToggleActiveChat);
            this.chatFiltersScreen.children.remove(entry.patternField);
        }
        this.entries.clear();

        for(int j = 0; j < this.minecraft.gameSettings.chatFilters.size(); j++)
        {
            chatfilter = this.minecraft.gameSettings.chatFilters.get(j);
            ITextComponent pattern = new TranslationTextComponent(chatfilter.pattern.pattern());

            ChatFilterEntry entry = new ChatFilterEntry(j, chatfilter, pattern);
            this.addEntry(entry);
            this.entries.add(entry);
        }
    }

    protected int getScrollbarPosition() { return this.chatFiltersScreen.width - 10; }

    public int getRowWidth()
    {
        return 400;
    }

    public abstract static class Entry extends AbstractOptionList.Entry<Entry>
    {
    }

    public class ChatFilterEntry extends Entry
    {
        private final ChatFilter chatFilter;
        private final int index;
        private final ITextComponent pattern;

        protected final Button btnToggleActivePlayer;
        protected final Button btnToggleActiveChat;
        protected final TextFieldWidget patternField;

        private ChatFilterEntry(int index, final ChatFilter chatFilter, final ITextComponent pattern)
        {
            this.index = index;
            this.chatFilter = chatFilter;
            this.pattern = pattern;

            this.patternField = new TextFieldWidget(
                    ChatFilterList.this.minecraft.fontRenderer,
                    ChatFilterList.this.chatFiltersScreen.width / 2 - 180,
                    65 + this.index * 25,
                    150,
                    20,
                    new TranslationTextComponent("99thdc.options.chatfilters.regex"))
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
                try{
                    Pattern p = Pattern.compile(p_214319_1_, Pattern.CASE_INSENSITIVE);
                    this.chatFilter.pattern = p;
                } catch (Exception ex) {}
            });
            ChatFilterList.this.chatFiltersScreen.children.add(this.patternField);

            this.btnToggleActivePlayer = new Button(ChatFilterList.this.chatFiltersScreen.width / 2 + 45, 65 + this.index * 25, 75, 20, this.chatFilter.activePlayer ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.chatFilter.activePlayer = !this.chatFilter.activePlayer;
                button.setMessage(this.chatFilter.activePlayer ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
                ChatFilterList.this.minecraft.gameSettings.setChatFilter(this.index, this.chatFilter);
            });
            ChatFilterList.this.chatFiltersScreen.children.add(this.btnToggleActivePlayer);

            this.btnToggleActiveChat = new Button(ChatFilterList.this.chatFiltersScreen.width / 2 + 130, 65 + this.index * 25, 75, 20, this.chatFilter.activeChat ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.chatFilter.activeChat = !this.chatFilter.activeChat;
                button.setMessage(this.chatFilter.activeChat ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
                ChatFilterList.this.minecraft.gameSettings.setChatFilter(this.index, this.chatFilter);
            });
            ChatFilterList.this.chatFiltersScreen.children.add(this.btnToggleActiveChat);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            this.patternField.y = p_230432_3_;
            this.patternField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnToggleActivePlayer.y = p_230432_3_;
            this.btnToggleActivePlayer.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnToggleActiveChat.y = p_230432_3_;
            this.btnToggleActiveChat.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
        }

        public List <? extends IGuiEventListener > getEventListeners()
        {
            return new ArrayList();
        }
    }
}
