package nl._99th_client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_client.gui.widget.EventTriggerList;

public class OptionsChat_EventTriggersScreen extends SettingsScreen
{
    private EventTriggerList eventTriggerList;

    public OptionsChat_EventTriggersScreen(Screen screen, GameSettings settings)
    {
        super(screen, settings, new TranslationTextComponent("99thclient.options.eventtriggers"));
    }

    protected void init()
    {
        this.eventTriggerList = new EventTriggerList(this, this.minecraft);
        this.eventTriggerList.updateSize(this.eventTriggerList.width, this.eventTriggerList.height - 15, this.eventTriggerList.y0 + 15, this.eventTriggerList.y1);
        this.children.add(this.eventTriggerList);

        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        this.eventTriggerList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.chattriggers.response"), this.width / 2 + 20, 45, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.chattriggers.delay"), this.width / 2 + 110, 45, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
