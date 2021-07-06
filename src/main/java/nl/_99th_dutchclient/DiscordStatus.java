package nl._99th_dutchclient;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import io.netty.util.internal.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import nl._99th_dutchclient.settings.DiscordShowRPC;
import nl._99th_dutchclient.util.MCStringUtils;
import java.util.Collection;
import java.util.Optional;

public class DiscordStatus
{
    private Minecraft mc;
    public DiscordRichPresence richPresence;
    public String lastGamemode = "";
    public String lastMap = "";
    public String lastKit = "";
    public String lastProfile = "";
    public String lastKills = "";

    public DiscordStatus(Minecraft mc) {
        this.mc = mc;
        this.init();
    }

    private void init() {
        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "843127466705027082";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, "");

        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.largeImageKey = "icon";
        presence.largeImageText = "99th_DutchClient";
        this.richPresence = presence;
        lib.Discord_UpdatePresence(presence);

        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    public void update() {
        String state = "";
        String details = "";

        ClientPlayNetHandler clientplaynethandler = this.mc.getConnection();

        if(clientplaynethandler == null || !clientplaynethandler.getNetworkManager().isChannelOpen()) {
            details = "";
        } else if (this.mc.getIntegratedServer() != null && !this.mc.getIntegratedServer().getPublic()) {
            details = "Playing singleplayer";
        } else if(mc.isConnectedToRealms()) {
            details = "Playing realms";
        } else if(this.mc.getIntegratedServer() == null && (this.mc.getCurrentServerData() == null || !this.mc.getCurrentServerData().isOnLAN())) {
            if(this.mc.gameSettings.discordrpcShowServer != DiscordShowRPC.OFF) {
                details = "Online on " + this.mc.getCurrentServerData().serverName;

                if(this.mc.world != null && this.mc.world.getScoreboard() != null && this.mc.world.getScoreboard().getObjectiveInDisplaySlot(1) != null && this.mc.gameSettings.discordrpcShowServer != DiscordShowRPC.SERVER) {
                    state = this.getState(this.mc.world.getScoreboard().getObjectiveInDisplaySlot(1));
                }
            } else {
                details = "Playing multiplayer";
            }
        } else {
            details = "Playing multiplayer";
        }

        this.set(details, state);
    }

