package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import nl._99th_dutchclient.util.DefaultFontInfo;
import org.apache.commons.lang3.StringUtils;

public class ColorCommand extends Command {
    public ColorCommand() {
        super(new String[] {"color","colour"});
    }

    @Override
    public void execute(String string) {
        NewChatGui chatGui = Minecraft.getInstance().ingameGUI.getChatGUI();
        int halfChatWidth = chatGui.getChatWidth() / 2;

        String line = StringUtils.repeat(" ", halfChatWidth * 2 / (DefaultFontInfo.SPACE.getLength() + 1));
        chatGui.printChatMessage(new StringTextComponent(TextFormatting.STRIKETHROUGH + line).setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))));

        chatGui.printChatMessage(
                new StringTextComponent("$0 ")
                    .append(new StringTextComponent(TextFormatting.BLACK + "BLACK"))
                    .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$0 BLACK", false), halfChatWidth)))
                    .append(new StringTextComponent("$8 "))
                    .append(new StringTextComponent(TextFormatting.DARK_GRAY + "DARK GRAY")));

        chatGui.printChatMessage(
                new StringTextComponent("$1 ")
                        .append(new StringTextComponent(TextFormatting.DARK_BLUE + "DARK BLUE"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$1 DARK BLUE", false), halfChatWidth)))
                        .append(new StringTextComponent("$9 "))
                        .append(new StringTextComponent(TextFormatting.BLUE + "BLUE")));

        chatGui.printChatMessage(
                new StringTextComponent("$2 ")
                        .append(new StringTextComponent(TextFormatting.DARK_GREEN + "DARK GREEN"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$2 DARK GREEN", false), halfChatWidth)))
                        .append(new StringTextComponent("$a "))
                        .append(new StringTextComponent(TextFormatting.GREEN + "GREEN")));

        chatGui.printChatMessage(
                new StringTextComponent("$3 ")
                        .append(new StringTextComponent(TextFormatting.DARK_AQUA + "DARK AQUA"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$3 DARK AQUA", false), halfChatWidth)))
                        .append(new StringTextComponent("$b "))
                        .append(new StringTextComponent(TextFormatting.AQUA + "AQUA")));

        chatGui.printChatMessage(
                new StringTextComponent("$4 ")
                        .append(new StringTextComponent(TextFormatting.DARK_RED + "DARK RED"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$4 DARK RED", false), halfChatWidth)))
                        .append(new StringTextComponent("$c "))
                        .append(new StringTextComponent(TextFormatting.RED + "RED")));

        chatGui.printChatMessage(
                new StringTextComponent("$5 ")
                        .append(new StringTextComponent(TextFormatting.DARK_PURPLE + "DARK PURPLE"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$5 DARK PURPLE", false), halfChatWidth)))
                        .append(new StringTextComponent("$d "))
                        .append(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "LIGHT PURPLE")));

        chatGui.printChatMessage(
                new StringTextComponent("$6 ")
                        .append(new StringTextComponent(TextFormatting.GOLD + "GOLD"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$6 GOLD", false), halfChatWidth)))
                        .append(new StringTextComponent("$e "))
                        .append(new StringTextComponent(TextFormatting.YELLOW + "YELLOW")));

        chatGui.printChatMessage(
                new StringTextComponent("$7 ")
                        .append(new StringTextComponent(TextFormatting.GRAY + "GRAY"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$7 GRAY", false), halfChatWidth)))
                        .append(new StringTextComponent("$f "))
                        .append(new StringTextComponent(TextFormatting.WHITE + "WHITE")));

        chatGui.printChatMessage(
                new StringTextComponent("$k ")
                        .append(new StringTextComponent(TextFormatting.OBFUSCATED + "MAGIC"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$k MAGIC", false), halfChatWidth)))
                        .append(new StringTextComponent("$n "))
                        .append(new StringTextComponent(TextFormatting.UNDERLINE + "UNDERLINE")));

        chatGui.printChatMessage(
                new StringTextComponent("$l ")
                        .append(new StringTextComponent("BOLD").setStyle(Style.EMPTY.setBold(true)))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$l BOLD", true), halfChatWidth)))
                        .append(new StringTextComponent("$o "))
                        .append(new StringTextComponent("ITALIC").setStyle(Style.EMPTY.setItalic(true))));

        chatGui.printChatMessage(
                new StringTextComponent("$m ")
                        .append(new StringTextComponent(TextFormatting.STRIKETHROUGH + "STRIKETHROUGH"))
                        .append(new StringTextComponent(DefaultFontInfo.spaces(DefaultFontInfo.getStringLength("$m STRIKETHROUGH", false), halfChatWidth)))
                        .append(new StringTextComponent("$r "))
                        .append(new StringTextComponent("RESET")));
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\colour"));
    }
}
