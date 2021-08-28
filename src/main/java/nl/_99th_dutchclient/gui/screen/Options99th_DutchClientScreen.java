package nl._99th_dutchclient.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_dutchclient.Lang;

public class Options99th_DutchClientScreen extends SettingsScreen
{
    public Options99th_DutchClientScreen(Screen parentScreenIn, GameSettings gameSettingsIn)
    {
        super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("99thdc.options.title"));
    }

    protected void init()
    {
        this.addButton(new Button(this.width / 2 - 155, this.height / 6, 150, 20, new TranslationTextComponent(Lang.get("99thdc.options.hudgui")), (p_213055_1_) ->
        {
            this.minecraft.displayGuiScreen(new OptionsHUDGuiScreen(this, this.gameSettings));
        }));

        this.addButton(new Button(this.width / 2 + 5, this.height / 6, 150, 20, new TranslationTextComponent(Lang.get("99thdc.options.chat")), (p_213055_1_) ->
        {
            this.minecraft.displayGuiScreen(new OptionsChatScreen(this, this.gameSettings));
        }));

        this.addButton(AbstractOption.DISCORDRPC_SHOW_SERVER.createWidget(this.minecraft.gameSettings,this.width / 2 - 155, this.height / 6 + 48, 310));

        this.addButton(AbstractOption.TIME_TILL_AFK.createWidget(this.minecraft.gameSettings, this.width / 2 - 155, this.height / 6 + 72, 310));

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
