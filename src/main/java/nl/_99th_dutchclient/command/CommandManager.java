package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;

public class CommandManager {
    private Minecraft mc;
    public ArrayList<Command> commands = new ArrayList<>();

    public CommandManager(Minecraft mc) {
        this.mc = mc;
        this.loadCommands();
    }

    private void loadCommands() {
        this.commands.add(new AFKCommand());
        this.commands.add(new ColorCommand());
        this.commands.add(new NameHistoryCommand());
    }

    public boolean checkCommand(String string) {
        this.mc.ingameGUI.getChatGUI().addToSentMessages(string);

        for(Command command : this.commands) {
            if(command.matches(string)) {
                command.execute(string);
                return true;
            }
        }

        this.mc.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("99thdc.commands.notFound"));
        return false;
    }
}
