package nl._99th_client.ccg;

import net.minecraft.util.text.ITextComponent;
import nl._99th_client.util.MCStringUtils;
import org.json.simple.JSONObject;

import java.util.List;

public class Achievement {
    private Gamemode gamemode;
    private Category category;
    private int order;
    private Difficulty difficulty;
    private String name;
    private String description;

    private int rewardXP;
    private int rewardPoints;
    private int rewardCubelets;

    private boolean completed;

    public Achievement(Gamemode gamemode, Category category, int order, Difficulty difficulty, String name, String description, int rewardXP, int rewardPoints, int rewardCubelets, boolean completed) {
        this.gamemode = gamemode;
        this.category = category;
        this.order = order;
        this.difficulty = difficulty;
        this.name = name;
        this.description = description;
        this.rewardXP = rewardXP;
        this.rewardPoints = rewardPoints;
        this.rewardCubelets = rewardCubelets;
        this.completed = completed;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("gamemode", gamemode.getString());
        jsonObject.put("category", category.getString().toLowerCase());
        jsonObject.put("order", order);
        jsonObject.put("difficulty", difficulty.getString().toLowerCase());
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("reward_xp", rewardXP);
        jsonObject.put("reward_points", rewardPoints);
        jsonObject.put("reward_cubelets", rewardCubelets);
        jsonObject.put("user_completed", completed);

        return jsonObject;
    }

    public enum Category {
        GENERAL("General"),
        PROGRESSIVE("Progressive"),
        KITS("Kits"),
        STANDARD("Standard"),
        MODIFIERS("Modifiers"),
        UNKNOWN("Unknown");

        private final String name;

        Category(String name)
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

        public static Category fromString(String stringIn) {
            switch(stringIn.toLowerCase()) {
                case "general":
                    return GENERAL;
                case "progressive":
                    return PROGRESSIVE;
                case "kits":
                    return KITS;
                case "standard":
                    return STANDARD;
                case "modifiers":
                    return MODIFIERS;
                case "unknown":
                default:
                    return UNKNOWN;
            }
        }
    }

    public enum Difficulty {
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

    public static String descriptionFromTooltip(List<ITextComponent> tooltip) {
        String description = "";

        for(int i = 1; i < tooltip.size(); i++) {
            if(MCStringUtils.isBlank(tooltip.get(i).getString())) break;
            description += tooltip.get(i).getString() + " ";
        }

        return description.trim();
    }

    public static Difficulty difficultyFromTooltip(List<ITextComponent> tooltip) {
        for(int i = 1; i < tooltip.size(); i++) {
            if(tooltip.get(i).getString().startsWith("Difficulty: "))
                return Difficulty.fromString(tooltip.get(i).getString().substring(12));
        }

        return Difficulty.UNKNOWN;
    }

    public static int rewardFromTooltip(List<ITextComponent> tooltip, String type) {
        for(int i = 1; i < tooltip.size(); i++) {
            String line = tooltip.get(i).getString();
            if (line.startsWith("- ") && (line.endsWith(" " + type) || line.endsWith(" " + type + "s")))
                return MCStringUtils.tryParse(line.split(" ")[1]);
        }

        return 0;
    }
}