    private String getState(ScoreObjective sidebar) {
        Scoreboard scoreboard = sidebar.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(sidebar);

        // If we are in a lobby, we reset lastGamemode and lastMap and return the lobby-state
        Optional<Score> lobby = collection.stream().skip(3).findFirst();
        if(lobby.isPresent() && lobby.get().getPlayerName().contains("Lobby")) {
            this.lastGamemode = "";
            this.lastMap = "";
            this.lastKit = "";
            this.lastProfile = "";
            this.lastKills = "";

            if(this.mc.gameSettings.discordrpcShowServer == DiscordShowRPC.GAME) {
                return "In a lobby";
            } else {
                return "In lobby " + MCStringUtils.strip(lobby.get().getPlayerName()).replaceAll("Lobby:?\\s?", "");
            }
        }

        // If we are in a waiting lobby, we update lastGamemode and lastMap
        Optional<Score> level = collection.stream().skip(2).findFirst();
        Optional<Score> hasLevel = collection.stream().skip(3).findFirst();
        Optional<Score> profile = collection.stream().skip(3).findFirst();
        Optional<Score> hasProfile = collection.stream().skip(4).findFirst();
        Optional<Score> map = collection.stream().skip(5).findFirst();
        Optional<Score> isMap = collection.stream().skip(6).findFirst();
        Optional<Score> kit = collection.stream().skip(8).findFirst();
        Optional<Score> hasKit = collection.stream().skip(9).findFirst();
        Optional<Score> killsFFA = collection.stream().skip(6).findFirst();
        Optional<Score> killsASS = collection.stream().skip(8).findFirst();

        if(this.mc.player != null && map.isPresent() && isMap.isPresent() && isMap.get().getPlayerName().contains("Map:")) {
            boolean isTeam = this.mc.player.inventory.mainInventory.get(0).getDisplayName().getString().contains("Team Selection");

            if(isTeam && !sidebar.getDisplayName().getString().contains("Team")) {
                this.lastGamemode = "Team " + MCStringUtils.strip(sidebar.getDisplayName().getUnformattedComponentText());
            } else if(!isTeam) {
                switch (MCStringUtils.strip(sidebar.getDisplayName().getString())) {
                    case "SkyWars":
                    case "EggWars":
                    case "Lucky Islands":
                        this.lastGamemode = "Solo " + MCStringUtils.strip(sidebar.getDisplayName().getUnformattedComponentText());
                        break;
                    default:
                        this.lastGamemode = MCStringUtils.strip(sidebar.getDisplayName().getUnformattedComponentText());
                        break;
                }
            } else {
                this.lastGamemode = MCStringUtils.strip(sidebar.getDisplayName().getUnformattedComponentText());
            }
            this.lastMap = MCStringUtils.strip(map.get().getPlayerName());

            if(level.isPresent() && hasLevel.isPresent() && hasLevel.get().getPlayerName().contains("Level:")) {
                this.lastMap += " (level " + MCStringUtils.strip(level.get().getPlayerName()) + ")";
            }
        }
        if(this.mc.player != null && kit.isPresent() && hasKit.isPresent() && hasKit.get().getPlayerName().contains("Kit Name:")) {
            this.lastKit = MCStringUtils.strip(kit.get().getPlayerName());
        }
        if(this.mc.player != null && profile.isPresent() && hasProfile.isPresent() && hasProfile.get().getPlayerName().contains("Active Profile:")) {
            this.lastProfile = MCStringUtils.strip(profile.get().getPlayerName());
        }
        if(this.mc.player != null && killsASS.isPresent() && killsASS.get().getPlayerName().contains("Kills: ")) {
            this.lastKills = MCStringUtils.strip(killsASS.get().getPlayerName()).substring(7);
        }
        if(this.mc.player != null && killsFFA.isPresent() && killsFFA.get().getPlayerName().contains("Kills: ")) {
            this.lastKills = MCStringUtils.strip(killsFFA.get().getPlayerName()).substring(7);
        }

        if(StringUtil.isNullOrEmpty(this.lastGamemode)) {
            this.lastGamemode = MCStringUtils.strip(sidebar.getDisplayName().getUnformattedComponentText());
        }

        if (this.mc.gameSettings.discordrpcShowServer == DiscordShowRPC.GAME) {
            return "Playing " + this.lastGamemode;
        } else {
            if(!StringUtil.isNullOrEmpty(this.lastMap)) {
                return "Playing " + this.lastGamemode + " on map " + this.lastMap;
            }
            if(!StringUtil.isNullOrEmpty(this.lastKit)) {
                return "Playing " + this.lastGamemode + " with kit " + this.lastKit;
            }
            if(!StringUtil.isNullOrEmpty(this.lastProfile)) {
                return "Playing " + this.lastGamemode + " on " + this.lastProfile;
            }
            if(!StringUtil.isNullOrEmpty(this.lastKills)) {
                return "Playing " + this.lastGamemode + " having killed " + this.lastKills;
            }

            return "Playing " + this.lastGamemode;
        }
    }

    private void set(String details, String state) {
        if(this.richPresence == null) {
            return;
        }

        DiscordRPC lib = DiscordRPC.INSTANCE;
        DiscordRichPresence presence = this.richPresence;

        presence.details = StringUtil.isNullOrEmpty(details) ? null : details;
        presence.state = StringUtil.isNullOrEmpty(state) ? null : state;
        this.richPresence = presence;
        lib.Discord_UpdatePresence(presence);
    }

    public void close() {
        DiscordRPC.INSTANCE.Discord_Shutdown();
    }
}
