package org.tfsmp.smptradinghouse.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.item.TItem;

public class GUIInteraction implements Listener
{
    private SMPTradingHouse plugin;
    public GUIInteraction()
    {
        plugin = SMPTradingHouse.getPlugin();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        String title = e.getView().getTitle();
        if (title.equals(ChatColor.DARK_GREEN + "Trading House"))
        {
            handleTradingHouse(e);
        }
    }

    public void handleTradingHouse(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack stack = e.getCurrentItem();
        String pageDisplay;
        try
        {
            pageDisplay = e.getClickedInventory().getItem(1).getItemMeta().getDisplayName();
        }
        catch (NullPointerException | IndexOutOfBoundsException ex)
        {
            return;
        }
        int page = Integer.valueOf(pageDisplay.substring(7));
        if (stack == null)
            return;
        if (!stack.hasItemMeta())
            return;
        if (e.getSlot() >= 10 && e.getSlot() <= 43)
        {
            if (!stack.getItemMeta().hasLore())
                return;
            new TradeOfferGUI(TItem.getItem(Integer.parseInt(stack.getItemMeta().getLore().get(stack.getItemMeta().getLore().size() - 1).substring(6))));
            return;
        }
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.AQUA + "Previous Page") && e.getSlot() == 46)
            {
                new TradingHouseGUI(page - 1).open(player);
                return;
            }
            if (name.equals(ChatColor.AQUA + "Next Page") && e.getSlot() == 52)
            {
                new TradingHouseGUI(page + 1).open(player);
                return;
            }
            if (name.equals(ChatColor.AQUA + "Exit") && e.getSlot() == 49)
            {
                player.closeInventory();
            }
        }
    }
}