package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class InventoryInfo
{
    public static void render(Minecraft mc, MatrixStack ms) {
        MainWindow mw = mc.getMainWindow();
        int windowHeight = mw.getScaledHeight();

        // Draw main-hand item
        renderMainhand(mc, ms, windowHeight);

        // Draw off-hand item
        renderOffhand(mc, ms, windowHeight);

        // Draw armor
        renderArmor(mc, ms, windowHeight);
    }

    private static void renderMainhand(Minecraft mc, MatrixStack ms, int wh) {
        String itemString = ItemToString(mc.player.getHeldItemMainhand());
        mc.fontRenderer.drawString(ms, itemString, 1, wh - 51, -1);
    }

    private static void renderOffhand(Minecraft mc, MatrixStack ms, int wh) {
        String itemString = ItemToString(mc.player.getHeldItemOffhand());
        mc.fontRenderer.drawString(ms, itemString, 1, wh - 41, -1);
    }

    private static void renderArmor(Minecraft mc, MatrixStack ms, int wh) {
        String itemString;
        int j = 0;

        Iterable<ItemStack> armor = mc.player.getArmorInventoryList();
        for(ItemStack i : armor) {
            if(!i.isEmpty()){
                itemString = ItemToString(i);
                mc.fontRenderer.drawString(ms, itemString, 1, wh - (61 + (j * 10)), -1);
            }
            j++;
        }
    }

    private static String ItemToString(ItemStack itemStack) {
        String s = "";
        Item item = itemStack.getItem();

        if(item == Items.AIR) return "";

        if(itemStack.isStackable()) {
            s = s + itemStack.getCount() + " ";
        }

        s = s + item.getName().getString();

        if(itemStack.isDamageable()) {
            s = s + " (" + (itemStack.getMaxDamage() - itemStack.getDamage()) + "/" + itemStack.getMaxDamage() + ")";
        }

        return s;
    }
}
