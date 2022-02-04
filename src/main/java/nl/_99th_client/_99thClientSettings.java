package nl._99th_client;

import com.google.common.collect.Lists;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.optifine.util.KeyUtils;
import nl._99th_client.chat.ChatFilter;
import nl._99th_client.chat.ChatTrigger;
import nl._99th_client.chat.EventTrigger;
import nl._99th_client.chat.Hotkey;
import nl._99th_client.command.CustomCommand;
import nl._99th_client.installer.Utils;
import nl._99th_client.settings.*;
import nl._99th_client.util.MCStringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONWriter;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class _99thClientSettings {
    private static final Logger LOGGER = LogManager.getLogger();
    protected Minecraft mc;
    private File mcDataDir;
    private File optionsFile99thclient;

    public KeyBinding keyBindFreelook;
    public KeyBinding keyBindCommand;
    public KeyBinding[] keyBindings;
    public HUDSetting locationHUD = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.SQUARE, 1, 1, 0);
    public HUDSetting inventoryHUDmain = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, 1, 51, 0);
    public HUDSetting inventoryHUDitems = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, 78, 19, 0);
    public HUDSetting systemHUD = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.SQUARE, 91, 1, 0);
    public HUDSetting cpsHUDleft = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, -125, 15, 0);
    public HUDSetting cpsHUDright = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, 96, 15, 0);
    public HUDSetting lookingHUD = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, 1, 126, 0);
    public List<Item> itemHUDitems = Lists.newArrayList(Items.ARROW, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND, Items.EMERALD);
    public boolean fullBrightness = false;
    public boolean infiniteChat = true;
    public boolean showChatTimestamp = false;
    public DiscordShowRPC discordrpcShowServer = DiscordShowRPC.SERVER;
    public HealthIndicator healthIndicator = HealthIndicator.OFF;
    public ShowToasts showToasts = ShowToasts.ALL;
    public PotionIcons potionIcons = PotionIcons.NORMAL;
    public boolean tntTimer = true;
    public boolean potionTimer = true;
    public boolean armorBreakWarning = false;
    public boolean outOfBlocksWarning = false;
    public boolean tablistPing = false;
    public boolean decodeChatMagic = false;
    public boolean blockHighlight = false;
    public boolean resourcepackOptimization = false;
    public boolean dataCollection = true;
    public int timeTillAFK = 0;
    public String chatPrefix = "";
    public boolean chatPrefixEnabled = false;
    public List<ChatTrigger> chatTriggers = Lists.newArrayList(
            new ChatTrigger("\\s?(\\w*)(?:\\shas activated).*", "/thanks $1", ActiveAFK.OFF, 0, 0)
    );
    public List<ChatFilter> chatFilters = Lists.newArrayList();
    public List<Hotkey> hotkeys = Lists.newArrayList();
    public List<EventTrigger> eventTriggers = Lists.newArrayList(
            new EventTrigger(EventTrigger.Event.WORLD_JOIN, "", false, 0),
            new EventTrigger(EventTrigger.Event.SERVER_JOIN, "", false, 0)
    );
    public List<CustomCommand> customCommands = Lists.newArrayList();

    public _99thClientSettings(Minecraft mcIn, File mcDataDir)
    {
        this.mc = mcIn;
        this.mcDataDir = mcDataDir;
        this.optionsFile99thclient = new File(mcDataDir, nl._99th_client.Config.configFile);

        this.keyBindFreelook = new KeyBinding("99thclient.key.freelook", 71, "key.categories.misc");
        this.keyBindCommand = new KeyBinding("99thclient.key.command", 92, "key.categories.multiplayer");
        this.keyBindings = ArrayUtils.add(this.keyBindings, this.keyBindFreelook);
        this.keyBindings = ArrayUtils.add(this.keyBindings, this.keyBindCommand);
    }

    public void load99thclientSettings(GameSettings gameSettings) {
        if(!optionsFile99thclient.exists()) {
            this.load99thclientSettingsFromTXT(this.mcDataDir, gameSettings);
            this.saveSettings();
        } else {
            this.load99thclientSettingsFromJSON(gameSettings);
        }
    }

    public void load99thclientSettingsFromTXT(File mcDataDir, GameSettings gameSettings)
    {
        boolean didResetChatTriggers = false;
        boolean didResetChatFilters = false;
        boolean didResetEventTriggers = false;
        boolean didResetHotkeys = false;
        boolean didResetCustomCommands = false;
        try
        {
            for(String oldFile : nl._99th_client.Config.oldConfigFiles) {
                File textFile = new File(mcDataDir, oldFile);

                if (!textFile.exists()) {
                    continue;
                }

                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), StandardCharsets.UTF_8));
                String s = "";

                while ((s = bufferedreader.readLine()) != null) {
                    try {
                        String[] astring = s.split("<:>");

                        if (astring[0].equals("showLocationHUD") && astring.length >= 2) {
                            this.locationHUD = new HUDSetting(Boolean.valueOf(astring[1]), -1, -1, -1, HUDSetting.Bracket.SQUARE, 1, 1, 1);
                        }

                        if (astring[0].equals("showInventoryHUD") && astring.length >= 2) {
                            this.inventoryHUDmain = new HUDSetting(Boolean.valueOf(astring[1]), -1, -1, -1, HUDSetting.Bracket.NONE, 1, 51, 0);
                            this.inventoryHUDitems = new HUDSetting(Boolean.valueOf(astring[1]), -1, -1, -1, HUDSetting.Bracket.NONE, 78, 19, 0);
                        }

                        if (astring[0].equals("showSystemHUD") && astring.length >= 2) {
                            this.systemHUD = new HUDSetting(Boolean.valueOf(astring[1]), -1, -1, -1, HUDSetting.Bracket.SQUARE, 91, 1, 0);
                        }

                        if (astring[0].equals("showCPSHUD") && astring.length >= 2) {
                            this.cpsHUDleft = new HUDSetting(Boolean.valueOf(astring[1]), -1, -1, -1, HUDSetting.Bracket.NONE, -125, 15, 0);
                            this.cpsHUDright = new HUDSetting(Boolean.valueOf(astring[1]), -1, -1, -1, HUDSetting.Bracket.NONE, 96, 15, 0);
                        }

                        if (astring[0].equals("showLookingHUD") && astring.length >= 2) {
                            this.lookingHUD = new HUDSetting(Boolean.valueOf(astring[1]), -1, -1, -1, HUDSetting.Bracket.NONE, 1, 126, 0);
                        }

                        if (astring[0].equals("itemHUDitems") && astring.length >= 2) {
                            this.itemHUDitems = MCStringUtils.parseItems(astring[1]);
                        }

                        if (astring[0].equals("fullBrightness") && astring.length >= 2) {
                            this.fullBrightness = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("infiniteChat") && astring.length >= 2) {
                            this.infiniteChat = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("showChatTimestamp") && astring.length >= 2) {
                            this.showChatTimestamp = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("discordrpcShowServer") && astring.length >= 2) {
                            switch (astring[1]) {
                                case "0":
                                    this.discordrpcShowServer = DiscordShowRPC.OFF;
                                    break;
                                case "3":
                                    this.discordrpcShowServer = DiscordShowRPC.GAME;
                                    break;
                                case "4":
                                    this.discordrpcShowServer = DiscordShowRPC.MAP;
                                    break;
                                case "1":
                                    this.discordrpcShowServer = DiscordShowRPC.PLAYING;
                                    break;
                                case "2":
                                default:
                                    this.discordrpcShowServer = DiscordShowRPC.SERVER;
                                    break;
                            }
                        }

                        if (astring[0].equals("healthIndicator") && astring.length >= 2) {
                            switch (astring[1]) {
                                case "1":
                                    this.healthIndicator = HealthIndicator.NUMBERS;
                                    break;
                                case "2":
                                    this.healthIndicator = HealthIndicator.ICONS;
                                    break;
                                case "0":
                                default:
                                    this.healthIndicator = HealthIndicator.OFF;
                                    break;
                            }
                        }

                        if (astring[0].equals("showToasts") && astring.length >= 2) {
                            switch (astring[1]) {
                                case "0":
                                    this.showToasts = ShowToasts.OFF;
                                    break;
                                case "1":
                                    this.showToasts = ShowToasts.SYSTEM;
                                    break;
                                case "2":
                                default:
                                    this.showToasts = ShowToasts.ALL;
                                    break;
                            }
                        }

                        if (astring[0].equals("potionIcons") && astring.length >= 2) {
                            switch (astring[1]) {
                                case "0":
                                    this.potionIcons = PotionIcons.HIDE;
                                    break;
                                case "2":
                                    this.potionIcons = PotionIcons.ALL;
                                    break;
                                case "1":
                                default:
                                    this.potionIcons = PotionIcons.NORMAL;
                                    break;
                            }
                        }

                        if (astring[0].equals("tntTimer") && astring.length >= 2) {
                            this.tntTimer = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("potionTimer") && astring.length >= 2) {
                            this.potionTimer = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("armorBreakWarning") && astring.length >= 2) {
                            this.armorBreakWarning = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("outOfBlocksWarning") && astring.length >= 2) {
                            this.outOfBlocksWarning = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("tablistPing") && astring.length >= 2) {
                            this.tablistPing = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("decodeChatMagic") && astring.length >= 2) {
                            this.decodeChatMagic = Boolean.valueOf(astring[1]);
                            if (this.mc.fontRenderer != null) {
                                this.mc.fontRenderer.setDecodeChatMagic(this.decodeChatMagic);
                            }
                        }

                        if (astring[0].equals("blockHighlight") && astring.length >= 2) {
                            this.blockHighlight = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("resourcepackOptimization") && astring.length >= 2) {
                            this.resourcepackOptimization = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("dataCollection") && astring.length >= 2) {
                            this.dataCollection = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("timeTillAFK") && astring.length >= 2) {
                            this.timeTillAFK = MCStringUtils.tryParse(astring[1]);
                        }

                        if (astring[0].equals("chatPrefix") && astring.length >= 2) {
                            this.chatPrefix = astring[1];
                        }

                        if (astring[0].equals("chatPrefixEnabled") && astring.length >= 2) {
                            this.chatPrefixEnabled = Boolean.valueOf(astring[1]);
                        }

                        if (astring[0].equals("chatTrigger") && astring.length >= 2) {
                            if (!didResetChatTriggers) {
                                this.chatTriggers = Lists.newArrayList();
                                didResetChatTriggers = true;
                            }

                            if (astring.length == 3) {
                                this.chatTriggers.add(new ChatTrigger(astring[1], astring[2], ActiveAFK.ALWAYS, 0, 0));
                            } else if (astring.length >= 4) {
                                ActiveAFK state = ActiveAFK.OFF;
                                switch (astring[3]) {
                                    case "1":
                                        state = ActiveAFK.ALWAYS;
                                        break;
                                    case "2":
                                        state = ActiveAFK.AFKONLY;
                                        break;
                                    case "3":
                                        state = ActiveAFK.ACTIVEONLY;
                                        break;
                                    case "0":
                                    default:
                                        state = ActiveAFK.OFF;
                                        break;
                                }

                                if (astring.length == 4) {
                                    this.chatTriggers.add(new ChatTrigger(astring[1], astring[2], state, 0, 0));
                                } else if (astring.length == 5) {
                                    this.chatTriggers.add(new ChatTrigger(astring[1], astring[2], state, MCStringUtils.tryParse(astring[4]), 0));
                                } else if (astring.length == 6) {
                                    this.chatTriggers.add(new ChatTrigger(astring[1], astring[2], state, MCStringUtils.tryParse(astring[4]), MCStringUtils.tryParse(astring[5])));
                                }
                            }
                        }

                        if (astring[0].equals("chatFilter") && astring.length >= 2) {
                            if (!didResetChatFilters) {
                                this.chatFilters = Lists.newArrayList();
                                didResetChatFilters = true;
                            }

                            this.chatFilters.add(new ChatFilter(astring[1], Boolean.valueOf(astring[2]), Boolean.valueOf(astring[3])));
                        }

                        if (astring[0].equals("eventTrigger") && astring.length >= 2) {
                            if (!didResetEventTriggers) {
                                this.eventTriggers = Lists.newArrayList();
                                didResetEventTriggers = true;
                            }

                            EventTrigger.Event trigger = null;

                            switch (astring[1]) {
                                case "server_join":
                                    trigger = EventTrigger.Event.SERVER_JOIN;
                                    break;
                                case "world_join":
                                    trigger = EventTrigger.Event.WORLD_JOIN;
                                    break;
                            }

                            this.eventTriggers.add(new EventTrigger(trigger, astring[2], Boolean.valueOf(astring[3]), MCStringUtils.tryParse(astring[4])));
                        }

                        if (astring[0].equals("customCommand") && astring.length >= 2) {
                            if (!didResetCustomCommands) {
                                this.customCommands = Lists.newArrayList();
                                this.mc.commandManager.reloadCommands(false);
                                didResetCustomCommands = true;
                            }

                            CustomCommand command = new CustomCommand(astring[1], astring[2], Boolean.valueOf(astring[3]));
                            this.customCommands.add(command);
                            this.mc.commandManager.loadCommand(command);
                        }

                        if (astring[0].equals("hotkey") && astring.length == 4) {
                            if (!didResetHotkeys) {
                                this.hotkeys = Lists.newArrayList();
                                didResetHotkeys = true;
                            }

                            KeyBinding kb = new KeyBinding("99thclient.hotkeys.hotkey" + this.hotkeys.size(), -1, "key.categories.99thclienthotkeys");
                            kb.bind(InputMappings.getInputByName(astring[1]));

                            this.hotkeys.add(new Hotkey(kb, astring[2], Boolean.valueOf(astring[3])));
                        }

                        if (astring[0].equals("key_" + this.keyBindFreelook.getKeyDescription())) {
                            this.keyBindFreelook.bind(InputMappings.getInputByName(astring[1]));
                        }
                        if (astring[0].equals("key_" + this.keyBindCommand.getKeyDescription())) {
                            this.keyBindCommand.bind(InputMappings.getInputByName(astring[1]));
                        }
                    } catch (Exception exception1) {
                        LOGGER.error("Skipping bad option: " + s);
                        exception1.printStackTrace();
                    }
                }

                KeyUtils.fixKeyConflicts(gameSettings.keyBindings, new KeyBinding[]{this.keyBindFreelook, this.keyBindCommand});
                KeyBinding.resetKeyBindingArrayAndHash();
                bufferedreader.close();
            }
        }
        catch (Exception exception11)
        {
            LOGGER.error("Failed to load 99th_Client options from TXT-file");
            exception11.printStackTrace();
        }
    }

    public void load99thclientSettingsFromJSON(GameSettings gameSettings) {
        try {
            String json = Utils.readFile(this.optionsFile99thclient, "UTF-8");
            JSONParser jp = new JSONParser();
            JSONObject root = (JSONObject) jp.parse(json);
            this.fromJSON(root, gameSettings);
        }
        catch (Exception exception11)
        {
            LOGGER.error("Failed to load 99th_Client options from JSON-file");
            exception11.printStackTrace();
        }
    }

    public void saveSettings() {
        try
        {
            JSONObject root = this.buildJSON();
            FileOutputStream fosJson = new FileOutputStream(this.optionsFile99thclient);
            OutputStreamWriter oswJson = new OutputStreamWriter(fosJson, "UTF-8");
            JSONWriter jw = new JSONWriter(oswJson);
            jw.writeObject(root);
            oswJson.flush();
            oswJson.close();
        }
        catch (Exception exception1)
        {
            LOGGER.error("Failed to save options");
            exception1.printStackTrace();
        }

        this.updateDiscord();
    }

    public void resetSettings() {
        this.locationHUD = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.SQUARE, 1, 1, 0);
        this.inventoryHUDmain = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, 1, 51, 0);
        this.inventoryHUDitems = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, 78, 19, 0);
        this.systemHUD = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.SQUARE, 91, 1, 0);
        this.cpsHUDleft = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, -125, 15, 0);
        this.cpsHUDright = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.NONE, 96, 15, 0);
        this.lookingHUD = new HUDSetting(true, -1, -1, -1, HUDSetting.Bracket.SQUARE, 1, 126, 0);
        this.itemHUDitems = Lists.newArrayList(Items.ARROW, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND, Items.EMERALD);
        this.fullBrightness = false;
        this.infiniteChat = true;
        this.showChatTimestamp = false;
        this.discordrpcShowServer = DiscordShowRPC.SERVER;
        this.healthIndicator = HealthIndicator.OFF;
        this.showToasts = ShowToasts.ALL;
        this.potionIcons = PotionIcons.NORMAL;
        this.tntTimer = true;
        this.potionTimer = true;
        this.armorBreakWarning = false;
        this.outOfBlocksWarning = false;
        this.tablistPing = false;
        this.decodeChatMagic = false;
        this.mc.fontRenderer.setDecodeChatMagic(this.decodeChatMagic);
        this.blockHighlight = false;
        this.resourcepackOptimization = false;
        this.dataCollection = true;
        this.timeTillAFK = 0;
        this.chatPrefix = "";
        this.chatPrefixEnabled = false;
        this.chatTriggers = Lists.newArrayList(
                new ChatTrigger("\\s?(\\w*)(?:\\shas activated).*", "/thanks $1", ActiveAFK.OFF, 0, 0)
        );
        this.chatFilters = Lists.newArrayList();
        this.eventTriggers = Lists.newArrayList(
                new EventTrigger(EventTrigger.Event.WORLD_JOIN, "", false, 0),
                new EventTrigger(EventTrigger.Event.SERVER_JOIN, "", false, 0)
        );
        this.hotkeys = Lists.newArrayList();
        this.customCommands = Lists.newArrayList();

        this.keyBindFreelook = new KeyBinding("99thclient.key.freelook", 71, "key.categories.misc");
        this.keyBindCommand = new KeyBinding("99thclient.key.command", 92, "key.categories.multiplayer");
        this.keyBindings = ArrayUtils.add(this.keyBindings, this.keyBindFreelook);
        this.keyBindings = ArrayUtils.add(this.keyBindings, this.keyBindCommand);
        KeyUtils.fixKeyConflicts(this.mc.gameSettings.keyBindings, new KeyBinding[] {this.keyBindFreelook, this.keyBindCommand});
        KeyBinding.resetKeyBindingArrayAndHash();

        this.saveSettings();
    }

    public JSONObject buildJSON() {
        JSONObject root = new JSONObject();
        root.put("buildAt", new Date().toString());
        root.put("mcVersion", Config.mcVersion);
        root.put("clientVersion", Config.clientVersion);

        JSONObject jLocationHUD = new JSONObject();
        jLocationHUD.put("active", this.locationHUD.active);
        jLocationHUD.put("mainColor", this.locationHUD.mainColor);
        jLocationHUD.put("subColor", this.locationHUD.subColor);
        jLocationHUD.put("bracketColor", this.locationHUD.bracketColor);
        jLocationHUD.put("bracketType", this.locationHUD.bracketType.getString());
        jLocationHUD.put("x", this.locationHUD.x);
        jLocationHUD.put("y", this.locationHUD.y);
        jLocationHUD.put("z", this.locationHUD.z);
        root.put("locationHUD", jLocationHUD);

        JSONObject jInventoryHUDmain = new JSONObject();
        jInventoryHUDmain.put("active", this.inventoryHUDmain.active);
        jInventoryHUDmain.put("mainColor", this.inventoryHUDmain.mainColor);
        jInventoryHUDmain.put("subColor", this.inventoryHUDmain.subColor);
        jInventoryHUDmain.put("bracketColor", this.inventoryHUDmain.bracketColor);
        jInventoryHUDmain.put("bracketType", this.inventoryHUDmain.bracketType.getString());
        jInventoryHUDmain.put("x", this.inventoryHUDmain.x);
        jInventoryHUDmain.put("y", this.inventoryHUDmain.y);
        jInventoryHUDmain.put("z", this.inventoryHUDmain.z);
        root.put("inventoryHUDmain", jInventoryHUDmain);

        JSONObject jInventoryHUDitems = new JSONObject();
        jInventoryHUDitems.put("active", this.inventoryHUDitems.active);
        jInventoryHUDitems.put("mainColor", this.inventoryHUDitems.mainColor);
        jInventoryHUDitems.put("subColor", this.inventoryHUDitems.subColor);
        jInventoryHUDitems.put("bracketColor", this.inventoryHUDitems.bracketColor);
        jInventoryHUDitems.put("bracketType", this.inventoryHUDitems.bracketType.getString());
        jInventoryHUDitems.put("x", this.inventoryHUDitems.x);
        jInventoryHUDitems.put("y", this.inventoryHUDitems.y);
        jInventoryHUDitems.put("z", this.inventoryHUDitems.z);
        root.put("inventoryHUDitems", jInventoryHUDitems);

        JSONObject jSystemHUD = new JSONObject();
        jSystemHUD.put("active", this.systemHUD.active);
        jSystemHUD.put("mainColor", this.systemHUD.mainColor);
        jSystemHUD.put("subColor", this.systemHUD.subColor);
        jSystemHUD.put("bracketColor", this.systemHUD.bracketColor);
        jSystemHUD.put("bracketType", this.systemHUD.bracketType.getString());
        jSystemHUD.put("x", this.systemHUD.x);
        jSystemHUD.put("y", this.systemHUD.y);
        jSystemHUD.put("z", this.systemHUD.z);
        root.put("systemHUD", jSystemHUD);

        JSONObject jCpsHUDleft = new JSONObject();
        jCpsHUDleft.put("active", this.cpsHUDleft.active);
        jCpsHUDleft.put("mainColor", this.cpsHUDleft.mainColor);
        jCpsHUDleft.put("subColor", this.cpsHUDleft.subColor);
        jCpsHUDleft.put("bracketColor", this.cpsHUDleft.bracketColor);
        jCpsHUDleft.put("bracketType", this.cpsHUDleft.bracketType.getString());
        jCpsHUDleft.put("x", this.cpsHUDleft.x);
        jCpsHUDleft.put("y", this.cpsHUDleft.y);
        jCpsHUDleft.put("z", this.cpsHUDleft.z);
        root.put("cpsHUDleft", jCpsHUDleft);

        JSONObject jCpsHUDright = new JSONObject();
        jCpsHUDright.put("active", this.cpsHUDright.active);
        jCpsHUDright.put("mainColor", this.cpsHUDright.mainColor);
        jCpsHUDright.put("subColor", this.cpsHUDright.subColor);
        jCpsHUDright.put("bracketColor", this.cpsHUDright.bracketColor);
        jCpsHUDright.put("bracketType", this.cpsHUDright.bracketType.getString());
        jCpsHUDright.put("x", this.cpsHUDright.x);
        jCpsHUDright.put("y", this.cpsHUDright.y);
        jCpsHUDright.put("z", this.cpsHUDright.z);
        root.put("cpsHUDright", jCpsHUDright);

        JSONObject jLookingHUD = new JSONObject();
        jLookingHUD.put("active", this.lookingHUD.active);
        jLookingHUD.put("mainColor", this.lookingHUD.mainColor);
        jLookingHUD.put("subColor", this.lookingHUD.subColor);
        jLookingHUD.put("bracketColor", this.lookingHUD.bracketColor);
        jLookingHUD.put("bracketType", this.lookingHUD.bracketType.getString());
        jLookingHUD.put("x", this.lookingHUD.x);
        jLookingHUD.put("y", this.lookingHUD.y);
        jLookingHUD.put("z", this.lookingHUD.z);
        root.put("lookingHUD", jLookingHUD);

        root.put("fullBrightness", this.fullBrightness);
        root.put("infiniteChat", this.infiniteChat);
        root.put("showChatTimestamp", this.showChatTimestamp);
        root.put("discordrpcShowServer", this.discordrpcShowServer.func_238162_a_());
        root.put("healthIndicator", this.healthIndicator.func_238162_a_());
        root.put("showToasts", this.showToasts.func_238162_a_());
        root.put("potionIcons", this.potionIcons.func_238162_a_());
        root.put("tntTimer", this.tntTimer);
        root.put("potionTimer", this.potionTimer);
        root.put("armorBreakWarning", this.armorBreakWarning);
        root.put("outOfBlocksWarning", this.outOfBlocksWarning);
        root.put("tablistPing", this.tablistPing);
        root.put("decodeChatMagic", this.decodeChatMagic);
        root.put("blockHighlight", this.blockHighlight);
        root.put("resourcepackOptimization", this.resourcepackOptimization);
        root.put("dataCollection", this.dataCollection);
        root.put("timeTillAFK", this.timeTillAFK);
        root.put("chatPrefix", this.chatPrefix);
        root.put("chatPrefixEnabled", this.chatPrefixEnabled);

        JSONArray jItemHUDitems = new JSONArray();
        for(Item item : this.itemHUDitems) {
            jItemHUDitems.add(item.toString());
        }
        root.put("itemHUDitems", jItemHUDitems);

        JSONArray jChatTriggers = new JSONArray();
        for(ChatTrigger trigger : this.chatTriggers) {
            JSONObject jCTrigger = new JSONObject();
            jCTrigger.put("pattern", trigger.pattern.pattern());
            jCTrigger.put("response", trigger.response);
            jCTrigger.put("active", trigger.active.func_238162_a_());
            jCTrigger.put("delay", trigger.delay);
            jCTrigger.put("cooldown", trigger.cooldown);
            jChatTriggers.add(jCTrigger);
        }
        root.put("chatTriggers", jChatTriggers);

        JSONArray jChatFilters = new JSONArray();
        for(ChatFilter filter : this.chatFilters) {
            JSONObject jCFilter = new JSONObject();
            jCFilter.put("pattern", filter.pattern.pattern());
            jCFilter.put("activePlayer", filter.activePlayer);
            jCFilter.put("activeChat", filter.activeChat);
            jChatFilters.add(jCFilter);
        }
        root.put("chatFilters", jChatFilters);

        JSONArray jEventTriggers = new JSONArray();
        for(EventTrigger trigger : this.eventTriggers) {
            JSONObject jETrigger = new JSONObject();
            jETrigger.put("trigger", trigger.trigger.getString());
            jETrigger.put("response", trigger.response);
            jETrigger.put("active", trigger.active);
            jETrigger.put("delay", trigger.delay);
            jEventTriggers.add(jETrigger);
        }
        root.put("eventTriggers", jEventTriggers);

        JSONArray jHotkeys = new JSONArray();
        for(Hotkey hotkey : this.hotkeys) {
            JSONObject jHotkey = new JSONObject();
            jHotkey.put("keyBinding", hotkey.keyBinding.getTranslationKey());
            jHotkey.put("response", hotkey.response);
            jHotkey.put("active", hotkey.active);
            jHotkeys.add(jHotkey);
        }
        root.put("hotkeys", jHotkeys);

        JSONArray jCustomCommands = new JSONArray();
        for(CustomCommand customCommand : this.customCommands) {
            JSONObject jCCommand = new JSONObject();
            jCCommand.put("name", customCommand.name);
            jCCommand.put("response", customCommand.response);
            jCCommand.put("active", customCommand.active);
            jCustomCommands.add(jCCommand);
        }
        root.put("customCommands", jCustomCommands);

        JSONObject jKeyBinds = new JSONObject();
        jKeyBinds.put("freelook", this.keyBindFreelook.getTranslationKey());
        jKeyBinds.put("command", this.keyBindCommand.getTranslationKey());
        root.put("keyBinds", jKeyBinds);

        return root;
    }

    public void fromJSON(JSONObject root, GameSettings gameSettings) {
        try {
            if(root.containsKey("locationHUD")) {
                JSONObject jLocationHUD = (JSONObject) root.get("locationHUD");

                this.locationHUD = new HUDSetting(
                        (boolean) jLocationHUD.get("active"),
                        ((Long) jLocationHUD.get("mainColor")).intValue(),
                        ((Long) jLocationHUD.get("subColor")).intValue(),
                        ((Long) jLocationHUD.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jLocationHUD.get("bracketType")),
                        ((Long) jLocationHUD.get("x")).intValue(),
                        ((Long) jLocationHUD.get("y")).intValue(),
                        ((Long) jLocationHUD.get("z")).intValue());
            }
            if(root.containsKey("inventoryHUDmain")) {
                JSONObject jInventoryHUDmain = (JSONObject) root.get("inventoryHUDmain");

                this.inventoryHUDmain = new HUDSetting(
                        (boolean) jInventoryHUDmain.get("active"),
                        ((Long) jInventoryHUDmain.get("mainColor")).intValue(),
                        ((Long) jInventoryHUDmain.get("subColor")).intValue(),
                        ((Long) jInventoryHUDmain.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jInventoryHUDmain.get("bracketType")),
                        ((Long) jInventoryHUDmain.get("x")).intValue(),
                        ((Long) jInventoryHUDmain.get("y")).intValue(),
                        ((Long) jInventoryHUDmain.get("z")).intValue());
            }
            if(root.containsKey("inventoryHUDitems")) {
                JSONObject jInventoryHUDitems = (JSONObject) root.get("inventoryHUDitems");

                this.inventoryHUDitems = new HUDSetting(
                        (boolean) jInventoryHUDitems.get("active"),
                        ((Long) jInventoryHUDitems.get("mainColor")).intValue(),
                        ((Long) jInventoryHUDitems.get("subColor")).intValue(),
                        ((Long) jInventoryHUDitems.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jInventoryHUDitems.get("bracketType")),
                        ((Long) jInventoryHUDitems.get("x")).intValue(),
                        ((Long) jInventoryHUDitems.get("y")).intValue(),
                        ((Long) jInventoryHUDitems.get("z")).intValue());
            }
            if(root.containsKey("systemHUD")) {
                JSONObject jSystemHUD = (JSONObject) root.get("systemHUD");

                this.systemHUD = new HUDSetting(
                        (boolean) jSystemHUD.get("active"),
                        ((Long) jSystemHUD.get("mainColor")).intValue(),
                        ((Long) jSystemHUD.get("subColor")).intValue(),
                        ((Long) jSystemHUD.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jSystemHUD.get("bracketType")),
                        ((Long) jSystemHUD.get("x")).intValue(),
                        ((Long) jSystemHUD.get("y")).intValue(),
                        ((Long) jSystemHUD.get("z")).intValue());
            }
            if(root.containsKey("cpsHUDleft")) {
                JSONObject jCpsHUDleft = (JSONObject) root.get("cpsHUDleft");

                this.cpsHUDleft = new HUDSetting(
                        (boolean) jCpsHUDleft.get("active"),
                        ((Long) jCpsHUDleft.get("mainColor")).intValue(),
                        ((Long) jCpsHUDleft.get("subColor")).intValue(),
                        ((Long) jCpsHUDleft.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jCpsHUDleft.get("bracketType")),
                        ((Long) jCpsHUDleft.get("x")).intValue(),
                        ((Long) jCpsHUDleft.get("y")).intValue(),
                        ((Long) jCpsHUDleft.get("z")).intValue());
            }
            if(root.containsKey("cpsHUDright")) {
                JSONObject jCpsHUDright = (JSONObject) root.get("cpsHUDright");

                this.cpsHUDright = new HUDSetting(
                        (boolean) jCpsHUDright.get("active"),
                        ((Long) jCpsHUDright.get("mainColor")).intValue(),
                        ((Long) jCpsHUDright.get("subColor")).intValue(),
                        ((Long) jCpsHUDright.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jCpsHUDright.get("bracketType")),
                        ((Long) jCpsHUDright.get("x")).intValue(),
                        ((Long) jCpsHUDright.get("y")).intValue(),
                        ((Long) jCpsHUDright.get("z")).intValue());
            }
            if(root.containsKey("lookingHUD")) {
                JSONObject jLookingHUD = (JSONObject) root.get("lookingHUD");

                this.lookingHUD = new HUDSetting(
                        (boolean) jLookingHUD.get("active"),
                        ((Long) jLookingHUD.get("mainColor")).intValue(),
                        ((Long) jLookingHUD.get("subColor")).intValue(),
                        ((Long) jLookingHUD.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jLookingHUD.get("bracketType")),
                        ((Long) jLookingHUD.get("x")).intValue(),
                        ((Long) jLookingHUD.get("y")).intValue(),
                        ((Long) jLookingHUD.get("z")).intValue());
            }
            if(root.containsKey("fullBrightness")) {
                this.fullBrightness = (boolean) root.get("fullBrightness");
            }
            if(root.containsKey("infiniteChat")) {
                this.infiniteChat = (boolean) root.get("infiniteChat");
            }
            if(root.containsKey("showChatTimestamp")) {
                this.showChatTimestamp = (boolean) root.get("showChatTimestamp");
            }
            if(root.containsKey("discordrpcShowServer")) {
                this.discordrpcShowServer = DiscordShowRPC.func_238163_a_(((Long) root.get("discordrpcShowServer")).intValue());
            }
            if(root.containsKey("healthIndicator")) {
                this.healthIndicator = HealthIndicator.func_238163_a_(((Long) root.get("healthIndicator")).intValue());
            }
            if(root.containsKey("showToasts")) {
                this.showToasts = ShowToasts.func_238163_a_(((Long) root.get("showToasts")).intValue());
            }
            if(root.containsKey("potionIcons")) {
                this.potionIcons = PotionIcons.func_238163_a_(((Long) root.get("potionIcons")).intValue());
            }
            if(root.containsKey("tntTimer")) {
                this.tntTimer = (boolean) root.get("tntTimer");
            }
            if(root.containsKey("potionTimer")) {
                this.potionTimer = (boolean) root.get("potionTimer");
            }
            if(root.containsKey("armorBreakWarning")) {
                this.armorBreakWarning = (boolean) root.get("armorBreakWarning");
            }
            if(root.containsKey("outOfBlocksWarning")) {
                this.outOfBlocksWarning = (boolean) root.get("outOfBlocksWarning");
            }
            if(root.containsKey("tablistPing")) {
                this.tablistPing = (boolean) root.get("tablistPing");
            }
            if(root.containsKey("decodeChatMagic")) {
                this.decodeChatMagic = (boolean) root.get("decodeChatMagic");
            }
            if(root.containsKey("blockHighlight")) {
                this.blockHighlight = (boolean) root.get("blockHighlight");
            }
            if(root.containsKey("resourcepackOptimization")) {
                this.resourcepackOptimization = (boolean) root.get("resourcepackOptimization");
            }
            if(root.containsKey("dataCollection")) {
                this.dataCollection = (boolean) root.get("dataCollection");
            }
            if(root.containsKey("timeTillAFK")) {
                this.timeTillAFK = ((Long) root.get("timeTillAFK")).intValue();
            }
            if(root.containsKey("chatPrefix")) {
                this.chatPrefix = (String) root.get("chatPrefix");
            }
            if(root.containsKey("chatPrefixEnabled")) {
                this.chatPrefixEnabled = (boolean) root.get("chatPrefixEnabled");
            }

            if(root.containsKey("itemHUDitems")) {
                this.itemHUDitems = new ArrayList<>();
                for (Object o : (JSONArray) root.get("itemHUDitems")) {
                    String s = (String) o;
                    Item i = MCStringUtils.parseItem(s);
                    if (i != null) {
                        this.itemHUDitems.add(i);
                    }
                }
            }

            if(root.containsKey("chatTriggers")) {
                this.chatTriggers = new ArrayList<>();
                for(Object o : (JSONArray) root.get("chatTriggers")) {
                    JSONObject jCTrigger = (JSONObject) o;
                    this.chatTriggers.add(new ChatTrigger(
                            (String) jCTrigger.get("pattern"),
                            (String) jCTrigger.get("response"),
                            ActiveAFK.func_238163_a_(((Long) jCTrigger.get("active")).intValue()),
                            ((Long) jCTrigger.get("delay")).intValue(),
                            ((Long) jCTrigger.get("cooldown")).intValue()));
                }
            }

            if(root.containsKey("chatFilters")) {
                this.chatFilters = new ArrayList<>();
                for (Object o : (JSONArray) root.get("chatFilters")) {
                    JSONObject jCFilter = (JSONObject) o;
                    this.chatFilters.add(new ChatFilter(
                            (String) jCFilter.get("pattern"),
                            (boolean) jCFilter.get("activePlayer"),
                            (boolean) jCFilter.get("activeChat")));
                }
            }

            if(root.containsKey("eventTriggers")) {
                this.eventTriggers = new ArrayList<>();
                for (Object o : (JSONArray) root.get("eventTriggers")) {
                    JSONObject jETrigger = (JSONObject) o;
                    this.eventTriggers.add(new EventTrigger(
                            EventTrigger.Event.fromString((String) jETrigger.get("trigger")),
                            (String) jETrigger.get("response"),
                            (boolean) jETrigger.get("active"),
                            ((Long) jETrigger.get("delay")).intValue()));
                }
            }

            if(root.containsKey("hotkeys")) {
                this.hotkeys = new ArrayList<>();
                for (Object o : (JSONArray) root.get("hotkeys")) {
                    JSONObject jHotkey = (JSONObject) o;

                    KeyBinding kb = new KeyBinding("99thclient.hotkeys.hotkey" + this.hotkeys.size(), -1, "key.categories.99thclienthotkeys");
                    kb.bind(InputMappings.getInputByName((String) jHotkey.get("keyBinding")));

                    this.hotkeys.add(new Hotkey(
                            kb,
                            (String) jHotkey.get("response"),
                            (boolean) jHotkey.get("active")));
                }
            }

            if(root.containsKey("customCommands")) {
                this.customCommands = new ArrayList<>();
                for (Object o : (JSONArray) root.get("customCommands")) {
                    JSONObject jCCommand = (JSONObject) o;

                    CustomCommand command = new CustomCommand(
                            (String) jCCommand.get("name"),
                            (String) jCCommand.get("response"),
                            (boolean) jCCommand.get("active"));

                    this.customCommands.add(command);
                    this.mc.commandManager.loadCommand(command);
                }
            }

            if(root.containsKey("keyBinds")) {
                JSONObject jKeyBinds = (JSONObject) root.get("keyBinds");
                if(jKeyBinds.containsKey("freelook")) {
                    this.keyBindFreelook.bind(InputMappings.getInputByName((String) jKeyBinds.get("freelook")));
                }
                if(jKeyBinds.containsKey("command")) {
                    this.keyBindCommand.bind(InputMappings.getInputByName((String) jKeyBinds.get("command")));
                }
            }

            if(this.mc.fontRenderer != null) {
                this.mc.fontRenderer.setDecodeChatMagic(this.decodeChatMagic);
            }

            KeyUtils.fixKeyConflicts(gameSettings.keyBindings, new KeyBinding[] {this.keyBindFreelook, this.keyBindCommand});
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        catch (Exception exception11)
        {
            LOGGER.error("Failed to load options from JSONObject");
            exception11.printStackTrace();
        }
    }

    /*
     * LIST SETTERS AND REMOVERS
     */
    public void setChatTrigger(int index, ChatTrigger chatTrigger)
    {
        this.chatTriggers.set(index, chatTrigger);
        this.saveSettings();
    }

    public void removeChatTrigger(ChatTrigger chatTrigger)
    {
        this.chatTriggers.remove(chatTrigger);
        this.saveSettings();
    }

    public void setChatFilter(int index, ChatFilter chatFilter)
    {
        this.chatFilters.set(index, chatFilter);
        this.saveSettings();
    }

    public void removeChatFilter(ChatFilter chatFilter)
    {
        this.chatFilters.remove(chatFilter);
        this.saveSettings();
    }

    public void setHotkey(int index, Hotkey hotkey)
    {
        this.hotkeys.set(index, hotkey);
        this.saveSettings();
    }

    public void removeHotkey(Hotkey hotkey)
    {
        this.hotkeys.remove(hotkey);
        this.saveSettings();
    }

    public void setCustomCommand(int index, CustomCommand customCommand)
    {
        this.customCommands.set(index, customCommand);
        this.saveSettings();
        this.mc.commandManager.reloadCommands();
    }

    public void removeCustomCommand(CustomCommand customCommand)
    {
        this.customCommands.remove(customCommand);
        this.saveSettings();
        this.mc.commandManager.reloadCommands();
    }

    /*
     * EXTENDED UPDATERS
     */
    public void updateDiscord() {
        if(this.mc.discord != null) {
            if (this.mc.discord.enabled && this.discordrpcShowServer == DiscordShowRPC.OFF) {
                this.mc.discord.close();
            } else if (!this.mc.discord.enabled && this.discordrpcShowServer != DiscordShowRPC.OFF) {
                this.mc.discord.start();
            }
        }
    }

    public void updateDataCollection() {
        if(this.mc.apiClient != null) {
            if (this.dataCollection) {
                this.mc.apiClient.startSession(this.mc.getSession());
            } else {
                this.mc.apiClient.stopSession();
            }
        }
    }
}
