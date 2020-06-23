package org.tfsmp.smptradinghouse.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SUtil
{
    public static String formatTime(int seconds)
    {
        int minutes = 0, hours = 0, days = 0;
        while (seconds >= 60)
        {
            seconds -= 60;
            minutes += 1;
        }
        while (minutes >= 60)
        {
            minutes -= 60;
            hours += 1;
        }
        while (hours >= 24)
        {
            hours -= 24;
            days += 1;
        }
        return days + "d " + hours + "h " + minutes + "m " + seconds + "s ";
    }

    public static ItemStack createNamedItem(Material material, String name)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }

    public static int randomInteger(int min, int max)
    {
        int range = max - min + 1;
        int value = (int)(Math.random() * range) + min;
        return value;
    }
}