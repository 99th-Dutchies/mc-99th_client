package nl._99th_client.settings;

public class HUDSetting {
    public boolean active = true;
    public int mainColor = -1;
    public int subColor = -1;
    public int bracketColor = -1;
    public Bracket bracketType = Bracket.NONE;
    public int x = 0;
    public int y = 0;
    public int z = 0;

    public HUDSetting() {
        this(true, -1, -1, -1, Bracket.NONE, 0, 0, 0);
    }

    public HUDSetting(boolean active, int mainColor, int subColor) {
        this(active, mainColor, subColor, -1, Bracket.NONE, 0, 0,0);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, int bracketColor, Bracket bracketType) {
        this(active, mainColor, subColor, bracketColor, bracketType, 0, 0,0);
    }

    public HUDSetting(boolean active, int mainColor, int subColor, int bracketColor, Bracket bracketType, int x, int y, int z) {
        this.active = active;
        this.mainColor = mainColor;
        this.subColor = subColor;
        this.bracketColor = bracketColor;
        this.bracketType = bracketType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public enum Bracket {
        NONE("none"),
        ANGLE("angle"),
        CURLY("curly"),
        ROUND("round"),
        SHARP_ANGLE("sharp_angle"),
        SQUARE("square");

        private final String name;

        Bracket(String name)
        {
            this.name = name;
        }

        public String open() {
            switch(this.name) {
                case "angle":
                    return "〈";
                case "curly":
                    return "{";
                case "round":
                    return "(";
                case "sharp_angle":
                    return "<";
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
                    return "〉";
                case "curly":
                    return "}";
                case "round":
                    return ")";
                case "sharp_angle":
                    return ">";
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
                case "sharp_angle":
                    return Bracket.SHARP_ANGLE;
                case "square":
                    return Bracket.SQUARE;
                default:
                case "none":
                    return Bracket.NONE;
            }
        }
    }
}
