package org.tfsmp.smptradinghouse.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.Arrays;
import java.util.List;

public class TradingMenuGUI extends GUI
{
    private static SMPTradingHouse plugin = SMPTradingHouse.getPlugin();

    private static final List<Integer> blank = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 17, 18, 19, 20, 21, 23, 24, 25, 26);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    public TradingMenuGUI()
    {
        super(ChatColor.DARK_PURPLE + "Trading Menu", 27);
        for (int b : blank)
        {
            super.setSlot(b, bitem);
        }
        super.setSlot(10, SUtil.createNamedItem(Material.DIAMOND_BLOCK, ChatColor.DARK_GREEN + "Trading House"));
        super.setSlot(13, SUtil.createNamedItem(Material.EMERALD_BLOCK, ChatColor.DARK_RED + "My Trades"));
        super.setSlot(16, SUtil.createNamedItem(Material.IRON_BLOCK, ChatColor.GREEN + "Pickup"));
        super.setSlot(22, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Exit"));
    }
}