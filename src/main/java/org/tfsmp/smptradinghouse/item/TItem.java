package org.tfsmp.smptradinghouse.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.player.SPlayer;
import org.tfsmp.smptradinghouse.util.SLog;

import java.util.ArrayList;

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

    private TItem(int id, ItemStack item, int timeRemaining, String vendor)
    {
        this.id = id;
        this.item = item;
        this.timeRemaining = timeRemaining;
        this.vendor = vendor;
    }

    public void saveData()
    {
        plugin.trading.set(id + ".item", item);
        plugin.trading.set(id + ".timeRemaining", timeRemaining);
        plugin.trading.set(id + ".vendor", vendor);
        plugin.trading.save();
    }

    public static TItem getItem(int id)
    {
        if (!plugin.trading.contains(String.valueOf(id)))
        {
            return null;
        }
        return new TItem(id,
                plugin.players.getItemStack(id + ".item"),
                plugin.players.getInt(id + ".timeRemaining"),
                plugin.players.getString(id + ".vendor"));
    }
}