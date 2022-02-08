package nl._99th_client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nl._99th_client.settings.HUDSetting;
import nl._99th_client.util.MCStringUtils;

public class OptionsHUD_HUDSettingScreen extends SettingsScreen
{
    private TranslationTextComponent title;
    private final HUDSetting hudSetting;
    private HUDSetting hudSettingSecondary;
    private HUDSetting hudSettingTertiary;

    private TextFieldWidget mainColorTFW;
    private Button dropShadowButton;
    private Button useDamageColorButton;

    public OptionsHUD_HUDSettingScreen(Screen parentScreenIn, GameSettings gameSettingsIn, TranslationTextComponent title, HUDSetting hudSetting)
    {
        super(parentScreenIn, gameSettingsIn, title);

        this.title = title;
        this.hudSetting = hudSetting;
    }

    public OptionsHUD_HUDSettingScreen(Screen parentScreenIn, GameSettings gameSettingsIn, TranslationTextComponent title, HUDSetting hudSetting, HUDSetting hudSettingSecondary)
    {
        super(parentScreenIn, gameSettingsIn, title);

        this.title = title;
        this.hudSetting = hudSetting;
        this.hudSettingSecondary = hudSettingSecondary;
    }

    public OptionsHUD_HUDSettingScreen(Screen parentScreenIn, GameSettings gameSettingsIn, TranslationTextComponent title, HUDSetting hudSetting, HUDSetting hudSettingSecondary, HUDSetting hudSettingTertiary)
    {
        super(parentScreenIn, gameSettingsIn, title);

        this.title = title;
        this.hudSetting = hudSetting;
        this.hudSettingSecondary = hudSettingSecondary;
        this.hudSettingTertiary = hudSettingTertiary;
    }

