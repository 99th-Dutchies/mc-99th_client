package nl._99th_dutchclient.util;

import net.minecraft.client.Minecraft;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InventoryHelper {
    public static void clickHotbar(int slot) {
        Minecraft mc = Minecraft.getInstance();
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);

        stpe.schedule(() -> { mc.player.inventory.currentItem = slot; }, 100, TimeUnit.MILLISECONDS);
        stpe.schedule(() -> { mc.rightClickMouse(); }, 200, TimeUnit.MILLISECONDS);
    }
}
