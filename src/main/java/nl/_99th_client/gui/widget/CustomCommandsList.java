package nl._99th_client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.text.*;
import nl._99th_client.command.CustomCommand;
import nl._99th_client.gui.screen.OptionsCustomCommandsScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomCommandsList extends AbstractOptionList<CustomCommandsList.Entry>
{
    private final OptionsCustomCommandsScreen customCommandsScreen;
    private List<CustomCommandEntry> entries = new ArrayList<>();

    public CustomCommandsList(OptionsCustomCommandsScreen customCommands, Minecraft mcIn)
    {
        super(mcIn, customCommands.width + 45, customCommands.height, 43, customCommands.height - 43, 25);
        this.customCommandsScreen = customCommands;
        this.loadCustomCommands();
    }

    public void loadCustomCommands() {
        CustomCommand customCommand;
        this.clearEntries();
        for(CustomCommandEntry entry : this.entries) {
            this.customCommandsScreen.children.remove(entry.btnToggleActive);
            this.customCommandsScreen.children.remove(entry.btnRemove);
            this.customCommandsScreen.children.remove(entry.nameField);
            this.customCommandsScreen.children.remove(entry.responseField);
        }
        this.entries.clear();

        for(int j = 0; j < this.minecraft.gameSettings._99thClientSettings.customCommands.size(); j++)
        {
            customCommand = this.minecraft.gameSettings._99thClientSettings.customCommands.get(j);

            CustomCommandEntry entry = new CustomCommandEntry(j, customCommand);
            this.addEntry(entry);
            this.entries.add(entry);
        }
    }

    protected int getScrollbarPosition() { return this.customCommandsScreen.width - 10; }

    public int getRowWidth()
    {
        return 420;
    }

    public abstract static class Entry extends AbstractOptionList.Entry<Entry>
    {
    }

    public class CustomCommandEntry extends Entry
    {
        private final CustomCommand customCommand;
        private final int index;

        protected final Button btnToggleActive;
        protected final Button btnRemove;
        protected final TextFieldWidget nameField;
        protected final TextFieldWidget responseField;

        private CustomCommandEntry(int index, final CustomCommand customCommand)
        {
            this.index = index;
            this.customCommand = customCommand;

            this.nameField = new TextFieldWidget(
                    CustomCommandsList.this.minecraft.fontRenderer,
                    CustomCommandsList.this.customCommandsScreen.width / 2 - 155,
                    65 + this.index * 25,
                    120,
                    20,
                    new TranslationTextComponent("99thclient.options.customcommands.command"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.nameField.setMaxStringLength(256);
            this.nameField.setText(this.customCommand.name);
            this.nameField.setResponder((p_214319_1_) ->
            {
                this.customCommand.setName(p_214319_1_.toLowerCase(Locale.ROOT));
                CustomCommandsList.this.minecraft.commandManager.reloadCommands();
            });
            CustomCommandsList.this.customCommandsScreen.children.add(this.nameField);

            this.responseField = new TextFieldWidget(
                    CustomCommandsList.this.minecraft.fontRenderer,
                    CustomCommandsList.this.customCommandsScreen.width / 2 - 30,
                    65 + this.index * 25,
                    120,
                    20,
                    new TranslationTextComponent("99thclient.options.customcommands.response"))
            {
                protected IFormattableTextComponent getNarrationMessage()
                {
                    return super.getNarrationMessage().appendString(". ");
                }
            };
            this.responseField.setMaxStringLength(256);
            this.responseField.setText(this.customCommand.response);
            this.responseField.setResponder((p_214319_1_) ->
            {
                this.customCommand.response = p_214319_1_;
                CustomCommandsList.this.minecraft.commandManager.reloadCommands();
            });
            CustomCommandsList.this.customCommandsScreen.children.add(this.responseField);

            this.btnToggleActive = new Button(
                    CustomCommandsList.this.customCommandsScreen.width / 2 + 95, 0, 70, 20, this.customCommand.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.customCommand.active = !this.customCommand.active;
                button.setMessage(this.customCommand.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
                CustomCommandsList.this.minecraft.commandManager.reloadCommands();
            });
            CustomCommandsList.this.customCommandsScreen.children.add(this.btnToggleActive);

            this.btnRemove = new Button(CustomCommandsList.this.customCommandsScreen.width / 2 + 170, 65 + this.index * 25, 20, 20, new StringTextComponent("X"), (button) -> {
                CustomCommandsList ccl = CustomCommandsList.this;

                ccl.minecraft.gameSettings._99thClientSettings.removeCustomCommand(this.customCommand);
                ccl.loadCustomCommands();
                ccl.setScrollAmount(0);
            });
            CustomCommandsList.this.customCommandsScreen.children.add(this.btnRemove);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            IFormattableTextComponent backslash = new StringTextComponent("\\");
            int backslashWidth = CustomCommandsList.this.minecraft.fontRenderer.getStringPropertyWidth(backslash);

            CustomCommandsList.this.minecraft.fontRenderer.func_243248_b(
                    p_230432_1_,
                    backslash,
                    (float)(CustomCommandsList.this.minecraft.currentScreen.width / 2 - 160 - backslashWidth),
                    (float)(p_230432_3_ + p_230432_6_ - 9 - 6),
                    16777215);
            this.nameField.y = p_230432_3_;
            this.nameField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            this.responseField.y = p_230432_3_;
            this.responseField.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
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