    protected void init()
    {
        this.initInput(this.hudSetting, this.hudSettingSecondary == null && this.hudSettingTertiary == null ? 120 : 70, 0);
        if(this.hudSettingSecondary != null) {
            this.initInput(this.hudSettingSecondary, 70, 75);
        }
        if(this.hudSettingTertiary != null) {
            this.initInput(this.hudSettingTertiary, 70, 150);
        }

        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, (p_223703_1_) ->
        {
            this.minecraft.displayGuiScreen(this.parentScreen);
        }));
    }

    private void initInput(HUDSetting setting, int inputWidth, int xOffset) {
        this.addButton(new Button(this.width / 2 + xOffset, this.height / 6, inputWidth, 20, setting.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
            setting.active = !setting.active;
            button.setMessage(setting.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
        }));

        int curY = 1;
        if(setting.type.hasItems()) {
            this.addButton(new Button(this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, new TranslationTextComponent("99thclient.options.HUDsetting.itemShow." + setting.itemShow.getString()), (button) -> {
                switch(setting.itemShow) {
                    case TEXT:
                        setting.itemShow = HUDSetting.ItemShow.ICON;
                        break;
                    default:
                    case ICON:
                        setting.itemShow = HUDSetting.ItemShow.TEXT;
                }
                button.setMessage(new TranslationTextComponent("99thclient.options.HUDsetting.itemShow." + setting.itemShow.getString()));
                this.mainColorTFW.setVisible(setting.itemShow == HUDSetting.ItemShow.TEXT);
                this.dropShadowButton.visible = setting.itemShow == HUDSetting.ItemShow.TEXT;
                this.useDamageColorButton.visible = setting.itemShow == HUDSetting.ItemShow.TEXT;
            }));
            curY++;
        }

        if(setting.type.hasColor()) {
            this.mainColorTFW = new TextFieldWidget(this.font, this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, new TranslationTextComponent("99thclient.options.HUDsetting.mainColor"));
            this.mainColorTFW.setMaxStringLength(7);
            this.mainColorTFW.setText(Color.fromInt(setting.mainColor).getHex());
            this.mainColorTFW.setVisible(setting.itemShow == HUDSetting.ItemShow.TEXT);
            this.mainColorTFW.setResponder((p_214319_1_) -> {
                Color c = Color.fromHex(p_214319_1_);
                if (c == null) {
                    setting.mainColor = -1;
                } else {
                    setting.mainColor = c.getColor();
                }
            });
            this.addButton(this.mainColorTFW);
            curY++;
        }

        if(setting.type.hasSubColor()) {
            TextFieldWidget subColorTFW = new TextFieldWidget(this.font, this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, new TranslationTextComponent("99thclient.options.HUDsetting.subColor"));
            subColorTFW.setMaxStringLength(7);
            subColorTFW.setText(Color.fromInt(setting.subColor).getHex());
            subColorTFW.setResponder((p_214319_1_) -> {
                Color c = Color.fromHex(p_214319_1_);
                if (c == null) {
                    setting.subColor = -1;
                } else {
                    setting.subColor = c.getColor();
                }
            });
            this.addButton(subColorTFW);
            curY++;
        }

        if(setting.type.hasColor()) {
            this.dropShadowButton = new Button(this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, setting.dropShadow ? new TranslationTextComponent("Yes") : new TranslationTextComponent("No"), (button) -> {
                setting.dropShadow = !setting.dropShadow;
                button.setMessage(setting.dropShadow ? new TranslationTextComponent("Yes") : new TranslationTextComponent("No"));
            });
            this.dropShadowButton.visible = setting.itemShow == HUDSetting.ItemShow.TEXT;
            this.addButton(this.dropShadowButton);
            curY++;
        }

        if(setting.type.hasItems()) {
            this.useDamageColorButton = new Button(this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, setting.useDamageColor ? new TranslationTextComponent("Yes") : new TranslationTextComponent("No"), (button) -> {
                setting.useDamageColor = !setting.useDamageColor;
                button.setMessage(setting.useDamageColor ? new TranslationTextComponent("Yes") : new TranslationTextComponent("No"));
            });
            this.useDamageColorButton.visible = setting.itemShow == HUDSetting.ItemShow.TEXT;
            this.addButton(this.useDamageColorButton);
            curY++;
        }

        if(setting.type.hasBracket()) {
            TextFieldWidget bracketColorTFW = new TextFieldWidget(this.font, this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, new TranslationTextComponent("99thclient.options.HUDsetting.bracketColor"));
            bracketColorTFW.setMaxStringLength(7);
            bracketColorTFW.setText(Color.fromInt(setting.bracketColor).getHex());
            bracketColorTFW.setResponder((p_214319_1_) -> {
                Color c = Color.fromHex(p_214319_1_);
                if (c == null) {
                    setting.bracketColor = -1;
                } else {
                    setting.bracketColor = c.getColor();
                }
            });
            this.addButton(bracketColorTFW);
            curY++;

            this.addButton(new Button(this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, setting.bracketType == HUDSetting.Bracket.NONE ? new TranslationTextComponent("gui.none") : new StringTextComponent(setting.bracketType.open() + " " + setting.bracketType.close()), (button) -> {
                switch(setting.bracketType) {
                    case SQUARE:
                        setting.bracketType = HUDSetting.Bracket.ROUND;
                        break;
                    case ROUND:
                        setting.bracketType = HUDSetting.Bracket.ANGLE;
                        break;
                    case ANGLE:
                        setting.bracketType = HUDSetting.Bracket.CURLY;
                        break;
                    case CURLY:
                        setting.bracketType = HUDSetting.Bracket.NONE;
                        break;
                    default:
                    case NONE:
                        setting.bracketType = HUDSetting.Bracket.SQUARE;
                        break;
                }
                button.setMessage(setting.bracketType == HUDSetting.Bracket.NONE ? new TranslationTextComponent("gui.none") : new StringTextComponent(setting.bracketType.open() + " " + setting.bracketType.close()));
            }));
            curY++;
        }

        if(setting.type.hasPosition()) {
            TextFieldWidget xPosTFW = new TextFieldWidget(this.font, this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, new TranslationTextComponent("99thclient.options.HUDsetting.xPos"));
            xPosTFW.setMaxStringLength(7);
            xPosTFW.setText(setting.x + "");
            xPosTFW.setResponder((p_214319_1_) -> {
                setting.x = MCStringUtils.tryParse(p_214319_1_);
            });
            this.addButton(xPosTFW);
            curY++;

            TextFieldWidget yPosTFW = new TextFieldWidget(this.font, this.width / 2 + xOffset, this.height / 6 + curY * 24, inputWidth, 20, new TranslationTextComponent("99thclient.options.HUDsetting.yPos"));
            yPosTFW.setMaxStringLength(7);
            yPosTFW.setText(setting.y + "");
            yPosTFW.setResponder((p_214319_1_) -> {
                setting.y = MCStringUtils.tryParse(p_214319_1_);
            });
            this.addButton(yPosTFW);
            curY++;
        }

        if(this.title.getKey().equals("99thclient.options.INVENTORY_COUNT_HUD")) {
            TextFieldWidget itemHUDitemsTFW = new TextFieldWidget(this.font, this.width / 2 + xOffset, this.height / 6 + curY * 24, 220, 20, new TranslationTextComponent("99thclient.options.ITEMS_HUD.items"));
            itemHUDitemsTFW.setMaxStringLength(1230);
            itemHUDitemsTFW.setText(MCStringUtils.itemsToString(Minecraft.getInstance().gameSettings._99thClientSettings.itemHUDitems));
            itemHUDitemsTFW.setResponder((p_214319_1_) -> {
                Minecraft.getInstance().gameSettings._99thClientSettings.itemHUDitems = MCStringUtils.parseItems(p_214319_1_);
            });
            this.addButton(itemHUDitemsTFW);
            curY++;
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);

        if(this.hudSettingTertiary != null) {
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.SYSTEM_HUD.fps"), this.width / 2 + 35, this.height / 6 - 18, 16777215);
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.SYSTEM_HUD.memory"), this.width / 2 + 110, this.height / 6 - 18, 16777215);
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.SYSTEM_HUD.ping"), this.width / 2 + 185, this.height / 6 - 18, 16777215);
        } else if(this.hudSettingSecondary != null) {
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("key.mouse.left"), this.width / 2 + 35, this.height / 6 - 18, 16777215);
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("key.mouse.right"), this.width / 2 + 110, this.height / 6 - 18, 16777215);
        }

        drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.active"), this.width / 2 - 149, this.height / 6 + 6, -1);
        int curY = 1;
        if(this.hudSetting.type.hasItems() || (this.hudSettingSecondary != null && this.hudSettingSecondary.type.hasItems())) {
            drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.itemShow"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            curY++;
        }
        if (this.hudSetting.type.hasColor() || (this.hudSettingSecondary != null && this.hudSettingSecondary.type.hasColor())) {
            if(this.hudSetting.itemShow == HUDSetting.ItemShow.TEXT || (this.hudSettingSecondary != null && this.hudSettingSecondary.itemShow == HUDSetting.ItemShow.TEXT)) {
                drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.mainColor"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            }
            curY++;
        }
        if(this.hudSetting.type.hasSubColor() || (this.hudSettingSecondary != null && this.hudSettingSecondary.type.hasSubColor())) {
            drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.subColor"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            curY++;
        }
        if (this.hudSetting.type.hasColor() || (this.hudSettingSecondary != null && this.hudSettingSecondary.type.hasColor())) {
            if(this.hudSetting.itemShow == HUDSetting.ItemShow.TEXT || (this.hudSettingSecondary != null && this.hudSettingSecondary.itemShow == HUDSetting.ItemShow.TEXT)) {
                drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.dropShadow"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            }
            curY++;
        }
        if(this.hudSetting.type.hasItems() || (this.hudSettingSecondary != null && this.hudSettingSecondary.type.hasItems())) {
            if(this.hudSetting.itemShow == HUDSetting.ItemShow.TEXT || (this.hudSettingSecondary != null && this.hudSettingSecondary.itemShow == HUDSetting.ItemShow.TEXT)) {
                drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.useDamageColor"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            }
            curY++;
        }
        if(this.hudSetting.type.hasBracket() || (this.hudSettingSecondary != null && this.hudSettingSecondary.type.hasBracket())) {
            drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.bracketColor"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            curY++;

            drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.bracketType"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            curY++;
        }
        if(this.hudSetting.type.hasPosition() || (this.hudSettingSecondary != null && this.hudSettingSecondary.type.hasPosition())) {
            drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.xPos"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            curY++;

            drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.HUDsetting.yPos"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
            curY++;
        }
        if(this.title.getKey().equals("99thclient.options.INVENTORY_COUNT_HUD")) {
            drawString(matrixStack, this.font, new TranslationTextComponent("99thclient.options.ITEMS_HUD.items"), this.width / 2 - 149, this.height / 6 + 6 + curY * 24, -1);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
