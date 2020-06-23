package org.tfsmp.smptradinghouse;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.tfsmp.smptradinghouse.command.Command_opentradinghouse;
import org.tfsmp.smptradinghouse.command.Command_tradinghousetest;
import org.tfsmp.smptradinghouse.config.Config;
import org.tfsmp.smptradinghouse.gui.GUIInteraction;
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
        loadCommands();
        loadListeners();
        SLog.info("Enabled.");
    }

    @Override
    public void onDisable()
    {
        plugin = null;
        SLog.info("Disabled.");
    }

    private void loadCommands()
    {
        this.getCommand("opentradinghouse").setExecutor(new Command_opentradinghouse());
        this.getCommand("tradinghousetest").setExecutor(new Command_tradinghousetest());
    }

    private void loadListeners()
    {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new GUIInteraction(), this);
    }
}
