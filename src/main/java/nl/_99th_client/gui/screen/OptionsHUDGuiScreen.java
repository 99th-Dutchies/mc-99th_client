package nl._99th_client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_client.gui.widget.HUDGuiList;

public class OptionsHUDGuiScreen extends SettingsScreen
{
    /** The ID of the button that has been pressed. */
    public KeyBinding buttonId;
    public long time;
    private HUDGuiList hudGuiList;

    public OptionsHUDGuiScreen(Screen parentScreenIn, GameSettings gameSettingsIn)
    {
        super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("99thclient.options.hudgui.title"));
    }

    protected void init()
    {
        this.hudGuiList = new HUDGuiList(this, this.minecraft);
        this.children.add(this.hudGuiList);

        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        this.hudGuiList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
