package nl._99th_dutchclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import nl._99th_dutchclient.util.ColorGradient;
import java.awt.Color;
import java.lang.Math;

public class InventoryInfo
{
    public static void render(Minecraft mc, MatrixStack ms, float pt) {
        // Draw main-hand item
        renderMainhand(mc, ms);

        // Draw off-hand item
        renderOffhand(mc, ms);

        // Draw armor
        renderArmor(mc, ms);

        // Draw inventory items
        renderInventory(mc, pt, ms);
    }

    private static void renderMainhand(Minecraft mc, MatrixStack ms) {
        String itemString = ItemToString(mc.player.getHeldItemMainhand());
        int itemColor = ItemToColor(mc.player.getHeldItemMainhand());
        mc.fontRenderer.drawStringWithShadow(ms, itemString, 1, 51, itemColor);
    }

    private static void renderOffhand(Minecraft mc, MatrixStack ms) {
        String itemString = ItemToString(mc.player.getHeldItemOffhand());
        int itemColor = ItemToColor(mc.player.getHeldItemOffhand());
        mc.fontRenderer.drawStringWithShadow(ms, itemString, 1, 61, itemColor);
    }

    private static void renderArmor(Minecraft mc, MatrixStack ms) {
        String itemString;
        int itemColor;
        int j = 0;

        Iterable<ItemStack> armor = mc.player.getArmorInventoryList();
        for(ItemStack i : armor) {
            if(!i.isEmpty()){
                itemString = ItemToString(i);
                itemColor = ItemToColor(i);
                mc.fontRenderer.drawStringWithShadow(ms, itemString, 1, 106 - (j * 10), itemColor);
            }
            j++;
        }
    }

    public static void renderInventory(Minecraft mc, float partialTicks, MatrixStack ms) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int i = 0;
        PlayerInventory inv = mc.player.inventory;

        for(Item item : mc.gameSettings.itemHUDitems) {
            int j1 = mc.getMainWindow().getScaledWidth() / 2 - 78 + (i + 10) * 20;
            int k1 = mc.getMainWindow().getScaledHeight() - 16 - 3;

            ItemStack invStack;
            ItemStack dispStack = new ItemStack(item);

            // Handle mainInventory
            for(int j = 0; j < inv.mainInventory.size(); j++) {
                invStack = inv.mainInventory.get(j);
                if(invStack.isItemEqual(dispStack) && invStack.getCount() > 0) {
                    dispStack.setCount(dispStack.getCount() + invStack.getCount());
                }
            }
            // Handle Off Hand
            invStack = mc.player.getHeldItemOffhand();
            if(invStack.isItemEqual(dispStack) && invStack.getCount() > 0) {
                dispStack.setCount(dispStack.getCount() + invStack.getCount());
            }
            // Handle Armour
            Iterable<ItemStack> armor = mc.player.getArmorInventoryList();
            for(ItemStack armorStack : armor) {
                if(!armorStack.isEmpty()) {
                    if (armorStack.isItemEqual(dispStack) && armorStack.getCount() > 0) {
                        dispStack.setCount(dispStack.getCount() + armorStack.getCount());
                    }
                }
            }

            dispStack.setCount(dispStack.getCount() - 1);

            if(dispStack.getCount() > 0) {
                mc.ingameGUI.renderInventoryItem(j1, k1, partialTicks, mc.player, dispStack, dispStack.getCount() + "");
                i++;
            }
        }

        RenderSystem.disableRescaleNormal();
        RenderSystem.disableBlend();
    }

    private static String ItemToString(ItemStack itemStack) {
        String s = "";
        Item item = itemStack.getItem();

        if(item == Items.AIR) return "";

        if(itemStack.isStackable()) {
            s = s + itemStack.getCount() + " ";
        }

        s = s + itemStack.getDisplayName().getString();

        if(itemStack.isDamageable()) {
            s = s + " (" + (itemStack.getMaxDamage() - itemStack.getDamage()) + "/" + itemStack.getMaxDamage() + ")";
        }

        return s;
    }

    private static int ItemToColor(ItemStack itemStack) {
        if(!itemStack.isDamageable()) {
            if(itemStack.getItem() instanceof ArmorItem) {
                return new Color(64, 192, 64).getRGB();
            } else {
                return -1;
            }
        }

        float dur = itemStack.getMaxDamage() - itemStack.getDamage();
        float per = (dur / itemStack.getMaxDamage()) * 100;

        if(per < 0) return -1;
        if(per > 100) return -1;
        if(per == 100) return new Color(64, 192, 64).getRGB();

        ColorGradient[] ranges = new ColorGradient[] {
            new ColorGradient(0, 10, new Color(170, 0, 0), new Color(213, 43, 43)),
            new ColorGradient(10, 20, new Color(213, 43, 43), new Color(255, 85, 85)),
            new ColorGradient(20, 30, new Color(255, 85, 85), new Color(255, 128, 43)),
            new ColorGradient(30, 40, new Color(255, 128, 43), new Color(255, 170, 0)),
            new ColorGradient(40, 50, new Color(255, 170, 0), new Color(255, 213, 43)),
            new ColorGradient(50, 60, new Color(255, 213, 43), new Color(255, 255, 85)),
            new ColorGradient(60, 70, new Color(255, 255, 85), new Color(198, 255, 85)),
            new ColorGradient(70, 80, new Color(198, 255, 85), new Color(142, 255, 85)),
            new ColorGradient(80, 90, new Color(142, 255, 85), new Color(85, 255, 85)),
            new ColorGradient(90, 100, new Color(85, 255, 85), new Color(64, 192, 64))
        };

        return ranges[(int) Math.floor(per / 10)].getColor(per).getRGB();
    }
}
