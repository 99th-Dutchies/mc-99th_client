package nl._99th_client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_client.chat.Hotkey;
import nl._99th_client.gui.widget.HotkeyList;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;

public class OptionsHotkeysScreen extends SettingsScreen
{
    /** The ID of the button that has been pressed. */
    public KeyBinding buttonId;
    public long time;
    private HotkeyList hotkeyList;

    public OptionsHotkeysScreen(Screen screen, GameSettings settings)
    {
        super(screen, settings, new TranslationTextComponent("99thclient.options.hotkeys"));
    }

    protected void init()
    {
        this.hotkeyList = new HotkeyList(this, this.minecraft);
        this.children.add(this.hotkeyList);

        this.removeEmpty();

        this.addButton(new Button(this.width / 2 - 155, this.height - 27, 150, 20, new TranslationTextComponent("99thclient.options.hotkeys.add"), (p_223703_1_) ->
        {
            KeyBinding kb = new KeyBinding("99thclient.hotkeys.hotkey" + this.minecraft.gameSettings._99thHotkeys.size(), -1, "key.categories.99thclienthotkeys");
            kb.bind(InputMappings.getInputByName("key.keyboard.unknown"));

            this.minecraft.gameSettings._99thHotkeys.add(new Hotkey(kb, "", false));
            this.hotkeyList.loadHotkeys();
        }));

        this.addButton(new Button(this.width / 2 + 5, this.height - 27, 150, 20, DialogTexts.GUI_DONE, (p_213124_1_) ->
        {
            this.removeEmpty();

            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    private void removeEmpty() {
        ArrayList<Hotkey> remove = new ArrayList<>();

        for(Hotkey hk : this.minecraft.gameSettings._99thHotkeys){
            if(hk.keyBinding.isDefault() && StringUtils.isBlank(hk.response)) {
                remove.add(hk);
            }
        }

        for(Hotkey hk : remove) {
            this.minecraft.gameSettings._99thHotkeys.remove(hk);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (this.buttonId != null)
        {
            this.gameSettings.setKeyBindingCode(this.buttonId, InputMappings.Type.MOUSE.getOrMakeInput(button));
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
            return true;
        }
        else
        {
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (this.buttonId != null)
        {
            if (keyCode == 256)
            {
                this.gameSettings.setKeyBindingCode(this.buttonId, InputMappings.INPUT_INVALID);
            }
            else
            {
                this.gameSettings.setKeyBindingCode(this.buttonId, InputMappings.getInputByCode(keyCode, scanCode));
            }

            this.buttonId = null;
            this.time = Util.milliTime();
            KeyBinding.resetKeyBindingArrayAndHash();
            return true;
        }
        else
        {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        this.hotkeyList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
