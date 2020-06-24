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

import java.util.Arrays;
import java.util.List;

public class PickupGUI extends GUI
{
    private static final List<Integer> border = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 17, 26, 35, 44, 45, 46, 47, 48, 50, 51, 52, 53);
    public static final List<Integer> listing = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);
    private static final ItemStack bitem = SUtil.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");

    public PickupGUI(Player player)
    {
        super(ChatColor.DARK_RED + "Pickup", 54);
        for (int b : border)
        {
            super.setSlot(b, bitem);
        }
        SPlayer sPlayer = SPlayer.getPlayer(player);
        for (int i = 0; i < listing.size(); i++)
        {
            ItemStack item;
            try
            {
                item = sPlayer.getPickup().get(i);
            }
            catch (IndexOutOfBoundsException ex)
            {
                continue;
            }
            super.setSlot(listing.get(i), item);
        }
        super.setSlot(49, SUtil.createNamedItem(Material.ARROW, ChatColor.AQUA + "Back"));
    }
}
