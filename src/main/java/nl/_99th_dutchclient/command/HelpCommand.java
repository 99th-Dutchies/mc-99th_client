package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class HelpCommand extends Command {
    public HelpCommand() {
        super(new String[] {"help"}, "Show a list of client commands");
    }

    @Override
    public void execute(String string) {
        Minecraft mc = Minecraft.getInstance();

        for(Command command : mc.commandManager.commands) {
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
