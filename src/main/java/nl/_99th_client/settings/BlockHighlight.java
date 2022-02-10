package nl._99th_client.settings;

import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

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

    public static boolean validBlock(BlockState blockState){
        if(!RenderTypeLookup.func_239221_b_(blockState).equals(RenderType.getSolid())) return false;

        Block block = blockState.getBlock();

        return !(block instanceof FourWayBlock ||
                block instanceof AbstractSkullBlock ||
                block instanceof AnvilBlock ||
                block instanceof StairsBlock ||
                block instanceof SlabBlock ||
                block instanceof BellBlock ||
                block instanceof AbstractPressurePlateBlock ||
                block instanceof EnchantingTableBlock ||
                block instanceof EndPortalFrameBlock ||
                block instanceof LecternBlock ||
                block instanceof HorizontalFaceBlock ||
                block instanceof AbstractBannerBlock ||
                block instanceof AbstractSignBlock);
    }

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
