package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.*;
import nl._99th_dutchclient.util.MCStringUtils;

public class HelpCommand extends Command {
    public HelpCommand() {
        super(new String[] {"help"}, "Show a list of client commands");
    }

    @Override
    public void execute(String string) {
        Minecraft mc = Minecraft.getInstance();
        String[] arguments = string.split(" ");
        int maxPages = (int) Math.ceil(mc.commandManager.commands.size() / 10.0);
        int pageIndex;

        if(arguments.length != 2) {
            pageIndex = 0;
        } else {
            pageIndex = MCStringUtils.tryParse(arguments[1]) - 1;
        }
        pageIndex = Math.max(0, Math.min(maxPages-1, pageIndex));

        IFormattableTextComponent tc = new StringTextComponent("Page ").setStyle(Style.EMPTY.setColor(Color.fromHex("#FF6600")));
        tc.append(new StringTextComponent((pageIndex + 1) + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))));
        tc.append(new StringTextComponent(" of ").setStyle(Style.EMPTY.setColor(Color.fromHex("#FF6600"))));
        tc.append(new StringTextComponent(maxPages + "").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))));
        if(pageIndex+1 != maxPages) {
            tc.append(new StringTextComponent(". Use \\help " + (pageIndex + 2) + " for the next page.").setStyle(Style.EMPTY.setColor(Color.fromHex("#FF6600")).setItalic(true)));
        }

        mc.ingameGUI.getChatGUI().printChatMessage(tc);

        for(int i = pageIndex * 10; i < mc.commandManager.commands.size() && i < (pageIndex+1) * 10; i++) {
            Command command = mc.commandManager.commands.get(i);

            mc.ingameGUI.getChatGUI().printChatMessage(
                    (new StringTextComponent("\\" + command.commandNames[0] + " ").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))))
                            .append(new StringTextComponent(command.description).setStyle(Style.EMPTY.setColor(Color.fromHex("#FF6600"))))
            );
        }
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\help"));
    }
}
