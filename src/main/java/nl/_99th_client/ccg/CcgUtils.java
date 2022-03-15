package nl._99th_client.ccg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.*;
import nl._99th_client.util.MCStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CcgUtils {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String CCGFooter = "play.cubecraft.net";

    public static boolean isCCG(ScoreObjective sidebar) {
        Scoreboard scoreboard = sidebar.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(sidebar);
        Optional<Score> footer = collection.stream().findFirst();

        return footer.isPresent() && MCStringUtils.strip(footer.get().getPlayerName()).equals(CCGFooter);
    }

    public static void handleChest(ChestContainer container, ITextComponent title) {
        ScoreObjective sidebar = Minecraft.getInstance().world.getScoreboard().getObjectiveInDisplaySlot(1);
        if(!isCCG(sidebar)) return;

        String rawTitle = MCStringUtils.strip(title.getString());
        if(container.getNumRows() == 6) {
            for (Gamemode gamemode : Gamemode.values()) {
                if(rawTitle.startsWith(gamemode.getDisplayName() + " - ")) {
                    handleAchievements(container, title, gamemode);
                    return;
                }
            }

            if(rawTitle.startsWith("Relic Journal")) {
                handleLobbyGames(container, LobbyGameItem.LobbyGame.RELIC_HUNT);
                return;
            }

            if(rawTitle.startsWith("Fishing Journal")) {
                handleLobbyGames(container, LobbyGameItem.LobbyGame.LOBBY_FISHING);
                return;
            }
        } else if(container.getNumRows() == 2) {
            if(rawTitle.endsWith(" level selection!")) {
                handleParkour(container, title);
                return;
            }
        }
    }

    public static void handleAchievements(ChestContainer container, ITextComponent title, Gamemode gamemode) {
        Thread thread = new Thread(() -> {
            ArrayList<Achievement> achievements = new ArrayList<>();
            String category = title.getString().split(" - ")[1];
            int order = 1;
            for(ItemStack is : container.getInventory()) {
                if(is.getItem() == Items.EXPERIENCE_BOTTLE || is.getItem() == Items.GLASS_BOTTLE) {
                    List<ITextComponent> tooltip = is.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL);

                    achievements.add(new Achievement(
                            gamemode,
                            Achievement.Category.fromString(category),
                            order,
                            Achievement.difficultyFromTooltip(tooltip),
                            tooltip.get(0).getString(),
                            Achievement.descriptionFromTooltip(tooltip),
                            Achievement.rewardFromTooltip(tooltip, "Experience"),
                            Achievement.rewardFromTooltip(tooltip, "Point"),
                            Achievement.rewardFromTooltip(tooltip, "Cubelet"),
                            is.getItem() == Items.EXPERIENCE_BOTTLE));
                    order++;
                }
            }
            if(achievements.size() > 0) {
                Minecraft.getInstance().apiClient.submitAchievements(achievements);
            }
        });

        thread.start();
    }

    public static void handleLobbyGames(ChestContainer container, LobbyGameItem.LobbyGame lobbyGame) {
        Thread thread = new Thread(() -> {
            ArrayList<LobbyGameItem> lobbyGameItems = new ArrayList<>();

            for(ItemStack is : container.getInventory()) {
                if(is.getItem() == Items.AIR) { break; }
                List<ITextComponent> tooltip = is.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL);

                if(!tooltip.get(0).getString().equals("?????")) {
                    if(lobbyGame == LobbyGameItem.LobbyGame.RELIC_HUNT) {
                        lobbyGameItems.add(new LobbyGameItem(
                                lobbyGame,
                                tooltip.size() >= 9 ? LobbyGameItem.Holiday.fromString(tooltip.get(8).getString().substring(9)) : LobbyGameItem.Holiday.NONE,
                                tooltip.get(0).getString(),
                                tooltip.get(2).getString().length() - 8,
                                MCStringUtils.tryParse(tooltip.get(3).getString().substring(13)),
                                tooltip.get(6).getString().substring(26),
                                MCStringUtils.tryParse(tooltip.get(5).getString().substring(22, tooltip.get(5).getString().length() - 7))
                        ));
                    } else if(lobbyGame == LobbyGameItem.LobbyGame.LOBBY_FISHING) {
                        lobbyGameItems.add(new LobbyGameItem(
                                lobbyGame,
                                tooltip.size() >= 9 ? LobbyGameItem.Holiday.fromString(tooltip.get(8).getString().substring(9)) : LobbyGameItem.Holiday.NONE,
                                tooltip.get(0).getString(),
                                tooltip.get(2).getString().length() - 8,
                                MCStringUtils.tryParse(tooltip.get(3).getString().substring(13)),
                                tooltip.get(6).getString().substring(22),
                                MCStringUtils.tryParse(tooltip.get(5).getString().substring(19, tooltip.get(5).getString().length() - 7))
                        ));
                    }
                }
            }
            if(lobbyGameItems.size() > 0) {
                Minecraft.getInstance().apiClient.submitLobbyGames(lobbyGameItems);
            }
        });

        thread.start();
    }

    public static void handleParkour(ChestContainer container, ITextComponent title) {
        Thread thread = new Thread(() -> {
            ArrayList<Parkour> parkours = new ArrayList<>();

            for(ItemStack is : container.getInventory()) {
                if(is.getItem() == Items.AIR) { break; }
                List<ITextComponent> tooltip = is.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL);

                boolean[] mods = Parkour.modsFromTooltip(tooltip);
                String[] playerMods = Parkour.playerModsFromTooltip(tooltip);

                parkours.add(new Parkour(
                        Parkour.Difficulty.fromString(title.getString().split(" ")[0]),
                        tooltip.get(0).getString().split(" \\[Lvl ")[0],
                        MCStringUtils.tryParse(tooltip.get(0).getString().substring(tooltip.get(0).getString().length() - 2, tooltip.get(0).getString().length()-1)),
                        tooltip.get(3).getString().split(" ")[3],
                        tooltip.get(4).getString().split(" ")[3],
                        tooltip.get(5).getString().split(" ")[3],
                        mods[0],
                        mods[1],
                        mods[2],
                        mods[3],
                        mods[4],
                        tooltip.get(1).getString().substring(16).equals("N/A") ? null : tooltip.get(1).getString().substring(16),
                        playerMods[0],
                        playerMods[1],
                        playerMods[2],
                        playerMods[3],
                        playerMods[4]
                ));
            }
            if(parkours.size() > 0) {
                Minecraft.getInstance().apiClient.submitParkours(parkours);
            }
        });

        thread.start();
    }
}
