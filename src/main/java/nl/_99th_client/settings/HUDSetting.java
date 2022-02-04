package nl._99th_client.settings;

public class HUDSetting {
    public Type type;
    public boolean active;
    public int mainColor;
    public int subColor;
    public int bracketColor;
    public Bracket bracketType;
    public int x;
    public int y;
    public int z;

    public HUDSetting() {
        this(Type.EMPTY, true, -1, -1, -1, Bracket.NONE, 0, 0, 0);
    }

    public HUDSetting(boolean active, int mainColor, int subColor) {
        this(Type.COLOR, active, mainColor, subColor, -1, Bracket.NONE, 0, 0,0);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, int bracketColor, Bracket bracketType) {
        this(Type.BRACKET, active, mainColor, subColor, bracketColor, bracketType, 0, 0,0);
    }

    public HUDSetting(boolean active, int x, int y, int z) {
        this(Type.POSITION, active, -1, -1, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, int x, int y, int z) {
        this(Type.POSITION_COLOR, active, mainColor, subColor, -1, Bracket.NONE, x, y, z);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, int bracketColor, Bracket bracketType, int x, int y, int z) {
        this(Type.FULL, active, mainColor, subColor, bracketColor, bracketType, x, y, z);
    }

    public HUDSetting(Type type, boolean active, int mainColor, int subColor, int bracketColor, Bracket bracketType, int x, int y, int z) {
        this.type = type;
        this.active = active;
        this.mainColor = mainColor;
        this.subColor = subColor;
        this.bracketColor = bracketColor;
        this.bracketType = bracketType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public enum Type {
        EMPTY("empty"),
        COLOR("color"),
        BRACKET("bracket"),
        POSITION("position"),
        POSITION_COLOR("position_color"),
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
