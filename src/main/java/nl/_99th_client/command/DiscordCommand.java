package nl._99th_client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import nl._99th_client.Config;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class DiscordCommand extends Command {
    public DiscordCommand() {
        super(new String[] {"discord"}, "Link you Minecraft account to Discord");
    }

    @Override
    public void execute(String string) {
        String[] arguments = string.split(" ");

        if(arguments.length != 2) {
            this.invalid();
            return;
        }

        if(!Minecraft.getInstance().gameSettings._99thClientSettings.dataCollection) {
            Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "You need allow Data Collection to link your Discord account. Enable this in your 99th_Client Settings!"));
            return;
        }

        String tag = arguments[1];

        Thread thread = new Thread(() -> {
            try {
                JSONObject data = Minecraft.getInstance().apiClient.resolveDiscordUser(tag);

                if (data == null || data.get("id") == null || data.get("tag") == null) {
                    if(data.get("reason") == null) {
                        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(
                                new StringTextComponent(TextFormatting.RED + "Discord user " + tag + " could not be found. ")
                                        .append(new StringTextComponent("Be sure to ").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))))
                                        .append(new StringTextComponent("join our Discord").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))).mergeStyle(TextFormatting.UNDERLINE).modifyStyle((p) -> {
                                            return p.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.discordInvite));
                                        }))
                                        .append(new StringTextComponent("!").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))))
                        );
                    } else {
                        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "An error occured: " + data.get("reason")));
                    }
                    return;
                }

                IFormattableTextComponent tcTag = new StringTextComponent("Discord user found: ").setStyle(Style.EMPTY.setColor(Color.fromHex("#FF6600")));
                tcTag.append(new StringTextComponent((String) data.get("tag")).setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))));
                Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(tcTag);

                IFormattableTextComponent tcConfirm = new StringTextComponent("Do you want to link this account? ").setStyle(Style.EMPTY.setColor(Color.fromHex("#FF6600")));
                tcConfirm.append(new StringTextComponent("Confirm").mergeStyle(TextFormatting.GREEN).modifyStyle((p) -> {
                    return p.setClickEvent(new ClickEvent(ClickEvent.Action.CONFIRM_LINK_DISCORD, (String) data.get("id"), (String) data.get("tag")));
                }));
                Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(tcConfirm);
            } catch (IOException | ParseException ex) {
                Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(
                        new StringTextComponent(TextFormatting.RED + "Discord user " + tag + " could not be found. ")
                                .append(new StringTextComponent("Be sure to ").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))))
                                .append(new StringTextComponent("join our Discord").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))).mergeStyle(TextFormatting.UNDERLINE).modifyStyle((p) -> {
                                    return p.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.discordInvite));
                                }))
                                .append(new StringTextComponent("!").setStyle(Style.EMPTY.setColor(Color.fromHex("#1E89EB"))))
                );
            }
        });

        thread.start();
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\discord [tag]"));
    }
}
