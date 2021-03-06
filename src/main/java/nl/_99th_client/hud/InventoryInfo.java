package nl._99th_client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import nl._99th_client.settings.HUDSetting;
import nl._99th_client.util.ColorGradient;
import java.awt.Color;
import java.lang.Math;

public class InventoryInfo
{
    public static void renderInHands(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt) {
        // Draw main-hand item
        renderItem(mc, ms, pt, hudSetting, mc.player.getHeldItemMainhand(), hudSetting.posY(), 0);

        // Draw off-hand item
        renderItem(mc, ms, pt, hudSetting, mc.player.getHeldItemOffhand(), hudSetting.posY(), 1);
    }

    public static void renderArmour(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float pt) {
        int j = 0;

        Iterable<ItemStack> armor = mc.player.getArmorInventoryList();
        for(ItemStack i : armor) {
            if(!i.isEmpty()){
                renderItem(mc, ms, pt, hudSetting, i, hudSetting.posY() + 30, -j);
            }
            j++;
        }
    }

    private static void renderItem(Minecraft mc, MatrixStack ms, float pt, HUDSetting hudSetting, ItemStack itemStack, int yBase, int yOffset) {
        if(hudSetting.itemShow == HUDSetting.ItemShow.ICON) {
            mc.ingameGUI.renderInventoryItem(hudSetting.posX(), yBase + yOffset * 16, pt, mc.player, itemStack, itemStack.getCount() > 1 ? itemStack.getCount() + "" : "");
        } else {
            StringTextComponent itemString = ItemToString(itemStack);

            Style iss = itemStack.getDisplayName().getStyle();
            IFormattableTextComponent s;

            if(hudSetting.useDamageColor) {
                Integer itemColor = ItemToColor(itemStack);

                s = itemString.setStyle(itemColor == null && iss != Style.EMPTY ? iss : Style.EMPTY.setColor(net.minecraft.util.text.Color.fromInt(itemColor == null ? hudSetting.mainColor : itemColor)));
            } else {
                s = itemString.setStyle(iss != Style.EMPTY ? iss : Style.EMPTY.setColor(net.minecraft.util.text.Color.fromInt(hudSetting.mainColor)));
            }

            if (hudSetting.dropShadow) {
                mc.fontRenderer.func_243246_a(ms, s, hudSetting.posX(), yBase + yOffset * 10, -1);
            } else {
                mc.fontRenderer.func_243248_b(ms, s, hudSetting.posX(), yBase + yOffset * 10, -1);
            }
        }
    }

    public static void renderInventory(Minecraft mc, HUDSetting hudSetting, MatrixStack ms, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int i = 0;
        PlayerInventory inv = mc.player.inventory;

        for(Item item : mc.gameSettings._99thClientSettings.itemHUDitems) {
            int j1 = hudSetting.posX() + i * 20;
            int k1 = hudSetting.posY();

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
                String t = "";
                if(dispStack.getCount() >= 1000) {
                    t = (dispStack.getCount() / 1000) + "K";
                } else {
                    t = dispStack.getCount() + "";
                }
                mc.ingameGUI.renderInventoryItem(j1, k1, partialTicks, mc.player, dispStack, t);
                i++;
            }
        }

        RenderSystem.disableRescaleNormal();
        RenderSystem.disableBlend();
    }

    private static StringTextComponent ItemToString(ItemStack itemStack) {
        StringTextComponent s = new StringTextComponent("");
        Item item = itemStack.getItem();

        if(item == Items.AIR) return s;

        if(itemStack.isStackable()) {
            s.append(new StringTextComponent(itemStack.getCount() + " "));
        }

        s.append(itemStack.getDisplayName());

        if(itemStack.isDamageable()) {
            s.append(new StringTextComponent(" (" + (itemStack.getMaxDamage() - itemStack.getDamage()) + "/" + itemStack.getMaxDamage() + ")"));
        }

        return s;
    }

    private static Integer ItemToColor(ItemStack itemStack) {
        if(!itemStack.isDamageable()) {
            if(itemStack.getItem() instanceof ArmorItem) {
                return new Color(64, 192, 64).getRGB();
            } else {
                return null;
            }
        }

        float dur = itemStack.getMaxDamage() - itemStack.getDamage();
        float per = (dur / itemStack.getMaxDamage()) * 100;

        if(per < 0) return null;
        if(per > 100) return null;
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
