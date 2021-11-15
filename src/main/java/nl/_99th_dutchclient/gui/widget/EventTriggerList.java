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
import nl._99th_dutchclient.chat.EventTrigger;
import nl._99th_dutchclient.gui.screen.OptionsEventTriggersScreen;
import nl._99th_dutchclient.util.MCStringUtils;

import java.util.ArrayList;
import java.util.List;

public class EventTriggerList extends AbstractOptionList<EventTriggerList.Entry>
{
    private final OptionsEventTriggersScreen eventTriggersScreen;
    private List<EventTriggerEntry> entries = new ArrayList<>();

    public EventTriggerList(OptionsEventTriggersScreen eventTriggers, Minecraft mcIn)
    {
        super(mcIn, eventTriggers.width + 45, eventTriggers.height, 43, eventTriggers.height - 43, 25);
        this.eventTriggersScreen = eventTriggers;
        this.loadTriggers();
    }

    public void loadTriggers() {
        EventTrigger eventTrigger;
        for(int j = 0; j < this.minecraft.gameSettings.eventTriggers.size(); j++)
        {
            eventTrigger = this.minecraft.gameSettings.eventTriggers.get(j);

            EventTriggerEntry entry = new EventTriggerEntry(j, eventTrigger);
            this.addEntry(entry);
            this.entries.add(entry);
        }
    }

    protected int getScrollbarPosition() { return this.eventTriggersScreen.width - 10; }

    public int getRowWidth()
    {
        return 420;
    }

    public abstract static class Entry extends AbstractOptionList.Entry<Entry>
    {
    }

    public class EventTriggerEntry extends Entry
    {
        private final EventTrigger eventTrigger;
        private final int index;

        protected final ITextComponent label;
        protected final Button btnToggleActive;
        protected final TextFieldWidget responseField;
        protected final TextFieldWidget delayField;

        private EventTriggerEntry(int index, final EventTrigger eventTrigger)
        {
            this.index = index;
            this.label = new TranslationTextComponent("99thdc.options.eventtriggers." + eventTrigger.trigger.getString());
            this.eventTrigger = eventTrigger;

            this.responseField = new TextFieldWidget(
                    EventTriggerList.this.minecraft.fontRenderer,
                    EventTriggerList.this.eventTriggersScreen.width / 2 - 40,
                    65 + this.index * 25,
                    120,
                    20,
                    new TranslationTextComponent("99thdc.options.eventtriggers.response"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.responseField.setMaxStringLength(256);
            this.responseField.setText(this.eventTrigger.response);
            this.responseField.setResponder((p_214319_1_) ->
            {
                this.eventTrigger.response = p_214319_1_;
            });
            EventTriggerList.this.eventTriggersScreen.children.add(this.responseField);

            this.delayField = new TextFieldWidget(
                    EventTriggerList.this.minecraft.fontRenderer,
                    EventTriggerList.this.eventTriggersScreen.width / 2 + 85,
                    65 + this.index * 25,
                    50,
                    20,
                    new TranslationTextComponent("99thdc.options.eventtriggers.delay"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.delayField.setMaxStringLength(256);
            this.delayField.setText(this.eventTrigger.delay + "");
            this.delayField.setResponder((p_214319_1_) ->
            {
                this.eventTrigger.delay = MCStringUtils.tryParse(p_214319_1_);
            });
            EventTriggerList.this.eventTriggersScreen.children.add(this.delayField);

            this.btnToggleActive = new Button(
                    EventTriggerList.this.eventTriggersScreen.width / 2 + 140, 0, 70, 20, this.eventTrigger.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.eventTrigger.active = !this.eventTrigger.active;
                button.setMessage(this.eventTrigger.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
            });
            EventTriggerList.this.eventTriggersScreen.children.add(this.btnToggleActive);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            EventTriggerList.this.minecraft.fontRenderer.func_243248_b(
                    p_230432_1_,
                    this.label,
                    (float)(EventTriggerList.this.minecraft.currentScreen.width / 2 - 180),
                    (float)(p_230432_3_ + p_230432_6_ - 9 - 1),
                    16777215);
            this.responseField.y = p_230432_3_;
            this.responseField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.delayField.y = p_230432_3_;
            this.delayField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnToggleActive.y = p_230432_3_;
            this.btnToggleActive.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
        }

        public List <? extends IGuiEventListener > getEventListeners()
        {
            return new ArrayList();
        }
    }
}
