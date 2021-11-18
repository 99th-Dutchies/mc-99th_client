package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CustomCommand extends Command
{
    public String name;
    public String response;
    public boolean active;

    public CustomCommand(String name, String response, boolean active) {
        super(new String[] {name});

        this.name = name;
        this.response = response;
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
        this.commandNames = new String[] {name};
    }

    @Override
    public boolean matches(String string) {
        if(!this.active) return false;

        for(String commandName : commandNames) {
            if(string.equals("\\" + commandName) || string.startsWith("\\" + commandName + " ")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void execute(String string) {
        Minecraft mc = Minecraft.getInstance();
        String resp = this.response;
        String[] splitIn = string.split("\\s+");

        for(int i = 1; i < splitIn.length; i++) {
            resp = resp.replace("$" + i, splitIn[i]);
        }

        String[] split = resp.split("\\|");

        for(String r : split) {
            if(r.startsWith("\\")) {
                mc.commandManager.checkCommand(r);
                continue;
            }

            mc.ingameGUI.getChatGUI().addToSentMessages(r);
            mc.player.sendChatMessage(r);
        }
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\" + this.name));
    }
}
