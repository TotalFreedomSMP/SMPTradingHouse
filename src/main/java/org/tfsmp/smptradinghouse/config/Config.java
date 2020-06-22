package org.tfsmp.smptradinghouse.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.tfsmp.smptradinghouse.SMPTradingHouse;

import java.io.File;

public class Config extends YamlConfiguration
{
    private final SMPTradingHouse plugin;
    private final File file;

    public Config(SMPTradingHouse plugin, String name)
    {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), name);

        if (!file.exists())
        {
            options().copyDefaults(true);
            plugin.saveResource(name, false);
        }
        load();
    }

    public void load()
    {
        try
        {
            super.load(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void save()
    {
        try
        {
            super.save(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}