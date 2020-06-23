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

public class TradeOfferGUI extends GUI
{
    private static final List<Integer> blank = Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 10, 12, 13, 14, 16, 18, 19, 20, 21, 22, 23, 24, 25, 26);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    private final TItem item;

    public TradeOfferGUI(TItem item)
    {
        super(ChatColor.DARK_AQUA + "Trade Offer", 27);
        this.item = item;
        for (int b : blank)
        {
            super.setSlot(b, bitem);
        }
        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName(ChatColor.AQUA + "Trade Info");
        List<String> infoLore = new ArrayList<>();
        infoLore.add(ChatColor.GOLD + "Vendor: " + ChatColor.GREEN + item.getVendor());
        infoLore.add(ChatColor.GOLD + "Time remaining: " + ChatColor.GREEN + SUtil.formatTime(item.getTimeRemaining()));
        infoLore.add(ChatColor.DARK_GRAY + "ID: " + item.getId());
        infoMeta.setLore(infoLore);
        info.setItemMeta(infoMeta);
        super.setSlot(4, info);
        super.setSlot(9, SUtil.createNamedItem(Material.LIME_WOOL, ChatColor.GREEN + "Submit"));
        super.setSlot(17, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.RED + "Cancel"));
        super.setSlot(11, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        super.setSlot(15, item.getItem());
    }
}
