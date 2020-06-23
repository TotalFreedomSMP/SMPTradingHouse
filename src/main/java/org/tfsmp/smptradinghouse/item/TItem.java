package org.tfsmp.smptradinghouse.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.player.SPlayer;
import org.tfsmp.smptradinghouse.util.SLog;
import org.tfsmp.smptradinghouse.util.SUtil;

import java.util.ArrayList;
import java.util.Collection;
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
    private final List<ItemStack> offers;

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

    public static TItem getItem(int id)
    {
        if (!plugin.trading.contains(String.valueOf(id)))
            return null;
        ConfigurationSection cs = plugin.trading.getConfigurationSection(id + ".offers");
        List<ItemStack> stacks = new ArrayList<>();
        if (cs == null)
        {
            stacks = new ArrayList<>();
        }
        else
        {
            for (String key : cs.getKeys(false))
            {
                stacks.add(plugin.trading.getItemStack(id + ".offers." + key));
            }
        }
        return new TItem(id,
                plugin.trading.getItemStack(id + ".item"),
                plugin.trading.getInt(id + ".timeRemaining"),
                plugin.trading.getString(id + ".vendor"),
                stacks);
    }

    public static TItem createItem(ItemStack stack, int time, Player player)
    {
        TItem item = new TItem(SUtil.randomInteger(0, 99999999), stack, time, player.getName(), new ArrayList<>());

        SPlayer sPlayer = SPlayer.getPlayer(player);
        sPlayer.addTrade(item);
        sPlayer.saveData();

        return item;
    }
}