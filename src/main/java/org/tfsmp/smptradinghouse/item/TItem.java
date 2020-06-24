package org.tfsmp.smptradinghouse.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.player.SPlayer;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.ArrayList;
import java.util.List;

public class TItem
{
    private static SMPTradingHouse plugin = SMPTradingHouse.getPlugin();

    @Getter
    private final int id;

    @Getter
    private final ItemStack item;

    @Getter
    @Setter
    private int timeRemaining;

    @Getter
    private final String vendor;

    @Getter
    private List<ItemStack> offers;

    private TItem(int id, ItemStack item, int timeRemaining, String vendor, List<ItemStack> offers)
    {
        this.id = id;
        this.item = item;
        this.timeRemaining = timeRemaining;
        this.vendor = vendor;
        this.offers = offers;
    }

    public void saveData()
    {
        plugin.trading.set(id + ".item", item);
        plugin.trading.set(id + ".timeRemaining", timeRemaining);
        plugin.trading.set(id + ".vendor", vendor);
        plugin.trading.set(id + ".offers", offers);
        plugin.trading.save();
    }

    public void addOffer(ItemStack stack, Player requester)
    {
        List<String> lore;
        if (!stack.hasItemMeta())
            lore = new ArrayList<>();
        else
            lore = stack.getItemMeta().hasLore() ? stack.getItemMeta().getLore() : new ArrayList<>();
        lore.add(requester.getName());
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        offers.add(stack);
    }

    public void delete()
    {
        plugin.trading.set(String.valueOf(id), null);
        plugin.trading.save();
    }

    public int getOfferAmount()
    {
        return offers.size();
    }

    public void clearOffers()
    {
        offers.clear();
    }

    public ItemStack getInfoItem()
    {
        ItemStack modified = item.clone();
        ItemMeta meta = modified.getItemMeta();
        List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        lore.add("");
        lore.add(ChatColor.GOLD + "Vendor: " + ChatColor.GREEN + vendor);
        lore.add(ChatColor.GOLD + "Time remaining: " + ChatColor.GREEN + SUtil.formatTime(timeRemaining));
        lore.add(ChatColor.GOLD + "" + getOfferAmount() + " Offer" + (getOfferAmount() == 1 ? "" : "s"));
        lore.add(ChatColor.DARK_GRAY + "ID: " + id);
        meta.setLore(lore);
        modified.setItemMeta(meta);
        return modified;
    }

    public boolean inConfig()
    {
        return plugin.trading.contains(String.valueOf(id));
    }

    public static TItem getItem(int id)
    {
        if (!plugin.trading.contains(String.valueOf(id)))
            return null;
        return new TItem(id,
                plugin.trading.getItemStack(id + ".item"),
                plugin.trading.getInt(id + ".timeRemaining"),
                plugin.trading.getString(id + ".vendor"),
                (List<ItemStack>) plugin.trading.getList(id + ".offers"));
    }

    public static TItem createItem(ItemStack stack, int time, Player player)
    {
        int id = SUtil.randomInteger(0, 99999999);
        TItem item;
        do
        {
            item = new TItem(id, stack, time, player.getName(), new ArrayList<>());
            id = SUtil.randomInteger(0, 99999999);
        }
        while (TItem.getItem(id) != null);

        SPlayer sPlayer = SPlayer.getPlayer(player);
        sPlayer.addTrade(item);
        sPlayer.saveData();

        return item;
    }
}