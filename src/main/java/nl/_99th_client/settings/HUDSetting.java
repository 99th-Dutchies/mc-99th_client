package nl._99th_client.settings;

import net.minecraft.client.Minecraft;
import org.json.simple.JSONObject;

public class HUDSetting {
    public Type type;
    public boolean active;
    public ItemShow itemShow;
    public int mainColor;
    public int subColor;
    public boolean useDamageColor;
    public boolean dropShadow;
    public int bracketColor;
    public Bracket bracketType;
    public int x;
    public int y;
    public int z;

    public HUDSetting() {
        this(Type.EMPTY, true, -1, -1, true, ItemShow.TEXT, false, -1, Bracket.NONE, 0, 0, 0);
    }

    public HUDSetting(boolean active, int x, int y, int z) {
        this(Type.POSITION, active, -1, -1, true, ItemShow.TEXT, false, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, boolean dropShadow, int x, int y, int z) {
        this(Type.POSITION_COLOR, active, mainColor, -1, true, ItemShow.TEXT, dropShadow, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, boolean useDamageColor, ItemShow itemShow, boolean dropShadow, int x, int y, int z) {
        this(Type.POSITION_COLOR_ITEMS, active, mainColor, -1, useDamageColor, itemShow, dropShadow, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, boolean dropShadow, int x, int y, int z) {
        this(Type.POSITION_TWOCOLOR, active, mainColor, subColor, true, ItemShow.TEXT, dropShadow, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, boolean dropShadow, int bracketColor, Bracket bracketType, int x, int y, int z) {
        this(Type.FULL, active, mainColor, subColor, true, ItemShow.TEXT, dropShadow, bracketColor, bracketType, x, y, z);
    }

    public HUDSetting(Type type, boolean active, int mainColor, int subColor, boolean useDamageColor, ItemShow itemShow, boolean dropShadow, int bracketColor, Bracket bracketType, int x, int y, int z) {
        this.type = type;
        this.active = active;
        this.itemShow = itemShow;
        this.mainColor = mainColor;
        this.subColor = subColor;
        this.useDamageColor = useDamageColor;
        this.dropShadow = dropShadow;
        this.bracketColor = bracketColor;
        this.bracketType = bracketType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int posX() {
        if (this.x > 0) {
            return this.x;
        } else {
            return Minecraft.getInstance().getMainWindow().getScaledWidth() + this.x;
        }
    }

    public int posY() {
        if (this.y > 0) {
            return this.y;
        } else {
            return Minecraft.getInstance().getMainWindow().getScaledHeight() + this.y;
        }
    }

    public static HUDSetting fromJson(Type type, JSONObject jsonObject) {
        switch(type) {
            default:
            case EMPTY:
                return new HUDSetting();
            case POSITION:
                return new HUDSetting(
                        (boolean) jsonObject.get("active"),
                        ((Long) jsonObject.get("x")).intValue(),
                        ((Long) jsonObject.get("y")).intValue(),
                        ((Long) jsonObject.get("z")).intValue());
            case POSITION_COLOR:
                return new HUDSetting(
                        (boolean) jsonObject.get("active"),
                        ((Long) jsonObject.get("mainColor")).intValue(),
                        (boolean) jsonObject.get("dropShadow"),
                        ((Long) jsonObject.get("x")).intValue(),
                        ((Long) jsonObject.get("y")).intValue(),
                        ((Long) jsonObject.get("z")).intValue());
            case POSITION_TWOCOLOR:
                return new HUDSetting(
                        (boolean) jsonObject.get("active"),
                        ((Long) jsonObject.get("mainColor")).intValue(),
                        ((Long) jsonObject.get("subColor")).intValue(),
                        (boolean) jsonObject.get("dropShadow"),
                        ((Long) jsonObject.get("x")).intValue(),
                        ((Long) jsonObject.get("y")).intValue(),
                        ((Long) jsonObject.get("z")).intValue());
            case POSITION_COLOR_ITEMS:
                return new HUDSetting(
                        (boolean) jsonObject.get("active"),
                        ((Long) jsonObject.get("mainColor")).intValue(),
                        (boolean) jsonObject.get("useDamageColor"),
                        HUDSetting.ItemShow.fromString((String) jsonObject.get("itemShow")),
                        (boolean) jsonObject.get("dropShadow"),
                        ((Long) jsonObject.get("x")).intValue(),
                        ((Long) jsonObject.get("y")).intValue(),
                        ((Long) jsonObject.get("z")).intValue());
            case FULL:
                return new HUDSetting(
                        (boolean) jsonObject.get("active"),
                        ((Long) jsonObject.get("mainColor")).intValue(),
                        ((Long) jsonObject.get("subColor")).intValue(),
                        (boolean) jsonObject.get("dropShadow"),
                        ((Long) jsonObject.get("bracketColor")).intValue(),
                        HUDSetting.Bracket.fromString((String) jsonObject.get("bracketType")),
                        ((Long) jsonObject.get("x")).intValue(),
                        ((Long) jsonObject.get("y")).intValue(),
                        ((Long) jsonObject.get("z")).intValue());
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("active", this.active);
        jsonObject.put("itemShow", this.itemShow.getString());
        jsonObject.put("mainColor", this.mainColor);
        jsonObject.put("subColor", this.subColor);
        jsonObject.put("useDamageColor", this.useDamageColor);
        jsonObject.put("dropShadow", this.dropShadow);
        jsonObject.put("bracketColor", this.bracketColor);
        jsonObject.put("bracketType", this.bracketType.getString());
        jsonObject.put("x", this.x);
        jsonObject.put("y", this.y);
        jsonObject.put("z", this.z);
        return jsonObject;
    }

    public enum Type {
        EMPTY("empty"),
        POSITION("position"),
        POSITION_COLOR("position_color"),
        POSITION_TWOCOLOR("position_twocolor"),
        POSITION_COLOR_ITEMS("position_color_items"),
        FULL("full");

        private final String name;

        Type(String name)
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

        public boolean hasColor() {
            return !(this.name.equals("empty") || this.name.equals("position"));
        }

        public boolean hasSubColor() {
            return this.name.equals("position_twocolor") || this.name.equals("full");
        }

        public boolean hasBracket() {
            return this.name.equals("full");
        }

        public boolean hasPosition() {
            return !(this.name.equals("empty"));
        }

        public boolean hasItems() {
            return this.name.equals("position_color_items");
        }

        public static HUDSetting.Type fromString(String in) {
            switch(in) {
                case "empty":
                    return Type.EMPTY;
                case "position":
                    return Type.POSITION;
                case "position_color":
                    return Type.POSITION_COLOR;
                case "position_twocolor":
                    return Type.POSITION_TWOCOLOR;
                case "position_color_items":
                    return Type.POSITION_COLOR_ITEMS;
                default:
                case "full":
                    return Type.FULL;
            }
        }
    }

    public enum ItemShow {
        TEXT("text"),
        ICON("icon");

        private final String name;

        ItemShow(String name)
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

        public static HUDSetting.ItemShow fromString(String in) {
            switch(in) {
                case "icon":
                    return ItemShow.ICON;
                default:
                case "text":
                    return ItemShow.TEXT;
            }
        }
    }

    public enum Bracket {
        NONE("none"),
        ANGLE("angle"),
        CURLY("curly"),
        ROUND("round"),
        SQUARE("square");

        private final String name;

        Bracket(String name)
        {
            this.name = name;
        }

        public String open() {
            switch(this.name) {
                case "angle":
                    return "<";
                case "curly":
                    return "{";
                case "round":
                    return "(";
                case "square":
                    return "[";
                default:
                case "none":
                    return "";
            }
        }

        public String close() {
            switch(this.name) {
                case "angle":
                    return ">";
                case "curly":
                    return "}";
                case "round":
                    return ")";
                case "square":
                    return "]";
                default:
                case "none":
                    return "";
            }
        }

        public String toString()
        {
            return this.getString();
        }

        public String getString()
        {
            return this.name;
        }

        public static HUDSetting.Bracket fromString(String in) {
            switch(in) {
                case "angle":
                    return Bracket.ANGLE;
                case "curly":
                    return Bracket.CURLY;
                case "round":
                    return Bracket.ROUND;
                case "square":
                    return Bracket.SQUARE;
                default:
                case "none":
                    return Bracket.NONE;
            }
        }
    }
}
