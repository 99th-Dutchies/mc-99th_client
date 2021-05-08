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
    private static final AbstractOption[] OPTIONS = new AbstractOption[] {AbstractOption.SHOW_LOCATION_HUD,AbstractOption.SHOW_INVENTORY_HUD,AbstractOption.FULL_BRIGHTNESS};

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
