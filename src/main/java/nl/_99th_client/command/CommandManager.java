package nl._99th_client.command;

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

    public void reloadCommands() {
        this.reloadCommands(true);
    }

    public void reloadCommands(boolean includeCustom) {
        this.commands = new ArrayList<>();
        this.loadCommands();

        if(includeCustom) {
            for (Command command : this.mc.gameSettings.customCommands) {
                this.loadCommand(command);
            }
        }
    }

    private void loadCommands() {
        this.commands.add(new HelpCommand());
        this.commands.add(new AFKCommand());
        this.commands.add(new ClickCommand());
        this.commands.add(new ColorCommand());
        this.commands.add(new NameHistoryCommand());
    }

    public void loadCommand(Command command) {
        this.commands.add(command);
    }

    public boolean checkCommand(String string) {
        this.mc.ingameGUI.getChatGUI().addToSentMessages(string);

        for(Command command : this.commands) {
            if(command.matches(string)) {
                command.execute(string);
                return true;
            }
        }

        this.mc.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("99thclient.commands.notFound"));
        return false;
    }
}
