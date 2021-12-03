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
            this.handleHotbar(MCStringUtils.tryParse(arguments[2]), mc);
        } else if(arguments.length == 2 && (mc.currentScreen == null)) {
            this.handleHotbar(MCStringUtils.tryParse(arguments[1]), mc);
        } else if(arguments[1].equals("inv")) {
            this.handleInv(MCStringUtils.tryParse(arguments[2]), mc);
        } else if(arguments[1].equals("chest")) {
            this.handleChest(MCStringUtils.tryParse(arguments[2]), mc);
        } else if(arguments.length == 2 && !(mc.currentScreen == null)) {
            this.handleChest(MCStringUtils.tryParse(arguments[1]), mc);
        }
    }

    @Override
    public void invalid() {
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid command usage: \\click (hotbar | inv | chest) [slot]"));
    }

    private void handleHotbar(int slot, Minecraft mc) {
        if(slot < 0 || slot > 8) {
            mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid hotbar slot: " + slot));
        } else {
            InventoryHelper.clickHotbar(slot);
        }
    }

    private void handleInv(int slot, Minecraft mc) {
        if(slot < 0 || slot > mc.player.container.inventorySlots.size() - 1) {
            mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid inventory slot: " + slot));
        } else {
            mc.player.container.slotClick(slot, 0, ClickType.PICKUP, mc.player);
        }
    }

    private void handleChest(int slot, Minecraft mc) {
        if(slot < 0) {
            mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(TextFormatting.RED + "Invalid chest slot: " + slot));
        } else {
            mc.playerController.windowClick(mc.player.container.windowId, slot, 0, ClickType.PICKUP, mc.player);
        }
    }
}
