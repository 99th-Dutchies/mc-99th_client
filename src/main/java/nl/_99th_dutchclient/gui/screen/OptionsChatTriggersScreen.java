package nl._99th_dutchclient.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_dutchclient.chat.ChatTrigger;
import nl._99th_dutchclient.gui.widget.ChatTriggerList;
import nl._99th_dutchclient.settings.ActiveAFK;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class OptionsChatTriggersScreen extends SettingsScreen
{
    private ChatTriggerList chatTriggerList;

    public OptionsChatTriggersScreen(Screen screen, GameSettings settings)
    {
        super(screen, settings, new TranslationTextComponent("99thdc.options.chattriggers.title"));
    }

    protected void init()
    {
        this.chatTriggerList = new ChatTriggerList(this, this.minecraft);
        this.chatTriggerList.updateSize(this.chatTriggerList.width, this.chatTriggerList.height - 15, this.chatTriggerList.y0 + 15, this.chatTriggerList.y1);
        this.children.add(this.chatTriggerList);

        this.removeEmpty();

        this.addButton(new Button(this.width / 2 - 155, this.height - 27, 150, 20, new TranslationTextComponent("99thdc.options.chattriggers.add"), (p_223703_1_) ->
        {
            this.minecraft.gameSettings.chatTriggers.add(new ChatTrigger("", "", ActiveAFK.OFF, 0, 0));
            this.chatTriggerList.loadTriggers();
        }));
        this.addButton(new Button(this.width / 2 + 5, this.height - 27, 150, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.removeEmpty();

            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    private void removeEmpty() {
        ArrayList<ChatTrigger> remove = new ArrayList<>();

        for(ChatTrigger ct : this.minecraft.gameSettings.chatTriggers){
            if(StringUtils.isBlank(ct.pattern.toString()) && StringUtils.isBlank(ct.response)) {
                remove.add(ct);
            }
        }

        for(ChatTrigger ctr : remove) {
            this.minecraft.gameSettings.chatTriggers.remove(ctr);
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        this.chatTriggerList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chattriggers.regex"), this.width / 2 - 150, 45, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chattriggers.response"), this.width / 2 - 25, 45, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chattriggers.delay"), this.width / 2 + 65, 45, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chattriggers.cooldown"), this.width / 2 + 125, 45, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
