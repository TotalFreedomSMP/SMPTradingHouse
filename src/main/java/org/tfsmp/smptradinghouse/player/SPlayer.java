package org.tfsmp.smptradinghouse.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.util.SLog;

import java.util.ArrayList;
import java.util.List;

public class SPlayer
{
    private static SMPTradingHouse plugin = SMPTradingHouse.getPlugin();

    @Getter
    private final Player player;

    @Getter
    private final String name;

    @Getter
    @Setter
    private int purchaseCount;

    @Getter
    @Setter
    private int sellCount;

    @Getter
    private List<Integer> tradeIDs;

    @Getter
    private List<ItemStack> pickup;

    private SPlayer(Player player, String name, int purchaseCount, int sellCount, List<Integer> tradeIDs, List<ItemStack> pickup)
    {
        this.player = player;
        this.name = name;
        this.purchaseCount = purchaseCount;
        this.sellCount = sellCount;
        this.tradeIDs = tradeIDs;
        this.pickup = pickup;
    }

    public void saveData()
    {
        if (name != null)
        {
            plugin.players.set(name.toLowerCase() + ".name", name);
            plugin.players.set(name.toLowerCase() + ".purchaseCount", purchaseCount);
            plugin.players.set(name.toLowerCase() + ".sellCount", sellCount);
            plugin.players.set(name.toLowerCase() + ".tradeIDs", tradeIDs);
            plugin.players.set(name.toLowerCase() + ".pickup", pickup);
            plugin.players.save();
            return;
        }
        plugin.players.set(player.getName().toLowerCase() + ".name", player.getName());
        plugin.players.set(player.getName().toLowerCase() + ".purchaseCount", purchaseCount);
        plugin.players.set(player.getName().toLowerCase() + ".sellCount", sellCount);
        plugin.players.set(player.getName().toLowerCase() + ".tradeIDs", tradeIDs);
        plugin.players.set(player.getName().toLowerCase() + ".pickup", pickup);
        plugin.players.save();
    }

    public void addTrade(TItem item)
    {
        tradeIDs.add(item.getId());
    }

    public void removeTrade(TItem item)
    {
        tradeIDs.remove(new Integer(item.getId()));
    }

    public void addPickupItem(ItemStack stack)
    {
        pickup.add(stack);
    }

    public void removePickupItem(ItemStack stack)
    {
        pickup.remove(stack);
    }

    public static SPlayer getPlayer(Player player)
    {
        if (plugin.players.contains(player.getName().toLowerCase()))
        {
            return new SPlayer(player,
                    null,
                    plugin.players.getInt(player.getName().toLowerCase() + ".purchaseCount"),
                    plugin.players.getInt(player.getName().toLowerCase() + ".sellCount"),
                    (List<Integer>) plugin.players.getList(player.getName().toLowerCase() + ".tradeIDs"),
                    (List<ItemStack>) plugin.players.getList(player.getName().toLowerCase() + ".pickup"));
        }
        SLog.info("Creating player data entry for " + player.getName());
        SPlayer sPlayer = new SPlayer(player, null, 0, 0, new ArrayList<>(), new ArrayList<>());
        sPlayer.saveData();
        return sPlayer;
    }

    public static SPlayer getOfflinePlayer(String name)
    {
        if (!plugin.players.contains(name.toLowerCase()))
            return null;
        return new SPlayer(null,
                name,
                plugin.players.getInt(name.toLowerCase() + ".purchaseCount"),
                plugin.players.getInt(name.toLowerCase() + ".sellCount"),
                (List<Integer>) plugin.players.getList(name.toLowerCase() + ".tradeIDs"),
                (List<ItemStack>) plugin.players.getList(name.toLowerCase() + ".pickup"));
    }
}