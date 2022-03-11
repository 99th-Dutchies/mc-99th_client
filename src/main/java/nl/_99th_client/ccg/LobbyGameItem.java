package nl._99th_client.ccg;

import org.json.simple.JSONObject;

public class LobbyGameItem {
    private LobbyGame lobbyGame;
    private Holiday holiday;
    private String name;
    private int rarity;
    private int pointValue;
    private String firstDiscovery;
    private int numDiscoveries;

    public LobbyGameItem(LobbyGame lobbyGame, Holiday holiday, String name, int rarity, int pointValue, String firstDiscovery, int numDiscoveries) {
        this.lobbyGame = lobbyGame;
        this.holiday = holiday;
        this.name = name;
        this.rarity = rarity;
        this.pointValue = pointValue;
        this.firstDiscovery = firstDiscovery;
        this.numDiscoveries = numDiscoveries;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("lobbygame", lobbyGame.getString());
        jsonObject.put("holiday", holiday.getString().toLowerCase());
        jsonObject.put("name", name);
        jsonObject.put("rarity", rarity);
        jsonObject.put("pointValue", pointValue);
        jsonObject.put("first_discovery", firstDiscovery);
        jsonObject.put("num_discoveries", numDiscoveries);

        return jsonObject;
    }

    public enum LobbyGame {
        RELIC_HUNT("relic_hunt", "Relic Hunt"),
        LOBBY_FISHING("lobby_fishing", "Lobby Fishing"),
        UNKNOWN("unknown", "Unknown");

        private final String name;
        private final String displayName;

        LobbyGame(String name, String displayName)
        {
            this.name = name;
            this.displayName = displayName;
        }

        public String toString()
        {
            return this.getString();
        }

        public String getDisplayName()
        {
            return this.displayName;
        }

        public String getString() {
            return this.name;
        }
    }

    public enum Holiday {
        NONE("None"),
        VALENTINES("Valentines"),
        EASTER("Easter"),
        SUMMER("Summer"),
        HALLOWEEN("Halloween"),
        CHRISTMAS("Christmas"),
        UNKNOWN("Unknown");

        private final String name;

        Holiday(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.getString();
        }

        public String getString() {
            return this.name;
        }

        public static Holiday fromString(String stringIn) {
            switch(stringIn.toLowerCase()) {
                case "":
                case "none":
                    return NONE;
                case "valentines":
                    return VALENTINES;
                case "easter":
                    return EASTER;
                case "summer":
                    return SUMMER;
                case "halloween":
                    return HALLOWEEN;
                case "christmas":
                    return CHRISTMAS;
                default:
                    return UNKNOWN;
            }
        }
    }
}
