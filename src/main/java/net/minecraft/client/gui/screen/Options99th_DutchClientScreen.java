package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_dutchclient.Lang;

public class Options99th_DutchClientScreen extends SettingsScreen
{
    private static final AbstractOption[] OPTIONS = new AbstractOption[] {AbstractOption.SHOW_LOCATION_HUD,AbstractOption.SHOW_INVENTORY_HUD,AbstractOption.FULL_BRIGHTNESS,AbstractOption.INFINITE_CHAT};

    public Options99th_DutchClientScreen(Screen parentScreenIn, GameSettings gameSettingsIn)
    {
        super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("99thdc.options.title"));
    }

    protected void init()
    {
        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6, 150, 20, AbstractOption.SHOW_LOCATION_HUD, AbstractOption.SHOW_LOCATION_HUD.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.SHOW_LOCATION_HUD.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.SHOW_LOCATION_HUD.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6, 150, 20, AbstractOption.SHOW_INVENTORY_HUD, AbstractOption.SHOW_INVENTORY_HUD.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.SHOW_INVENTORY_HUD.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.SHOW_INVENTORY_HUD.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 24, 150, 20, AbstractOption.FULL_BRIGHTNESS, AbstractOption.FULL_BRIGHTNESS.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.FULL_BRIGHTNESS.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.FULL_BRIGHTNESS.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6 + 24, 150, 20, AbstractOption.INFINITE_CHAT, AbstractOption.INFINITE_CHAT.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.INFINITE_CHAT.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.INFINITE_CHAT.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 48, 150, 20, AbstractOption.DISCORDRPC_SHOW_SERVER, AbstractOption.DISCORDRPC_SHOW_SERVER.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.DISCORDRPC_SHOW_SERVER.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.DISCORDRPC_SHOW_SERVER.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6 + 48, 150, 20, AbstractOption.TABLIST_PING, AbstractOption.TABLIST_PING.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.TABLIST_PING.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.TABLIST_PING.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new Button(this.width / 2 + 5, this.height - 51, 150, 20, new TranslationTextComponent("99thdc.options.chattriggers.title"), (p_213052_1_) ->
        {
            this.minecraft.displayGuiScreen(
                    new ChatTriggersScreen(
                            this,
                            this.minecraft.gameSettings));
        }));

        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 15, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
