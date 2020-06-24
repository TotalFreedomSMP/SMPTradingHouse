package org.tfsmp.smptradinghouse.gui;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.util.SLog;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TradingHouseGUI extends GUI
{
    private static SMPTradingHouse plugin = SMPTradingHouse.getPlugin();

    private static final List<Integer> border = Arrays.asList(0, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 17, 26, 35, 44, 45, 47, 48, 50, 51, 52, 53);
    private static final List<Integer> listing = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    private final List<TItem> items = new ArrayList<>();

    @Getter
    private final int page;

    public TradingHouseGUI(int page)
    {
        super(ChatColor.DARK_GREEN + "Trading House", 54);
        this.page = page;
        for (String sid : plugin.trading.getKeys(false))
        {
            int id = Integer.parseInt(sid);
            items.add(TItem.getItem(id));
        }
        super.setSlot(1, SUtil.createNamedItem(Material.PAPER, ChatColor.GREEN + "Page " + page));
        for (int b : border)
        {
            super.setSlot(b, bitem);
        }
        for (int i = 0; i < listing.size(); i++)
        {
            TItem item;
            try
            {
                item = items.get(((page - 1) * 27) + i);
            }
            catch (IndexOutOfBoundsException ex)
            {
                continue;
            }
            if (item == null)
                continue;
            ItemStack modified = item.getItem().clone();
            ItemMeta meta = modified.getItemMeta();
            List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
            lore.add("");
            lore.add(ChatColor.GOLD + "Vendor: " + ChatColor.GREEN + item.getVendor());
            lore.add(ChatColor.GOLD + "Time remaining: " + ChatColor.GREEN + SUtil.formatTime(item.getTimeRemaining()));
            lore.add(ChatColor.GOLD + "" + item.getOfferAmount() + " Offer" + (item.getOfferAmount() == 1 ? "" : "s"));
            lore.add(ChatColor.DARK_GRAY + "ID: " + item.getId());
            meta.setLore(lore);
            modified.setItemMeta(meta);
            super.setSlot(listing.get(i), modified);
        }
        if (page == 1)
            super.setSlot(46, bitem);
        else
            super.setSlot(46, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Previous Page"));
        super.setSlot(49, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Back"));
        try
        {
            items.get(page * 27);
        }
        catch (IndexOutOfBoundsException ex)
        {
            super.setSlot(52, bitem);
            return;
        }
        super.setSlot(52, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Next Page"));
    }

    public TradingHouseGUI()
    {
        this(1);
    }
}