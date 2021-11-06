package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;

public class AFKCommand extends Command {
    public AFKCommand() {
        super(new String[] {"afk"});
    }

    @Override
    public void execute(String string) {
        Minecraft.getInstance().afkStatus.toggle();
    }
}
