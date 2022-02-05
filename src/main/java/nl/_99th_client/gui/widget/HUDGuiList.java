package nl._99th_client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.text.*;
import nl._99th_client._99thClientSettings;
import nl._99th_client.gui.screen.OptionsHUDGuiScreen;
import nl._99th_client.gui.screen.OptionsHUD_HUDSettingScreen;
import nl._99th_client.settings.HUDSetting;
import nl._99th_client.settings.HealthIndicator;
import nl._99th_client.settings.PotionIcons;
import nl._99th_client.settings.ShowToasts;

import java.util.ArrayList;
import java.util.List;

public class HUDGuiList extends AbstractOptionList<HUDGuiList.Entry>
{
    private final OptionsHUDGuiScreen optionsHUDGuiScreen;
    private int maxListLabelWidth;
    private List<HUDGuiList.HUDGuiEntry> entries = new ArrayList<>();

    public HUDGuiList(OptionsHUDGuiScreen optionsHUDGuiScreen, Minecraft mcIn)
    {
        super(mcIn, optionsHUDGuiScreen.width + 45, optionsHUDGuiScreen.height, 43, optionsHUDGuiScreen.height - 32, 25);
        this.optionsHUDGuiScreen = optionsHUDGuiScreen;

        this.loadHUDGui();
    }

