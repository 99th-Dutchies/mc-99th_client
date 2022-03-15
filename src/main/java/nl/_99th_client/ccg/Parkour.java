package nl._99th_client.ccg;

import net.minecraft.util.text.ITextComponent;
import org.json.simple.JSONObject;

import java.util.List;

public class Parkour {
    private Difficulty difficulty;
    private String name;
    private int level;

    private String goldTime;
    private String silverTime;
    private String bronzeTime;

    private boolean modNoCrouch;
    private boolean modBlind;
    private boolean modSpeed;
    private boolean modJumpBlind;
    private boolean modDontStop;

    private String playerTime;
    private String playerNoCrouch;
    private String playerBlind;
    private String playerSpeed;
    private String playerJumpBlind;
    private String playerDontStop;

    public Parkour(Difficulty difficulty, String name, int level,
                   String goldTime, String silverTime, String bronzeTime,
                   boolean modNoCrouch, boolean modBlind, boolean modSpeed, boolean modJumpBlind, boolean modDontStop,
                   String playerTime, String playerNoCrouch, String playerBlind, String playerSpeed, String playerJumpBlind, String playerDontStop) {
        this.difficulty = difficulty;
        this.name = name;
        this.level = level;

        this.goldTime = goldTime;
        this.silverTime = silverTime;
        this.bronzeTime = bronzeTime;

        this.modNoCrouch = modNoCrouch;
        this.modBlind = modBlind;
        this.modSpeed = modSpeed;
        this.modJumpBlind = modJumpBlind;
        this.modDontStop = modDontStop;

        this.playerTime = playerTime;
        this.playerNoCrouch = playerNoCrouch;
        this.playerBlind = playerBlind;
        this.playerSpeed = playerSpeed;
        this.playerJumpBlind = playerJumpBlind;
        this.playerDontStop = playerDontStop;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("difficulty", difficulty.getString().toLowerCase());
        jsonObject.put("name", name);
        jsonObject.put("level", level);

        jsonObject.put("goldTime", goldTime);
        jsonObject.put("silverTime", silverTime);
        jsonObject.put("bronzeTime", bronzeTime);

        jsonObject.put("modNoCrouch", modNoCrouch);
        jsonObject.put("modBlind", modBlind);
        jsonObject.put("modSpeed", modSpeed);
        jsonObject.put("modJumpBlind", modJumpBlind);
        jsonObject.put("modDontStop", modDontStop);

        jsonObject.put("playerTime", playerTime);
        jsonObject.put("playerNoCrouch", playerNoCrouch);
        jsonObject.put("playerBlind", playerBlind);
        jsonObject.put("playerSpeed", playerSpeed);
        jsonObject.put("playerJumpBlind", playerJumpBlind);
        jsonObject.put("playerDontStop", playerDontStop);

        return jsonObject;
    }

    public enum Difficulty {
        SIMPLE("Simple"),
        EASY("Easy"),
        MEDIUM("Medium"),
        HARD("Hard"),
        UNKNOWN("Unknown");

        private final String name;

        Difficulty(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.getString();
        }

        public String getString()
        {
            return this.name;
        }

        public static Difficulty fromString(String stringIn) {
            switch(stringIn.toLowerCase()) {
                case "simple":
                    return SIMPLE;
                case "easy":
                    return EASY;
                case "medium":
                    return MEDIUM;
                case "hard":
                    return HARD;
                case "unknown":
                default:
                    return UNKNOWN;
            }
        }
    }

    public static boolean[] modsFromTooltip(List<ITextComponent> tooltip) {
        boolean[] mods = new boolean[] {false, false, false, false, false};

        for(int i = 8; i < tooltip.size(); i++) {
            if(tooltip.get(i).getString().startsWith(" - No Crouching"))
                mods[0] = true;
            if(tooltip.get(i).getString().startsWith(" - Blindness"))
                mods[1] = true;
            if(tooltip.get(i).getString().startsWith(" - Speed Boost"))
                mods[2] = true;
            if(tooltip.get(i).getString().startsWith(" - Jump Boost & Blindness"))
                mods[3] = true;
            if(tooltip.get(i).getString().startsWith(" - Don't Stop Moving"))
                mods[4] = true;
        }

        return mods;
    }

    public static String[] playerModsFromTooltip(List<ITextComponent> tooltip) {
        String[] playerMods = new String[] {null, null, null, null, null};

        for(int i = 8; i < tooltip.size(); i++) {
            if(tooltip.get(i).getString().startsWith(" - No Crouching") && tooltip.get(i).getString().length() > 15)
                playerMods[0] = tooltip.get(i).getString().substring(16);
            if(tooltip.get(i).getString().startsWith(" - Blindness") && tooltip.get(i).getString().length() > 13)
                playerMods[1] = tooltip.get(i).getString().substring(13);
            if(tooltip.get(i).getString().startsWith(" - Speed Boost") && tooltip.get(i).getString().length() > 14)
                playerMods[2] = tooltip.get(i).getString().substring(15);
            if(tooltip.get(i).getString().startsWith(" - Jump Boost & Blindness") && tooltip.get(i).getString().length() > 25)
                playerMods[3] = tooltip.get(i).getString().substring(26);
            if(tooltip.get(i).getString().startsWith(" - Don't Stop Moving") && tooltip.get(i).getString().length() > 20)
                playerMods[4] = tooltip.get(i).getString().substring(21);
        }

        return playerMods;
    }
}
