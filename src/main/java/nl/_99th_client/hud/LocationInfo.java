package nl._99th_client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import nl._99th_client.settings.HUDSetting;

public class LocationInfo
{
    public static void render(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt) {
        // Draw X, Y and Z coordinates
        renderX(mc, hudSetting, ms);
        renderY(mc, hudSetting, ms);
        renderZ(mc, hudSetting, ms);

        // Draw direction/orientation
        renderDir(mc, hudSetting, ms);

        // Draw biome
        renderBiome(mc, hudSetting, ms);
    }

    private static void renderX(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        IFormattableTextComponent s = new StringTextComponent(hudSetting.bracketType.open()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.bracketColor)));
        s.append(new StringTextComponent("X").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor))));
        s.append(new StringTextComponent(hudSetting.bracketType.close()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.bracketColor))));
        s.append(new StringTextComponent(" " + (int) mc.renderViewEntity.getPosX()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor))));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY(), -1);
        }
    }

    private static void renderY(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        IFormattableTextComponent s = new StringTextComponent(hudSetting.bracketType.open()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.bracketColor)));
        s.append(new StringTextComponent("Y").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor))));
        s.append(new StringTextComponent(hudSetting.bracketType.close()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.bracketColor))));
        s.append(new StringTextComponent(" " + (int) mc.renderViewEntity.getPosY()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor))));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY() + 10, -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY() + 10, -1);
        }
    }

    private static void renderZ(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        IFormattableTextComponent s = new StringTextComponent(hudSetting.bracketType.open()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.bracketColor)));
        s.append(new StringTextComponent("Z").setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor))));
        s.append(new StringTextComponent(hudSetting.bracketType.close()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.bracketColor))));
        s.append(new StringTextComponent(" " + (int) mc.renderViewEntity.getPosZ()).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor))));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY() + 20, -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY() + 20, -1);
        }
    }

    private static void renderDir(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        // Determine direction based on rotation
        float rot = ((mc.renderViewEntity.rotationYaw % 360.0F) + 360.0F) % 360.0F;

        String dir = rotationToDirection(rot);
        String deltaX = rotationToDeltaX(rot);
        String deltaY = rotationToDeltaZ(rot);

        // Draw orientation/direction
        IFormattableTextComponent dX = new StringTextComponent(deltaX).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor)));
        IFormattableTextComponent dDir = new StringTextComponent(dir).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));
        IFormattableTextComponent dY = new StringTextComponent(deltaY).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.subColor)));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, dX, hudSetting.posX() + 60, hudSetting.posY(), -1);
            mc.fontRenderer.func_243246_a(ms, dDir, hudSetting.posX() + 60, hudSetting.posY() + 10, -1);
            mc.fontRenderer.func_243246_a(ms, dY, hudSetting.posX() + 60, hudSetting.posY() + 20, -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, dX, hudSetting.posX() + 60, hudSetting.posY(), -1);
            mc.fontRenderer.func_243248_b(ms, dDir, hudSetting.posX() + 60, hudSetting.posY() + 10, -1);
            mc.fontRenderer.func_243248_b(ms, dY, hudSetting.posX() + 60, hudSetting.posY() + 20, -1);
        }
    }

    private static void renderBiome(Minecraft mc, HUDSetting hudSetting, MatrixStack ms) {
        String biomeName = mc.renderViewEntity.world.getBiome(mc.renderViewEntity.getPosition()).getCategory().getName().replaceAll("_", " ");
        biomeName = biomeName.substring(0, 1).toUpperCase() + biomeName.substring(1);

        IFormattableTextComponent s = new StringTextComponent(biomeName).setStyle(Style.EMPTY.setColor(Color.fromInt(hudSetting.mainColor)));

        if(hudSetting.dropShadow) {
            mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), hudSetting.posY() + 30, -1);
        } else {
            mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), hudSetting.posY() + 30, -1);
        }
    }

    private static String rotationToDirection(float rotation) {
        if (rotation < 0) {
            return "?";
        } else if (rotation <= 45) {
            return "S";
        } else if (rotation <= 135) {
            return "W";
        } else if (rotation <= 225) {
            return "N";
        } else if (rotation <= 315) {
            return "E";
        } else if (rotation < 360) {
            return "S";
        } else {
            return "?";
        }
    }

    private static String rotationToDeltaX(float rotation) {
        if (rotation < 0) {
            return "?";
        } else if (rotation <= 22.5) {
            return "";
        } else if (rotation <= 67.5) {
            return "-";
        } else if (rotation <= 112.5) {
            return "--";
        } else if (rotation <= 157.5) {
            return "-";
        } else if (rotation <= 202.5) {
            return "";
        } else if (rotation <= 247.5) {
            return "+";
        } else if (rotation <= 292.5) {
            return "++";
        } else if (rotation <= 337.5) {
            return "+";
        } else if (rotation < 360) {
            return "";
        } else {
            return "?";
        }
    }

    private static String rotationToDeltaZ(float rotation) {
        if (rotation < 0) {
            return "?";
        } else if (rotation <= 22.5) {
            return "++";
        } else if (rotation <= 67.5) {
            return "+";
        } else if (rotation <= 112.5) {
            return "";
        } else if (rotation <= 157.5) {
            return "-";
        } else if (rotation <= 202.5) {
            return "--";
        } else if (rotation <= 247.5) {
            return "-";
        } else if (rotation <= 292.5) {
            return "";
        } else if (rotation <= 337.5) {
            return "+";
        } else if (rotation < 360) {
            return "++";
        } else {
            return "?";
        }
    }
}
