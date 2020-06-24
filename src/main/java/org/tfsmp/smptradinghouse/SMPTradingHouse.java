package org.tfsmp.smptradinghouse;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.tfsmp.smptradinghouse.command.Command_clearoffers;
import org.tfsmp.smptradinghouse.command.Command_cleartradeids;
import org.tfsmp.smptradinghouse.command.Command_opentradingmenu;
import org.tfsmp.smptradinghouse.command.Command_tradinghousetest;
import org.tfsmp.smptradinghouse.config.Config;
import org.tfsmp.smptradinghouse.gui.GUIInteraction;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.player.SPlayer;
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
    public BukkitTask timeRemainingClock;

    @Override
    public void onEnable()
    {
        plugin = this;
        players = new Config(plugin, "players.yml");
        trading = new Config(plugin, "trading.yml");
        loadCommands();
        loadListeners();
        startTimeRemainingClock();
        SLog.info("Enabled.");
    }

    @Override
    public void onDisable()
    {
        plugin = null;
        stopTimeRemainingClock();
        SLog.info("Disabled.");
    }

    private void loadCommands()
    {
        this.getCommand("opentradingmenu").setExecutor(new Command_opentradingmenu());
        this.getCommand("tradinghousetest").setExecutor(new Command_tradinghousetest());
        this.getCommand("clearoffers").setExecutor(new Command_clearoffers());
        this.getCommand("cleartradeids").setExecutor(new Command_cleartradeids());
    }

    private void loadListeners()
    {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new GUIInteraction(), this);
    }

    private void startTimeRemainingClock()
    {
        timeRemainingClock = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (String key : plugin.trading.getKeys(false))
                {
                    TItem item = TItem.getItem(Integer.parseInt(key));
                    item.setTimeRemaining(item.getTimeRemaining() - 1);
                    item.saveData();
                    if (plugin.trading.getInt(key + ".timeRemaining") <= 0)
                    {
                        SPlayer player = SPlayer.getOfflinePlayer(item.getVendor());
                        player.addPickupItem(item.getItem());
                        player.removeTrade(item);
                        player.saveData();
                        plugin.trading.set(String.valueOf(item.getId()), null);
                        plugin.trading.save();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private void stopTimeRemainingClock()
    {
        timeRemainingClock.cancel();
    }
}
