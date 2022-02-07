package nl._99th_client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.*;
import nl._99th_client.util.json.JSONUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NameHistoryCommand extends Command {
    public NameHistoryCommand() {
        super(new String[] {"namehistory","nh"}, "Retrieve the name history of a player");
    }

    @Override
    public void execute(String string) {
        String[] arguments = string.split(" ");

        if(arguments.length != 2) {
            this.invalid();
            return;
        }

        String username = arguments[1];

        Thread thread = new Thread(() -> {
            try {
                JSONObject profile = JSONUtil.readJsonObjectFromUrl("https://api.mojang.com/users/profiles/minecraft/" + username);

                if (profile == null || profile.get("id") == null) {
                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Username history belonging to " + username + " could not be resolved."));
                    return;
                }

                JSONArray names = JSONUtil.readJsonArrayFromUrl("https://api.mojang.com/user/profiles/" + profile.get("id") + "/names");

                if (names == null) {
                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Username history belonging to " + username + " could not be resolved."));
                    return;
                }

                DateFormat dutchDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (int i = 0; i < names.size(); i++) {
                    JSONObject n = (JSONObject) names.get(i);

                    IFormattableTextComponent tc = new StringTextComponent(n.get("name").toString()).setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB")));

                    if (n.containsKey("changedToAt")) {
                        tc.append(new StringTextComponent(" since ").setStyle(Style.EMPTY.setColor(Color.fromHex("#FF6600"))));
                        tc.append(new StringTextComponent(dutchDateFormat.format(new Date((long) n.get("changedToAt")))).setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))));
                    }

                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(tc);
                }
            } catch (IOException ex) {
                Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Username history belonging to " + username + " could not be resolved."));
            }
        });

        thread.start();
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\namehistory [username]"));
    }
}
