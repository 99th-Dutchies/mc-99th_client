package nl._99th_client.settings;

public enum BlockHighlight {
    OFF("off", "99thclient.options.BLOCK_HIGHLIGHT.off"),
    BASIC("basic", "99thclient.options.BLOCK_HIGHLIGHT.basic"),
    DEFAULT("default", "99thclient.options.BLOCK_HIGHLIGHT.default");

    private final String name;
    private final String translationKey;

    BlockHighlight(String name, String translationKey)
    {
        this.name = name;
        this.translationKey = translationKey;
    }

    public String toString()
    {
        return this.getString();
    }

    public String getString()
    {
        return this.name;
    }
    public String getTranslationKey() { return this.translationKey; }

    public static BlockHighlight fromString(String in) {
        switch(in) {
            case "off":
                return OFF;
            case "basic":
                return BASIC;
            default:
            case "default":
                return DEFAULT;
        }
    }
}
