package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class AFKCommand extends Command {
    public AFKCommand() {
        super(new String[] {"afk"});
    }

    @Override
    public void execute(String string) {
        Minecraft.getInstance().afkStatus.toggle();
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\afk"));
    }
}
