package org.tfsmp.smptradinghouse.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateTradeGUI extends GUI
{
    private static final List<Integer> blank = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 17, 18, 19, 20, 24, 25, 26);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    public CreateTradeGUI()
    {
        super(ChatColor.DARK_GRAY + "Create Trade", 27);
        for (int b : blank)
        {
            super.setSlot(b, bitem);
        }
        super.setSlot(10, SUtil.createNamedItem(Material.LIME_WOOL, ChatColor.GREEN + "Create"));
        super.setSlot(13, SUtil.createNamedItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Click an item in!"));
        super.setSlot(16, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.RED + "Cancel"));
        super.setSlot(21, SUtil.createNamedItem(Material.LIME_WOOL, ChatColor.GOLD + "30m"));
        super.setSlot(22, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "6h"));
        super.setSlot(23, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "1d"));
    }
}
