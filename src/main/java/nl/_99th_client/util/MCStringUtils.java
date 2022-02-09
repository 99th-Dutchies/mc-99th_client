package nl._99th_client.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.item.Item;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class MCStringUtils
{
    public static String strip(String in) {
        return in.replaceAll("\u00a70|\u00a71|\u00a72|\u00a73|\u00a74|\u00a75|\u00a76|\u00a77|\u00a78|\u00a79|\u00a7a|\u00a7b|\u00a7c|\u00a7d|\u00a7e|\u00a7f|\u00a7k|\u00a7l|\u00a7m|\u00a7n|\u00a7o|\u00a7r", "");
    }

    public static String stripMagic(String in) {
        return in.replaceAll("\u00a7k", "");
    }

    public static int tryParse(String in) {
        int parsed = 0;

        try{
            parsed = Integer.parseInt(in);
        } catch(NumberFormatException e) {
            parsed = 0;
        }

        return parsed;
    }

    public static Item parseItem(String in) {
        StringReader stringreader = new StringReader(in);
        stringreader.setCursor(0);
        ItemParser itemparser = new ItemParser(stringreader, false);

        try
        {
            itemparser.parse();
            return itemparser.getItem();
        }
        catch (CommandSyntaxException commandsyntaxexception)
        {
        }

        return null;
    }

    public static List<Item> parseItems(String in) {
        List<Item> list = new ArrayList<>();
        String[] astring = in.split(",");

        for(String s : astring) {
            list.add(MCStringUtils.parseItem(s));
        }

        return list;
    }

    public static String itemsToString(List<Item> in) {
        String s = "";
        List<String> ls = new ArrayList<>();

        for(Item i : in) {
            ls.add(i.toString());
        }

        return String.join(",", ls);
    }

    public static List<IReorderingProcessor> multilineTooltip(String baseKey, int start, int end, int maxWidth) {
        List<IReorderingProcessor> tooltip = new ArrayList<>();

        for(int i = start; i <= end; i++) {
            tooltip.addAll(Minecraft.getInstance().fontRenderer.trimStringToWidth(new TranslationTextComponent(baseKey + "." + i), maxWidth));
        }

        return tooltip;
    }
}
