package org.tfsmp.smptradinghouse.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.player.SPlayer;
import org.tfsmp.smptradinghouse.util.SLog;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.List;

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
        if (title.equals(ChatColor.DARK_AQUA + "Create Trade Offer"))
        {
            handleOffer(e);
        }
        if (title.equals(ChatColor.DARK_RED + "My Trades"))
        {
            handlePersonalTrades(e);
        }
        if (title.equals(ChatColor.DARK_PURPLE + "Trading Menu"))
        {
            handleTradingMenu(e);
        }
        if (title.equals(ChatColor.DARK_GRAY + "Create Trade"))
        {
            handleCreateTrade(e);
        }
        if (title.startsWith(ChatColor.DARK_GRAY + "Trade Offers"))
        {
            handlePersonalTradeOffers(e);
        }
        if (title.equals(ChatColor.DARK_GRAY + "Accept or Deny Trade"))
        {
            handleDetermineTradeOffer(e);
        }
        if (title.equals(ChatColor.DARK_RED + "Pickup"))
        {
            handlePickup(e);
        }
    }

    public void handleTradingHouse(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
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
        if (e.getRawSlot() >= 10 && e.getRawSlot() <= 43)
        {
            if (!stack.getItemMeta().hasLore())
                return;
            new TradeOfferGUI(TItem.getItem(Integer.parseInt(stack.getItemMeta().getLore().get(stack.getItemMeta().getLore().size() - 1).substring(6)))).open(player);
            return;
        }
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.AQUA + "Previous Page") && e.getRawSlot() == 46)
            {
                new TradingHouseGUI(page - 1).open(player);
                return;
            }
            if (name.equals(ChatColor.AQUA + "Next Page") && e.getRawSlot() == 52)
            {
                new TradingHouseGUI(page + 1).open(player);
                return;
            }
            if (name.equals(ChatColor.AQUA + "Back") && e.getRawSlot() == 49)
            {
                new TradingMenuGUI().open(player);
            }
        }
    }

    public void handleOffer(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        Inventory pinv = player.getInventory();
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
            return;
        if (e.getRawSlot() >= 27 && e.getRawSlot() <= 62)
        {
            ItemStack insertion = inv.getItem(11);
            if (insertion == null)
                return;
            if (insertion.getType() == Material.RED_STAINED_GLASS_PANE && insertion.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click an item in!"))
            {
                if (stack.getType() == Material.AIR)
                    return;
                inv.setItem(11, pinv.getItem(e.getSlot()));
                pinv.setItem(e.getSlot(), new ItemStack(Material.AIR));
            }
            return;
        }
        if (e.getRawSlot() == 11 && stack.getType() != Material.RED_STAINED_GLASS_PANE)
        {
            if (stack.getItemMeta().hasDisplayName())
            {
                if (stack.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click an item in!"))
                {
                    return;
                }
            }
            pinv.setItem(pinv.firstEmpty(), inv.getItem(11));
            inv.setItem(11, SUtil.createNamedItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Click an item in!"));
            return;
        }
        if (!stack.hasItemMeta())
            return;
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.GREEN + "Submit") && e.getSlot() == 9)
            {
                ItemStack trading = inv.getItem(11);
                if (trading.getType() == Material.RED_STAINED_GLASS_PANE)
                {
                    if (trading.getItemMeta().hasDisplayName())
                    {
                        if (trading.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click an item in!"))
                        {
                            player.closeInventory();
                            player.sendMessage(ChatColor.RED + "Please place an item to offer for a trade!");
                            return;
                        }
                    }
                }
                TItem item = TItem.getItem(Integer.parseInt(inv.getItem(4).getItemMeta().getLore().get(inv.getItem(4).getItemMeta().getLore().size() - 1).substring(6)));
                if (item.getOfferAmount() == 7)
                {
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "This item has reached the maximum amount of offers!");
                    return;
                }
                item.addOffer(trading, player);
                item.saveData();
                Player vendor = Bukkit.getPlayer(item.getVendor());
                if (vendor != null)
                {
                    vendor.sendMessage(ChatColor.GOLD + player.getName() + " has submitted a trade offer on one of your items!");
                }
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Trade offer submitted! You will be notified whether the vendor has accepted or denied your offer.");
                return;
            }
            if (name.equals(ChatColor.RED + "Cancel") && e.getRawSlot() == 17)
            {
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "Trade offer cancelled!");
                return;
            }
        }
    }

    public void handlePersonalTrades(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        Inventory pinv = player.getInventory();
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
            return;
        if (PersonalTradesGUI.ITEMS.contains(e.getSlot()))
        {
            TItem item = TItem.getItem(Integer.parseInt(stack.getItemMeta().getLore().get(stack.getItemMeta().getLore().size() - 1).substring(6)));
            SLog.info(item.getId());
            new PersonalTradeOffersGUI(item).open(player);
        }
        if (!stack.hasItemMeta())
            return;
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.GREEN + "Create Trade") && e.getRawSlot() == 19)
            {
                SPlayer sPlayer = SPlayer.getPlayer(player);
                if (sPlayer.getTradeIDs().size() == 7)
                {
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "You've reached the maximum of trades!");
                    return;
                }
                new CreateTradeGUI().open(player);
                return;
            }
            if (name.equals(ChatColor.AQUA + "Back") && e.getRawSlot() == 22)
            {
                new TradingMenuGUI().open(player);
                return;
            }
        }
    }

    public void handleTradingMenu(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
            return;
        if (!stack.hasItemMeta())
            return;
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.DARK_GREEN + "Trading House") && e.getRawSlot() == 10)
            {
                new TradingHouseGUI().open(player);
                return;
            }
            if (name.equals(ChatColor.DARK_RED + "My Trades") && e.getRawSlot() == 13)
            {
                new PersonalTradesGUI(player).open(player);
                return;
            }
            if (name.equals(ChatColor.GREEN + "Pickup") && e.getRawSlot() == 16)
            {
                new PickupGUI(player).open(player);
                return;
            }
            if (name.equals(ChatColor.AQUA + "Exit") && e.getRawSlot() == 22)
            {
                player.closeInventory();
            }
        }
    }

    public void handleCreateTrade(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        Inventory pinv = player.getInventory();
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
            return;
        if (e.getRawSlot() >= 27 && e.getRawSlot() <= 62)
        {
            ItemStack insertion = inv.getItem(13);
            if (insertion == null)
                return;
            if (insertion.getType() == Material.RED_STAINED_GLASS_PANE && insertion.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click an item in!"))
            {
                if (stack.getType() == Material.AIR)
                    return;
                inv.setItem(13, pinv.getItem(e.getSlot()));
                pinv.setItem(e.getSlot(), new ItemStack(Material.AIR));
            }
            return;
        }
        if (e.getRawSlot() == 13 && stack.getType() != Material.RED_STAINED_GLASS_PANE)
        {
            if (stack.getItemMeta().hasDisplayName())
            {
                if (stack.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click an item in!"))
                {
                    return;
                }
            }
            pinv.setItem(pinv.firstEmpty(), inv.getItem(13));
            inv.setItem(13, SUtil.createNamedItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Click an item in!"));
            return;
        }
        if (!stack.hasItemMeta())
            return;
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.GREEN + "Create") && e.getRawSlot() == 10)
            {
                ItemStack trading = inv.getItem(13);
                if (trading.getType() == Material.RED_STAINED_GLASS_PANE)
                {
                    if (trading.getItemMeta().hasDisplayName())
                    {
                        if (trading.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click an item in!"))
                        {
                            player.closeInventory();
                            player.sendMessage(ChatColor.RED + "Please place an item to trade!");
                            return;
                        }
                    }
                }
                int time = 0;
                if (inv.getItem(21).getType() == Material.GREEN_WOOL)
                    time = 1800;
                if (inv.getItem(22).getType() == Material.GREEN_WOOL)
                    time = 21600;
                if (inv.getItem(23).getType() == Material.GREEN_WOOL)
                    time = 86400;
                TItem item = TItem.createItem(trading, time, player);
                item.saveData();
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Trade created!");
                return;
            }
            if (name.equals(ChatColor.RED + "Cancel") && e.getRawSlot() == 16)
            {
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "Trade cancelled!");
                return;
            }
            if (name.equals(ChatColor.GOLD + "30m") && e.getRawSlot() == 21)
            {
                inv.setItem(21, SUtil.createNamedItem(Material.LIME_WOOL, ChatColor.GOLD + "30m"));
                inv.setItem(22, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "6h"));
                inv.setItem(23, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "1d"));
                return;
            }
            if (name.equals(ChatColor.GOLD + "6h") && e.getRawSlot() == 22)
            {
                inv.setItem(21, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "30m"));
                inv.setItem(22, SUtil.createNamedItem(Material.LIME_WOOL, ChatColor.GOLD + "6h"));
                inv.setItem(23, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "1d"));
                return;
            }
            if (name.equals(ChatColor.GOLD + "1d") && e.getRawSlot() == 23)
            {
                inv.setItem(21, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "30m"));
                inv.setItem(22, SUtil.createNamedItem(Material.RED_WOOL, ChatColor.GOLD + "6h"));
                inv.setItem(23, SUtil.createNamedItem(Material.LIME_WOOL, ChatColor.GOLD + "1d"));
            }
        }
    }

    public void handlePersonalTradeOffers(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        Inventory pinv = player.getInventory();
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
            return;
        if (PersonalTradeOffersGUI.ITEMS.contains(e.getSlot()))
        {
            TItem item = TItem.getItem(Integer.parseInt(e.getView().getTitle().substring(21)));
            new DetermineTradeOfferGUI(item, inv.getItem(e.getRawSlot())).open(player);
            return;
        }
        if (!stack.hasItemMeta())
            return;
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.AQUA + "Back") && e.getRawSlot() == 22)
            {
                new PersonalTradesGUI(player).open(player);
            }
        }
    }

    public void handleDetermineTradeOffer(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        Inventory pinv = player.getInventory();
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
            return;
        if (!stack.hasItemMeta())
            return;
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.GREEN + "Accept") && e.getRawSlot() == 10)
            {
                SPlayer sPlayer = SPlayer.getPlayer(player);

                String req = inv.getItem(14).getItemMeta().getLore().get(0).substring(15);
                SPlayer requester = SPlayer.getOfflinePlayer(req);
                int id = Integer.parseInt(inv.getItem(12).getItemMeta().getLore().get(inv.getItem(12).getItemMeta().getLore().size() - 1).substring(6));
                TItem tiOut = TItem.getItem(id);
                ItemStack out = tiOut.getItem();
                ItemStack in = inv.getItem(14).clone();
                ItemMeta inMeta = in.getItemMeta();
                List<String> inLore = inMeta.getLore();
                inLore.remove(inLore.size() - 1);
                inMeta.setLore(inLore);
                in.setItemMeta(inMeta);

                sPlayer.addPickupItem(in);

                SLog.info("removeTrade -- start");
                sPlayer.removeTrade(tiOut);
                SLog.info("removeTrade -- stop");

                SLog.info("saveData -- start");
                sPlayer.saveData();
                SLog.info("saveData -- stop");

                requester.addPickupItem(out);
                tiOut.delete();
                requester.saveData();
                player.closeInventory();
                player.sendMessage(ChatColor.GOLD + "Trade fulfilled! Check your pickup area!");
                Player r = Bukkit.getPlayer(req);
                if (r != null)
                {
                    r.sendMessage(ChatColor.GOLD + player.getName() + " has fulfilled your trade! Check your pickup area!");
                }
            }
            if (name.equals(ChatColor.RED + "Deny") && e.getRawSlot() == 16)
            {
                int id = Integer.parseInt(inv.getItem(12).getItemMeta().getLore().get(inv.getItem(12).getItemMeta().getLore().size() - 1).substring(6));
                TItem ti = TItem.getItem(id);
                ItemStack reverted = inv.getItem(14).clone();
                ItemMeta rMeta = reverted.getItemMeta();
                List<String> rLore = rMeta.getLore();
                String req = rLore.get(0).substring(15);
                rLore.clear();
                rLore.add(req);
                rMeta.setLore(rLore);
                reverted.setItemMeta(rMeta);
                ti.getOffers().remove(reverted);
                ti.saveData();
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "Denied " + req + "'s trade offer!");
                Player r = Bukkit.getPlayer(req);
                if (r != null)
                {
                    r.sendMessage(ChatColor.RED + player.getName() + " has denied your trade offer!");
                }
            }
            if (name.equals(ChatColor.AQUA + "Exit") && e.getRawSlot() == 22)
            {
                player.closeInventory();
            }
        }
    }

    public void handlePickup(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        Inventory pinv = player.getInventory();
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
            return;
        if (e.getRawSlot() >= 10 && e.getRawSlot() <= 43)
        {
            SPlayer sPlayer = SPlayer.getPlayer(player);
            sPlayer.removePickupItem(stack);
            sPlayer.saveData();
            pinv.setItem(pinv.firstEmpty(), stack);
            inv.setItem(e.getRawSlot(), new ItemStack(Material.AIR));
            return;
        }
        if (stack.getItemMeta().hasDisplayName())
        {
            String name = stack.getItemMeta().getDisplayName();
            if (name.equals(ChatColor.AQUA + "Back") && e.getRawSlot() == 49)
            {
                new TradingMenuGUI().open(player);
            }
        }
    }
}