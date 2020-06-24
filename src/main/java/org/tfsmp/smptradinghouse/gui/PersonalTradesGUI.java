package org.tfsmp.smptradinghouse.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.player.SPlayer;
import org.tfsmp.smptradinghouse.util.SLog;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalTradesGUI extends GUI
{
    private static final List<Integer> blank = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 23, 24, 25, 26);
    public static final List<Integer> ITEMS = Arrays.asList(10, 11, 12, 13, 14, 15, 16);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    public PersonalTradesGUI(Player player)
    {
        super(ChatColor.DARK_RED + "My Trades", 27);
        for (int b : blank)
        {
            super.setSlot(b, bitem);
        }
        SPlayer sPlayer = SPlayer.getPlayer(player);
        for (int i = 0; i < 7; i++)
        {
            int id;
            try
            {
                id = sPlayer.getTradeIDs().get(i);
            }
            catch (IndexOutOfBoundsException ex)
            {
                continue;
            }
            TItem ti = TItem.getItem(id);
            if (ti == null)
                continue;
            ItemStack modified = ti.getItem().clone();
            ItemMeta meta = modified.getItemMeta();
            List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
            lore.add("");
            lore.add(ChatColor.GOLD + "Vendor: " + ChatColor.GREEN + ti.getVendor());
            lore.add(ChatColor.GOLD + "Time remaining: " + ChatColor.GREEN + SUtil.formatTime(ti.getTimeRemaining()));
            lore.add(ChatColor.GOLD + "" + ti.getOfferAmount() + " Offer" + (ti.getOfferAmount() == 1 ? "" : "s"));
            lore.add(ChatColor.DARK_GRAY + "ID: " + ti.getId());
            meta.setLore(lore);
            modified.setItemMeta(meta);
            super.setSlot(ITEMS.get(i), modified);
        }
        super.setSlot(19, SUtil.createNamedItem(Material.PAPER, ChatColor.GREEN + "Create Trade"));
        super.setSlot(22, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Back"));
    }
}
