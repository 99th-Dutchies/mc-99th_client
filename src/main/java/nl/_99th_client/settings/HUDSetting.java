package nl._99th_client.settings;

import net.minecraft.client.Minecraft;
import org.json.simple.JSONObject;

public class HUDSetting {
    public Type type;
    public boolean active;
    public int mainColor;
    public int subColor;
    public boolean dropShadow;
    public int bracketColor;
    public Bracket bracketType;
    public int x;
    public int y;
    public int z;

    public HUDSetting() {
        this(Type.EMPTY, true, -1, -1, false, -1, Bracket.NONE, 0, 0, 0);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, boolean dropShadow) {
        this(Type.COLOR, active, mainColor, subColor, dropShadow, -1, Bracket.NONE, 0, 0,0);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, boolean dropShadow, int bracketColor, Bracket bracketType) {
        this(Type.BRACKET, active, mainColor, subColor, dropShadow, bracketColor, bracketType, 0, 0,0);
    }

    public HUDSetting(boolean active, int x, int y, int z) {
        this(Type.POSITION, active, -1, -1 ,false, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, boolean dropShadow, int x, int y, int z) {
        this(Type.POSITION_COLOR, active, mainColor, -1, dropShadow, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, boolean dropShadow, int x, int y, int z) {
        this(Type.POSITION_TWOCOLOR, active, mainColor, subColor, dropShadow, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, boolean dropShadow, int bracketColor, Bracket bracketType, int x, int y, int z) {
        this(Type.FULL, active, mainColor, subColor, dropShadow, bracketColor, bracketType, x, y, z);
    }

    public HUDSetting(Type type, boolean active, int mainColor, int subColor, boolean dropShadow, int bracketColor, Bracket bracketType, int x, int y, int z) {
        this.type = type;
        this.active = active;
        this.mainColor = mainColor;
        this.subColor = subColor;
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

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("active", this.active);
        jsonObject.put("mainColor", this.mainColor);
        jsonObject.put("subColor", this.subColor);
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
        COLOR("color"),
        BRACKET("bracket"),
        POSITION("position"),
        POSITION_COLOR("position_color"),
        POSITION_TWOCOLOR("position_twocolor"),
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

        public boolean hasBracket() {
            return this.name.equals("bracket") || this.name.equals("full");
        }

        public boolean hasPosition() {
            return !(this.name.equals("empty") || this.name.equals("color") || this.name.equals("bracket"));
        }

        public static HUDSetting.Type fromString(String in) {
            switch(in) {
                case "empty":
                    return Type.EMPTY;
                case "color":
                    return Type.COLOR;
                case "bracket":
                    return Type.BRACKET;
                case "position":
                    return Type.POSITION;
                case "position_color":
                    return Type.POSITION_COLOR;
                case "position_twocolor":
                    return Type.POSITION_TWOCOLOR;
                default:
                case "full":
                    return Type.FULL;
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
