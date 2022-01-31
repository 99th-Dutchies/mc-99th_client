package nl._99th_client.gui.screen;

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
import nl._99th_client.util.MCStringUtils;

public class OptionsHUDGuiScreen extends SettingsScreen
{
    private TextFieldWidget itemHUDitemsField;

    public OptionsHUDGuiScreen(Screen parentScreenIn, GameSettings gameSettingsIn)
    {
        super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("99thclient.options.hudgui.title"));
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

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 24, 150, 20, AbstractOption.SHOW_LOOKING_HUD, AbstractOption.SHOW_LOOKING_HUD.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.SHOW_LOOKING_HUD.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.SHOW_LOOKING_HUD.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 48, 150, 20, AbstractOption.SHOW_SYSTEM_HUD, AbstractOption.SHOW_SYSTEM_HUD.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.SHOW_SYSTEM_HUD.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.SHOW_SYSTEM_HUD.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6 + 48, 150, 20, AbstractOption.SHOW_CPS_HUD, AbstractOption.SHOW_CPS_HUD.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.SHOW_CPS_HUD.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.SHOW_CPS_HUD.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(AbstractOption.SHOW_TOASTS.createWidget(this.minecraft.gameSettings,this.width / 2 - 155, this.height / 6 + 72, 150));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6 + 72, 150, 20, AbstractOption.TABLIST_PING, AbstractOption.TABLIST_PING.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.TABLIST_PING.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.TABLIST_PING.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 96, 150, 20, AbstractOption.FULL_BRIGHTNESS, AbstractOption.FULL_BRIGHTNESS.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.FULL_BRIGHTNESS.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.FULL_BRIGHTNESS.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(AbstractOption.HEALTH_INDICATOR.createWidget(this.minecraft.gameSettings,this.width / 2 + 5, this.height / 6 + 96, 150));

        this.addButton(AbstractOption.POTION_ICONS.createWidget(this.minecraft.gameSettings,this.width / 2 - 155, this.height / 6 + 120, 150));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6 + 120, 150, 20, AbstractOption.POTION_TIMER, AbstractOption.POTION_TIMER.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.POTION_TIMER.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.POTION_TIMER.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 144, 150, 20, AbstractOption.BLOCK_HIGHLIGHT, AbstractOption.BLOCK_HIGHLIGHT.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.BLOCK_HIGHLIGHT.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.BLOCK_HIGHLIGHT.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6 + 144, 150, 20, AbstractOption.TNT_TIMER, AbstractOption.TNT_TIMER.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.TNT_TIMER.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.TNT_TIMER.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 - 155, this.height / 6 + 168, 150, 20, AbstractOption.ARMOR_BREAK_WARNING, AbstractOption.ARMOR_BREAK_WARNING.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.ARMOR_BREAK_WARNING.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.ARMOR_BREAK_WARNING.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.addButton(new OptionButton(this.width / 2 + 5, this.height / 6 + 168, 150, 20, AbstractOption.OUT_OF_BLOCKS_WARNING, AbstractOption.OUT_OF_BLOCKS_WARNING.func_238152_c_(this.gameSettings), (p_213105_1_) ->
        {
            AbstractOption.OUT_OF_BLOCKS_WARNING.nextValue(this.minecraft.gameSettings);
            p_213105_1_.setMessage(AbstractOption.OUT_OF_BLOCKS_WARNING.func_238152_c_(this.minecraft.gameSettings));
            this.minecraft.gameSettings.saveOptions();
        }));

        this.itemHUDitemsField = new TextFieldWidget(this.font, this.width / 2 - 65, this.height / 6 + 192, 220, 20, new TranslationTextComponent("99thclient.options.ITEMS_HUD.items"));
        this.itemHUDitemsField.setMaxStringLength(1230);
        this.itemHUDitemsField.setText(MCStringUtils.itemsToString(Minecraft.getInstance().gameSettings._99thClientSettings.itemHUDitems));
        this.itemHUDitemsField.setResponder((p_214319_1_) -> {
            Minecraft.getInstance().gameSettings._99thClientSettings.itemHUDitems = MCStringUtils.parseItems(p_214319_1_);
        });
        this.children.add(this.itemHUDitemsField);

        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 15, 16777215);
        drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.ITEMS_HUD.items"), this.width / 2 - 149, this.height / 6 + 198, -1);
        this.itemHUDitemsField.render(matrixStack, mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
