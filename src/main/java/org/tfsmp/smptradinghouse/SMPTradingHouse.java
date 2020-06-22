package org.tfsmp.smptradinghouse;

import org.bukkit.plugin.java.JavaPlugin;
import org.tfsmp.smptradinghouse.config.Config;
import org.tfsmp.smptradinghouse.util.SLog;

public final class SMPTradingHouse extends JavaPlugin
{
    private static SMPTradingHouse plugin;
    public static SMPTradingHouse getPlugin()
    {
        return plugin;
    }

    public Config players;
    public Config trading;

    @Override
    public void onEnable()
    {
        plugin = this;
        players = new Config(plugin, "players.yml");
        trading = new Config(plugin, "trading.yml");
        SLog.info("Enabled.");
    }

    @Override
    public void onDisable()
    {
        plugin = null;
        SLog.info("Disabled.");
    }
}
