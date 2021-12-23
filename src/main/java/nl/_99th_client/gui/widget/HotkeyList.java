package nl._99th_client.gui.widget;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.*;
import nl._99th_client.chat.Hotkey;
import nl._99th_client.gui.screen.OptionsHotkeysScreen;
import java.util.ArrayList;
import java.util.List;

public class HotkeyList extends AbstractOptionList<HotkeyList.Entry>
{
    private final OptionsHotkeysScreen optionsHotkeysScreen;
    private int maxListLabelWidth;
    private List<HotkeyList.HotkeyEntry> entries = new ArrayList<>();

    public HotkeyList(OptionsHotkeysScreen optionsHotkeysScreen, Minecraft mcIn)
    {
        super(mcIn, optionsHotkeysScreen.width + 45, optionsHotkeysScreen.height, 43, optionsHotkeysScreen.height - 32, 25);
        this.optionsHotkeysScreen = optionsHotkeysScreen;

        this.loadHotkeys();
    }

    public void loadHotkeys() {
        this.clearEntries();
        for(HotkeyList.HotkeyEntry entry : this.entries) {
            this.optionsHotkeysScreen.children.remove(entry.btnChangeKeyBinding);
            this.optionsHotkeysScreen.children.remove(entry.responseField);
            this.optionsHotkeysScreen.children.remove(entry.btnToggleActive);
            this.optionsHotkeysScreen.children.remove(entry.btnRemove);
        }
        this.entries.clear();

        for (Hotkey hotkey : this.minecraft.gameSettings._99thHotkeys)
        {
            ITextComponent itextcomponent = new TranslationTextComponent(hotkey.response);
            int i = this.minecraft.fontRenderer.getStringPropertyWidth(itextcomponent);

            if (i > this.maxListLabelWidth)
            {
                this.maxListLabelWidth = i;
            }

            this.addEntry(new HotkeyList.HotkeyEntry(hotkey, itextcomponent));
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

    public abstract static class Entry extends AbstractOptionList.Entry<HotkeyList.Entry>
    {
    }

    public class HotkeyEntry extends HotkeyList.Entry
    {
        private final Hotkey hotkey;
        private final ITextComponent keyDesc;
        private final Button btnChangeKeyBinding;
        private final TextFieldWidget responseField;
        private final Button btnToggleActive;
        private final Button btnRemove;

        private HotkeyEntry(final Hotkey hotkey, final ITextComponent p_i232281_3_)
        {
            this.hotkey = hotkey;
            this.keyDesc = p_i232281_3_;
            this.btnChangeKeyBinding = new Button(0, 0, 75, 20, p_i232281_3_, (p_214386_2_) ->
            {
                HotkeyList.this.optionsHotkeysScreen.buttonId = hotkey.keyBinding;
            })
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return hotkey.keyBinding.isInvalid() ? new TranslationTextComponent("narrator.controls.unbound", p_i232281_3_) : new TranslationTextComponent("narrator.controls.bound", p_i232281_3_, super.getNarrationMessage());
                }
            };
            HotkeyList.this.optionsHotkeysScreen.children.add(this.btnChangeKeyBinding);

            this.responseField = new TextFieldWidget(
                    HotkeyList.this.minecraft.fontRenderer,
                    0,
                    0,
                    160,
                    20,
                    new TranslationTextComponent("99thclient.options.chattriggers.response"));
            this.responseField.setMaxStringLength(256);
            this.responseField.setText(this.hotkey.response);
            this.responseField.setResponder((p_214319_1_) -> { this.hotkey.response = p_214319_1_; });
            HotkeyList.this.optionsHotkeysScreen.children.add(this.responseField);

            this.btnToggleActive = new Button(0, 0, 70, 20, this.hotkey.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.hotkey.active = !this.hotkey.active;
                button.setMessage(this.hotkey.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
            });
            HotkeyList.this.optionsHotkeysScreen.children.add(this.btnToggleActive);

            this.btnRemove = new Button(0, 0, 20, 20, new StringTextComponent("X"), (p_214387_2_) ->
            {
                HotkeyList hl = HotkeyList.this;

                hl.minecraft.gameSettings.removeHotkey(this.hotkey);
                hl.loadHotkeys();
                hl.setScrollAmount(0);
            });
            HotkeyList.this.optionsHotkeysScreen.children.add(this.btnRemove);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            boolean flag = HotkeyList.this.optionsHotkeysScreen.buttonId == this.hotkey.keyBinding;

            this.btnChangeKeyBinding.x = p_230432_4_ - 80;
            this.btnChangeKeyBinding.y = p_230432_3_;
            this.btnChangeKeyBinding.setMessage(this.hotkey.keyBinding.func_238171_j_());
            this.responseField.x = p_230432_4_ + 0;
            this.responseField.y = p_230432_3_;
            this.responseField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnToggleActive.x = p_230432_4_ + 165;
            this.btnToggleActive.y = p_230432_3_;
            this.btnToggleActive.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.btnRemove.x = p_230432_4_ + 240;
            this.btnRemove.y = p_230432_3_;
            this.btnRemove.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);

            boolean flag1 = false;

            if (!this.hotkey.keyBinding.isInvalid())
            {
                for (KeyBinding keybinding : HotkeyList.this.minecraft.gameSettings.keyBindings)
                {
                    if (keybinding != this.hotkey.keyBinding && this.hotkey.keyBinding.conflicts(keybinding))
                    {
                        flag1 = true;
                        break;
                    }
                }
            }

            if (flag)
            {
                this.btnChangeKeyBinding.setMessage((new StringTextComponent("> ")).append(this.btnChangeKeyBinding.getMessage().deepCopy().mergeStyle(TextFormatting.YELLOW)).appendString(" <").mergeStyle(TextFormatting.YELLOW));
            }
            else if (flag1)
            {
                this.btnChangeKeyBinding.setMessage(this.btnChangeKeyBinding.getMessage().deepCopy().mergeStyle(TextFormatting.RED));
            }

            this.btnChangeKeyBinding.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
        }

        public List <? extends IGuiEventListener > getEventListeners()
        {
            return ImmutableList.of(this.btnChangeKeyBinding, this.btnRemove);
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button)
        {
            if (this.btnChangeKeyBinding.mouseClicked(mouseX, mouseY, button))
            {
                return true;
            }
            else
            {
                return this.btnRemove.mouseClicked(mouseX, mouseY, button);
            }
        }

        public boolean mouseReleased(double mouseX, double mouseY, int button)
        {
            return this.btnChangeKeyBinding.mouseReleased(mouseX, mouseY, button) || this.btnRemove.mouseReleased(mouseX, mouseY, button);
        }
    }
}
