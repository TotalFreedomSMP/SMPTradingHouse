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

public class PersonalTradeOffersGUI extends GUI
{
    private static final List<Integer> blank = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 23, 24, 25, 26);
    public static final List<Integer> ITEMS = Arrays.asList(10, 11, 12, 13, 14, 15, 16);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    private TItem item;

    public PersonalTradeOffersGUI(TItem item)
    {
        super(ChatColor.DARK_GRAY + "Trade Offers - ID: " + item.getId(), 27);
        this.item = item;
        for (int b : blank)
        {
            super.setSlot(b, bitem);
        }
        for (int i = 0; i < 7; i++)
        {
            ItemStack offer;
            try
            {
                offer = item.getOffers().get(i);
            }
            catch (IndexOutOfBoundsException ex)
            {
                continue;
            }
            ItemStack modified = offer.clone();
            ItemMeta meta = modified.getItemMeta();
            List<String> lore = meta.getLore();
            String requester = lore.get(0);
            lore.clear();
            lore.add(ChatColor.GOLD + "Requester: " + ChatColor.GREEN + requester);
            meta.setLore(lore);
            modified.setItemMeta(meta);
            super.setSlot(ITEMS.get(i), modified);
        }
        super.setSlot(22, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Back"));
    }
}
