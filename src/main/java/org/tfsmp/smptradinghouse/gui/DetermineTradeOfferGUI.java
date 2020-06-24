package org.tfsmp.smptradinghouse.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.util.SLog;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.Arrays;
import java.util.List;

public class DetermineTradeOfferGUI extends GUI
{
    private static final List<Integer> blank = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 15, 17, 18, 19, 20, 21, 23, 24, 25, 26);
    public static final List<Integer> ITEMS = Arrays.asList(10, 11, 12, 13, 14, 15, 16);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    private TItem item;
    private ItemStack receivable;

    public DetermineTradeOfferGUI(TItem item, ItemStack receivable)
    {
        super(ChatColor.DARK_GRAY + "Accept or Deny Trade", 27);
        this.item = item;
        this.receivable = receivable;
        for (int b : blank)
        {
            super.setSlot(b, bitem);
        }
        super.setSlot(10, SUtil.createNamedItem(Material.GREEN_WOOL, ChatColor.GREEN + "Accept"));
        super.setSlot(12, item.getInfoItem());
        super.setSlot(13, SUtil.createNamedItem(Material.ARROW, ChatColor.GOLD + "->"));
        super.setSlot(14, receivable);
        super.setSlot(16, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.RED + "Deny"));
        super.setSlot(22, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Exit"));
    }
}
