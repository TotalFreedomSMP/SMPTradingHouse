package org.tfsmp.smptradinghouse.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
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
    @Setter
    private int purchaseCount;

    @Getter
    @Setter
    private int sellCount;

    @Getter
    private List<Integer> tradeIDs;

    private SPlayer(Player player, int purchaseCount, int sellCount, List<Integer> tradeIDs)
    {
        this.player = player;
        this.purchaseCount = purchaseCount;
        this.sellCount = sellCount;
        this.tradeIDs = tradeIDs;
    }

    public void saveData()
    {
        plugin.players.set(player.getName().toLowerCase() + ".name", player.getName());
        plugin.players.set(player.getName().toLowerCase() + ".purchaseCount", purchaseCount);
        plugin.players.set(player.getName().toLowerCase() + ".sellCount", sellCount);
        plugin.players.set(player.getName().toLowerCase() + ".tradeIDs", tradeIDs);
        plugin.players.save();
    }

    public void addTrade(TItem item)
    {
        tradeIDs.add(item.getId());
    }

    public static SPlayer getPlayer(Player player)
    {
        if (plugin.players.contains(player.getName().toLowerCase()))
        {
            return new SPlayer(player,
                    plugin.players.getInt(player.getName().toLowerCase() + ".purchaseCount"),
                    plugin.players.getInt(player.getName().toLowerCase() + ".sellCount"),
                    plugin.players.getIntegerList(player.getName().toLowerCase() + ".tradeIDs"));
        }
        SLog.info("Creating player data entry for " + player.getName());
        return new SPlayer(player, 0, 0, new ArrayList<>());
    }
}