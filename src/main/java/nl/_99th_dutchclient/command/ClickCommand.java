package nl._99th_dutchclient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import nl._99th_dutchclient.util.InventoryHelper;
import nl._99th_dutchclient.util.MCStringUtils;
import java.util.Arrays;

public class ClickCommand extends Command {
    public ClickCommand() {
        super(new String[] {"click"}, "Click a slot");
    }

    @Override
    public void execute(String string) {
        Minecraft mc = Minecraft.getInstance();
        String[] arguments = string.split(" ");

        if(arguments.length < 2 || arguments.length > 3 ||
            arguments.length == 3 && !(Arrays.asList(new String[] {"hotbar","inv","chest"}).contains(arguments[1]))) {
            this.invalid();
            return;
        }

        if(Arrays.asList(new String[] {"hotbar","inv","chest"}).contains(arguments[1]) && arguments.length < 3) {
            this.invalid();
            return;
        } if(arguments[1].equals("hotbar")) {
            InventoryHelper.clickHotbar(MCStringUtils.tryParse(arguments[2]));
        } else if(arguments.length == 2 && (mc.currentScreen == null)) {
            InventoryHelper.clickHotbar(MCStringUtils.tryParse(arguments[1]));
        } else if(arguments[1].equals("inv")) {
            mc.player.container.slotClick(MCStringUtils.tryParse(arguments[2]), 0, ClickType.PICKUP, mc.player);
        } else if(arguments[1].equals("chest")) {
            mc.playerController.windowClick(mc.player.container.windowId, MCStringUtils.tryParse(arguments[2]), 0, ClickType.PICKUP, mc.player);
        } else if(arguments.length == 2 && !(mc.currentScreen == null)) {
            mc.playerController.windowClick(mc.player.container.windowId, MCStringUtils.tryParse(arguments[1]), 0, ClickType.PICKUP, mc.player);
        }
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\click (hotbar | inv | chest) [slot]"));
    }
}