    public void loadHUDGui() {
        ITextComponent itLocationHUD = new TranslationTextComponent("99thclient.options.LOCATION_HUD");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itLocationHUD));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itLocationHUD, this.minecraft.gameSettings._99thClientSettings.locationHUD));

        ITextComponent itArmourHUD = new TranslationTextComponent("99thclient.options.ARMOR_HUD");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itArmourHUD));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itArmourHUD, this.minecraft.gameSettings._99thClientSettings.inventoryHUDmain));

        ITextComponent itInventoryHUD = new TranslationTextComponent("99thclient.options.INVENTORY_COUNT_HUD");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itInventoryHUD));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itInventoryHUD, this.minecraft.gameSettings._99thClientSettings.inventoryHUDitems));

        ITextComponent itLookingHUD = new TranslationTextComponent("99thclient.options.LOOKING_HUD");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itLookingHUD));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itLookingHUD, this.minecraft.gameSettings._99thClientSettings.lookingHUD));

        ITextComponent itSystemHUD = new TranslationTextComponent("99thclient.options.SYSTEM_HUD");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itSystemHUD));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itSystemHUD, this.minecraft.gameSettings._99thClientSettings.systemHUD));

        ITextComponent itCPSHud = new TranslationTextComponent("99thclient.options.CPS_HUD");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itCPSHud));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itCPSHud, this.minecraft.gameSettings._99thClientSettings.cpsHUDleft, this.minecraft.gameSettings._99thClientSettings.cpsHUDright));

        ITextComponent itTablistPing = new TranslationTextComponent("99thclient.options.TABLIST_PING");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itTablistPing));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itTablistPing, "tablistPing"));

        ITextComponent itFullbrightness = new TranslationTextComponent("99thclient.options.FULL_BRIGHTNESS");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itFullbrightness));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itFullbrightness, "fullBrightness"));

        ITextComponent itBlockhighlight = new TranslationTextComponent("99thclient.options.BLOCK_HIGHLIGHT");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itBlockhighlight));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itBlockhighlight, "blockHighlight"));

        ITextComponent itTNTtimers = new TranslationTextComponent("99thclient.options.TNT_TIMER");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itTNTtimers));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itTNTtimers, "tntTimer"));

        ITextComponent itArmorBreakWarning = new TranslationTextComponent("99thclient.options.ARMOR_BREAK_WARNING");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itArmorBreakWarning));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itArmorBreakWarning, "armorBreakWarning"));

        ITextComponent itOutOfBlocksWarning = new TranslationTextComponent("99thclient.options.OUT_OF_BLOCKS_WARNING");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itOutOfBlocksWarning));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itOutOfBlocksWarning, "outOfBlocksWarning"));

        ITextComponent itPotiontimers = new TranslationTextComponent("99thclient.options.POTION_TIMER");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itPotiontimers));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itPotiontimers, "potionTimer"));

        ITextComponent itPotionIcons = new TranslationTextComponent("99thclient.options.POTION_ICONS");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itPotionIcons));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itPotionIcons, new Button(0, 0, 145, 20, new TranslationTextComponent(this.minecraft.gameSettings._99thClientSettings.potionIcons.func_238164_b_()), (button) -> {
            switch (this.minecraft.gameSettings._99thClientSettings.potionIcons)
            {
                case HIDE:
                    this.minecraft.gameSettings._99thClientSettings.potionIcons = PotionIcons.NORMAL;
                    break;

                case NORMAL:
                    this.minecraft.gameSettings._99thClientSettings.potionIcons = PotionIcons.ALL;
                    break;

                case ALL:
                    this.minecraft.gameSettings._99thClientSettings.potionIcons = PotionIcons.HIDE;
                    break;
            }
            button.setMessage(new TranslationTextComponent(this.minecraft.gameSettings._99thClientSettings.potionIcons.func_238164_b_()));
        })));

        ITextComponent itShowToasts = new TranslationTextComponent("99thclient.options.SHOW_TOASTS");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itShowToasts));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itShowToasts, new Button(0, 0, 145, 20, new TranslationTextComponent(this.minecraft.gameSettings._99thClientSettings.showToasts.func_238164_b_()), (button) -> {
            switch (this.minecraft.gameSettings._99thClientSettings.showToasts)
            {
                case OFF:
                    this.minecraft.gameSettings._99thClientSettings.showToasts = ShowToasts.SYSTEM;
                    break;

                case SYSTEM:
                    this.minecraft.gameSettings._99thClientSettings.showToasts = ShowToasts.ALL;
                    break;

                case ALL:
                    this.minecraft.gameSettings._99thClientSettings.showToasts = ShowToasts.OFF;
                    break;
            }
            button.setMessage(new TranslationTextComponent(this.minecraft.gameSettings._99thClientSettings.showToasts.func_238164_b_()));
        })));

        ITextComponent itHealthIndicator = new TranslationTextComponent("99thclient.options.HEALTH_INDICATOR");
        this.maxListLabelWidth = Math.max(this.maxListLabelWidth, this.minecraft.fontRenderer.getStringPropertyWidth(itHealthIndicator));
        this.addEntry(new HUDGuiList.HUDGuiEntry(itHealthIndicator, new Button(0, 0, 145, 20, new TranslationTextComponent(this.minecraft.gameSettings._99thClientSettings.healthIndicator.func_238164_b_()), (button) -> {
            switch (this.minecraft.gameSettings._99thClientSettings.healthIndicator)
            {
                case OFF:
                    this.minecraft.gameSettings._99thClientSettings.healthIndicator = HealthIndicator.NUMBERS;
                    break;

                case NUMBERS:
                    this.minecraft.gameSettings._99thClientSettings.healthIndicator = HealthIndicator.ICONS;
                    break;

                case ICONS:
                    this.minecraft.gameSettings._99thClientSettings.healthIndicator = HealthIndicator.OFF;
                    break;
            }
            button.setMessage(new TranslationTextComponent(this.minecraft.gameSettings._99thClientSettings.healthIndicator.func_238164_b_()));
        })));
    }

    protected int getScrollbarPosition()
    {
        return super.getScrollbarPosition() + 15;
    }

    public int getRowWidth()
    {
        return super.getRowWidth() + 32;
    }

    public abstract static class Entry extends AbstractOptionList.Entry<HUDGuiList.Entry>
    {
    }

    public class HUDGuiEntry extends HUDGuiList.Entry
    {
        private final ITextComponent title;
        private Button btnToggleActive;
        private Button btnMore;
        private HUDSetting hudSetting;
        private HUDSetting hudSettingSecondary;
        private Button iterableButton;
        private String settingName;
        private boolean isActive;

        private HUDGuiEntry(final ITextComponent p_i232281_3_, final HUDSetting hudSetting)
        {
            this.hudSetting = hudSetting;
            this.isActive = this.hudSetting.active;
            this.title = p_i232281_3_;

            this.btnToggleActive = new Button(0, 0, 70, 20, this.hudSetting.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                this.isActive = !this.isActive;
                this.hudSetting.active = this.isActive;
                button.setMessage(this.isActive ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
            });
            HUDGuiList.this.optionsHUDGuiScreen.children.add(this.btnToggleActive);

            this.btnMore = new Button(0, 0, 70, 20, new TranslationTextComponent("more"), (p_214387_2_) ->
            {
                if(this.hudSettingSecondary == null) {
                    HUDGuiList.this.minecraft.displayGuiScreen(new OptionsHUD_HUDSettingScreen(HUDGuiList.this.optionsHUDGuiScreen, HUDGuiList.this.minecraft.gameSettings, this.title, this.hudSetting));
                } else {
                    HUDGuiList.this.minecraft.displayGuiScreen(new OptionsHUD_HUDSettingScreen(HUDGuiList.this.optionsHUDGuiScreen, HUDGuiList.this.minecraft.gameSettings, this.title, this.hudSetting, this.hudSettingSecondary));
                }
            });
            HUDGuiList.this.optionsHUDGuiScreen.children.add(this.btnMore);
        }

        private HUDGuiEntry(final ITextComponent p_i232281_3_, final HUDSetting hudSetting, final HUDSetting hudSettingSecondary)
        {
            this.hudSetting = hudSetting;
            this.hudSettingSecondary = hudSettingSecondary;
            this.isActive = this.hudSetting.active || this.hudSettingSecondary.active;
            this.title = p_i232281_3_;

            this.btnToggleActive = new Button(0, 0, 70, 20, this.hudSetting.active ? (this.hudSetting.active && this.hudSettingSecondary.active ? new TranslationTextComponent("On") : new TranslationTextComponent("Partial")) : new TranslationTextComponent("Off"), (button) -> {
                this.isActive = !this.isActive;
                this.hudSetting.active = this.isActive;
                this.hudSettingSecondary.active = this.isActive;
                button.setMessage(this.isActive ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
            });
            HUDGuiList.this.optionsHUDGuiScreen.children.add(this.btnToggleActive);

            this.btnMore = new Button(0, 0, 70, 20, new TranslationTextComponent("more"), (p_214387_2_) ->
            {
                if(this.hudSettingSecondary == null) {
                    HUDGuiList.this.minecraft.displayGuiScreen(new OptionsHUD_HUDSettingScreen(HUDGuiList.this.optionsHUDGuiScreen, HUDGuiList.this.minecraft.gameSettings, this.title, this.hudSetting));
                } else {
                    HUDGuiList.this.minecraft.displayGuiScreen(new OptionsHUD_HUDSettingScreen(HUDGuiList.this.optionsHUDGuiScreen, HUDGuiList.this.minecraft.gameSettings, this.title, this.hudSetting, this.hudSettingSecondary));
                }
            });
            HUDGuiList.this.optionsHUDGuiScreen.children.add(this.btnMore);
        }

        private HUDGuiEntry(final ITextComponent p_i232281_3_, final String settingName)
        {
            this.settingName = settingName;
            this.title = p_i232281_3_;
            try {
                this.isActive = _99thClientSettings.class.getDeclaredField(this.settingName).getBoolean(Minecraft.getInstance().gameSettings._99thClientSettings);

                this.btnToggleActive = new Button(0, 0, 70, 20, isActive ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"), (button) -> {
                    isActive = !isActive;
                    try {
                        _99thClientSettings.class.getDeclaredField(this.settingName).setBoolean(Minecraft.getInstance().gameSettings._99thClientSettings, isActive);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    button.setMessage(isActive ? new TranslationTextComponent("On") : new TranslationTextComponent("Off"));
                });

                HUDGuiList.this.optionsHUDGuiScreen.children.add(this.btnToggleActive);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        private HUDGuiEntry(final ITextComponent p_i232281_3_, final Button iterableButton)
        {
            this.iterableButton = iterableButton;
            this.title = p_i232281_3_;

            HUDGuiList.this.optionsHUDGuiScreen.children.add(this.iterableButton);
        }

        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_)
        {
            HUDGuiList.this.minecraft.fontRenderer.func_243248_b(
                    p_230432_1_,
                    this.title,
                    (float)(HUDGuiList.this.minecraft.currentScreen.width / 2 - 180),
                    (float)(p_230432_3_ + p_230432_6_ - 9 - 1),
                    16777215);

            if(this.iterableButton != null) {
                this.iterableButton.x = p_230432_4_ + 115;
                this.iterableButton.y = p_230432_3_;
                this.iterableButton.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            } else {
                if (this.btnToggleActive != null) {
                    this.btnToggleActive.x = p_230432_4_ + 115;
                    this.btnToggleActive.y = p_230432_3_;
                    this.btnToggleActive.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
                }
                if (this.btnMore != null) {
                    this.btnMore.x = p_230432_4_ + 190;
                    this.btnMore.y = p_230432_3_;
                    this.btnMore.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
                }
            }
        }

        public List <? extends IGuiEventListener > getEventListeners()
        {
            return new ArrayList();
        }
    }
}