package org.tfsmp.smptradinghouse.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.tfsmp.smptradinghouse.SMPTradingHouse;

public class JoinListener implements Listener
{
    private SMPTradingHouse plugin;
    public JoinListener()
    {
        this.plugin = SMPTradingHouse.getPlugin();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        SPlayer.getPlayer(e.getPlayer());
    }
}