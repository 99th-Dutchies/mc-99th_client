package nl._99th_dutchclient.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_dutchclient.chat.ChatFilter;
import nl._99th_dutchclient.gui.widget.ChatFilterList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class OptionsChatFiltersScreen extends SettingsScreen
{
    private ChatFilterList chatFilterList;

    public OptionsChatFiltersScreen(Screen screen, GameSettings settings)
    {
        super(screen, settings, new TranslationTextComponent("99thdc.options.chatfilters.title"));
    }

    protected void init()
    {
        this.chatFilterList = new ChatFilterList(this, this.minecraft);
        this.chatFilterList.updateSize(this.chatFilterList.width, this.chatFilterList.height - 15, this.chatFilterList.y0 + 15, this.chatFilterList.y1);
        this.children.add(this.chatFilterList);

        this.removeEmpty();

        this.addButton(new Button(this.width / 2 - 155, this.height - 27, 150, 20, new TranslationTextComponent("99thdc.options.chatfilters.add"), (p_223703_1_) ->
        {
            this.minecraft.gameSettings.chatFilters.add(new ChatFilter("", false, false));
            this.chatFilterList.loadFilters();
        }));
        this.addButton(new Button(this.width / 2 + 5, this.height - 27, 150, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.removeEmpty();

            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    private void removeEmpty() {
        ArrayList<ChatFilter> remove = new ArrayList<>();

        for(ChatFilter cf : this.minecraft.gameSettings.chatFilters){
            if(StringUtils.isBlank(cf.pattern.toString())) {
                remove.add(cf);
            }
        }

        for(ChatFilter cfr : remove) {
            this.minecraft.gameSettings.chatFilters.remove(cfr);
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        this.chatFilterList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chatfilters.regex"), this.width / 2 - 105, 50, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chatfilters.activePlayer"), this.width / 2 + 80, 50, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thdc.options.chatfilters.activeChat"), this.width / 2 + 165, 50, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
