package nl._99th_client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_client.chat.ChatTrigger;
import nl._99th_client.gui.screen.OptionsChatTriggersScreen;
import nl._99th_client.util.MCStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChatTriggerList extends AbstractOptionList<ChatTriggerList.Entry>
{
    private final OptionsChatTriggersScreen chatTriggersScreen;
    private List<ChatTriggerEntry> entries = new ArrayList<>();

    public ChatTriggerList(OptionsChatTriggersScreen chatTriggers, Minecraft mcIn)
    {
        super(mcIn, chatTriggers.width + 45, chatTriggers.height, 43, chatTriggers.height - 43, 25);
        this.chatTriggersScreen = chatTriggers;
        this.loadTriggers();
    }

    public void loadTriggers() {
        ChatTrigger chattrigger;
        this.clearEntries();
        for(ChatTriggerEntry entry : this.entries) {
            this.chatTriggersScreen.children.remove(entry.btnToggleActive);
            this.chatTriggersScreen.children.remove(entry.btnRemove);
            this.chatTriggersScreen.children.remove(entry.patternField);
            this.chatTriggersScreen.children.remove(entry.responseField);
            this.chatTriggersScreen.children.remove(entry.delayField);
            this.chatTriggersScreen.children.remove(entry.cooldownField);
        }
        this.entries.clear();

        for(int j = 0; j < this.minecraft.gameSettings.chatTriggers.size(); j++)
        {
            chattrigger = this.minecraft.gameSettings.chatTriggers.get(j);
            ITextComponent pattern = new TranslationTextComponent(chattrigger.pattern.pattern());
            ITextComponent response = new TranslationTextComponent(chattrigger.response);

            ChatTriggerEntry entry = new ChatTriggerEntry(j, chattrigger, pattern, response);
            this.addEntry(entry);
            this.entries.add(entry);
        }
    }

    protected int getScrollbarPosition() { return this.chatTriggersScreen.width - 10; }

    public int getRowWidth()
    {
        return 420;
    }

    public abstract static class Entry extends AbstractOptionList.Entry<Entry>
    {
    }

    public class ChatTriggerEntry extends Entry
    {
        private final ChatTrigger chatTrigger;
        private final int index;
        private final ITextComponent pattern;
        private final ITextComponent response;

        protected final Button btnToggleActive;
        protected final Button btnRemove;
        protected final TextFieldWidget patternField;
        protected final TextFieldWidget responseField;
        protected final TextFieldWidget delayField;
        protected final TextFieldWidget cooldownField;

        private ChatTriggerEntry(int index, final ChatTrigger chatTrigger, final ITextComponent pattern, final ITextComponent response)
        {
            this.index = index;
            this.chatTrigger = chatTrigger;
            this.pattern = pattern;
            this.response = response;

            this.patternField = new TextFieldWidget(
                    ChatTriggerList.this.minecraft.fontRenderer,
                    ChatTriggerList.this.chatTriggersScreen.width / 2 - 210,
                    65 + this.index * 25,
                    120,
                    20,
                    new TranslationTextComponent("99thclient.options.chattriggers.regex"))
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
                    this.chatTrigger.pattern = p;
                } catch (Exception ex) {}
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.patternField);

            this.responseField = new TextFieldWidget(
                    ChatTriggerList.this.minecraft.fontRenderer,
                    ChatTriggerList.this.chatTriggersScreen.width / 2 - 85,
                    65 + this.index * 25,
                    120,
                    20,
                    new TranslationTextComponent("99thclient.options.chattriggers.response"))
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

            this.delayField = new TextFieldWidget(
                    ChatTriggerList.this.minecraft.fontRenderer,
                    ChatTriggerList.this.chatTriggersScreen.width / 2 + 40,
                    65 + this.index * 25,
                    50,
                    20,
                    new TranslationTextComponent("99thclient.options.chattriggers.delay"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.delayField.setMaxStringLength(256);
            this.delayField.setText(this.chatTrigger.delay + "");
            this.delayField.setResponder((p_214319_1_) ->
            {
                this.chatTrigger.delay = MCStringUtils.tryParse(p_214319_1_);
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.delayField);

            this.cooldownField = new TextFieldWidget(
                    ChatTriggerList.this.minecraft.fontRenderer,
                    ChatTriggerList.this.chatTriggersScreen.width / 2 + 100,
                    65 + this.index * 25,
                    50,
                    20,
                    new TranslationTextComponent("99thclient.options.chattriggers.cooldown"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.cooldownField.setMaxStringLength(256);
            this.cooldownField.setText(this.chatTrigger.cooldown + "");
            this.cooldownField.setResponder((p_214319_1_) ->
            {
                this.chatTrigger.cooldown = MCStringUtils.tryParse(p_214319_1_);
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.cooldownField);

            this.btnToggleActive = new Button(ChatTriggerList.this.chatTriggersScreen.width / 2 + 155, 65 + this.index * 25, 70, 20, new TranslationTextComponent(this.chatTrigger.active.func_238164_b_()), (button) -> {
                this.chatTrigger.active = this.chatTrigger.active.func_238166_c_();
                button.setMessage(new TranslationTextComponent(this.chatTrigger.active.func_238164_b_()));
                ChatTriggerList.this.minecraft.gameSettings.setChatTrigger(this.index, this.chatTrigger);
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.btnToggleActive);

            this.btnRemove = new Button(ChatTriggerList.this.chatTriggersScreen.width / 2 + 230, 65 + this.index * 25, 20, 20, new StringTextComponent("X"), (button) -> {
                ChatTriggerList ctl = ChatTriggerList.this;

                ctl.minecraft.gameSettings.removeChatTrigger(this.chatTrigger);
                ctl.loadTriggers();
                ctl.setScrollAmount(0);
            });
            ChatTriggerList.this.chatTriggersScreen.children.add(this.btnRemove);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            this.patternField.y = p_230432_3_;
            this.patternField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.responseField.y = p_230432_3_;
            this.responseField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.delayField.y = p_230432_3_;
            this.delayField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.cooldownField.y = p_230432_3_;
            this.cooldownField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnToggleActive.y = p_230432_3_;
            this.btnToggleActive.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnRemove.y = p_230432_3_;
            this.btnRemove.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
        }

        public List <? extends IGuiEventListener > getEventListeners()
        {
            return new ArrayList();
        }
    }
}
