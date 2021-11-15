package nl._99th_dutchclient.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.TranslationTextComponent;

public class OptionsChatScreen extends SettingsScreen
{
    private TextFieldWidget chatPrefixField;

    public OptionsChatScreen(Screen parentScreenIn, GameSettings gameSettingsIn)
    {
        super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("99thdc.options.chat.title"));
    }

    protected void init()
    {
        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6, 150, 20, AbstractOption.INFINITE_CHAT, AbstractOption.INFINITE_CHAT.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.INFINITE_CHAT.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.INFINITE_CHAT.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6, 150, 20, AbstractOption.DECODE_CHAT_MAGIC, AbstractOption.DECODE_CHAT_MAGIC.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.DECODE_CHAT_MAGIC.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.DECODE_CHAT_MAGIC.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 24, 150, 20, AbstractOption.CHAT_TIMESTAMP, AbstractOption.CHAT_TIMESTAMP.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.CHAT_TIMESTAMP.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.CHAT_TIMESTAMP.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 96, 150, 20, new TranslationTextComponent("99thdc.options.chattriggers.title"), (p_213052_1_) ->
        {
            this.minecraft.displayGuiScreen(new OptionsChatTriggersScreen(this, this.gameSettings));
        }));

        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96, 150, 20, new TranslationTextComponent("99thdc.options.chatfilters.title"), (p_213052_1_) ->
        {
            this.minecraft.displayGuiScreen(new OptionsChatFiltersScreen(this, this.gameSettings));
        }));

        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 120, 150, 20, new TranslationTextComponent("99thdc.options.eventtriggers"), (p_213055_1_) ->
        {
            this.minecraft.displayGuiScreen(new OptionsEventTriggersScreen(this, this.gameSettings));
        }));

        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 120, 150, 20, new TranslationTextComponent("99thdc.options.hotkeys"), (p_213055_1_) ->
        {
            this.minecraft.displayGuiScreen(new OptionsHotkeysScreen(this, this.gameSettings));
        }));

        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 144, 150, 20, new TranslationTextComponent("99thdc.options.customcommands.title"), (p_213055_1_) ->
        {
            this.minecraft.displayGuiScreen(new OptionsCustomCommandsScreen(this, this.gameSettings));
        }));

        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.minecraft.displayGuiScreen(this.parentScreen);
        }));

        this.chatPrefixField = new TextFieldWidget(this.font, this.width / 2 + 5, this.height / 6 + 48, 150, 20, new TranslationTextComponent("99thdc.options.chat.prefix"));
        this.chatPrefixField.setMaxStringLength(256);
        this.chatPrefixField.setText(Minecraft.getInstance().gameSettings.chatPrefix);
        this.chatPrefixField.setResponder((p_214319_1_) -> {
            Minecraft.getInstance().gameSettings.chatPrefix = p_214319_1_;
        });
        this.children.add(this.chatPrefixField);
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 15, 16777215);
        drawString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chat.prefix"), this.width / 2 - 149, this.height / 6 + 54, -1);
        this.chatPrefixField.render(matrixStack, mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
