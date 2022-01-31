package nl._99th_client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_client.command.CustomCommand;
import nl._99th_client.gui.widget.CustomCommandsList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class OptionsCustomCommandsScreen extends SettingsScreen
{
    private CustomCommandsList customCommandsList;

    public OptionsCustomCommandsScreen(Screen screen, GameSettings settings)
    {
        super(screen, settings, new TranslationTextComponent("99thclient.options.customcommands.title"));
    }

    protected void init()
    {
        this.customCommandsList = new CustomCommandsList(this, this.minecraft);
        this.customCommandsList.updateSize(this.customCommandsList.width, this.customCommandsList.height - 15, this.customCommandsList.y0 + 15, this.customCommandsList.y1);
        this.children.add(this.customCommandsList);

        this.removeEmpty();

        this.addButton(new Button(this.width / 2 - 155, this.height - 27, 150, 20, new TranslationTextComponent("99thclient.options.customcommands.add"), (p_223703_1_) ->
        {
            this.minecraft.gameSettings._99thClientSettings.customCommands.add(new CustomCommand("", "", false));
            this.customCommandsList.loadCustomCommands();
        }));
        this.addButton(new Button(this.width / 2 + 5, this.height - 27, 150, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.removeEmpty();
            this.minecraft.commandManager.reloadCommands();
            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    private void removeEmpty() {
        ArrayList<CustomCommand> remove = new ArrayList<>();

        for(CustomCommand cc : this.minecraft.gameSettings._99thClientSettings.customCommands){
            if(StringUtils.isBlank(cc.name) && StringUtils.isBlank(cc.response)) {
                remove.add(cc);
            }
        }

        for(CustomCommand ccr : remove) {
            this.minecraft.gameSettings._99thClientSettings.customCommands.remove(ccr);
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        this.customCommandsList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.customcommands.command"), this.width / 2 - 95, 45, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.customcommands.response"), this.width / 2 + 30, 45, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
