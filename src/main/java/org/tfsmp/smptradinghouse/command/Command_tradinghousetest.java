package org.tfsmp.smptradinghouse.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.tfsmp.smptradinghouse.SMPTradingHouse;
import org.tfsmp.smptradinghouse.item.TItem;
import org.tfsmp.smptradinghouse.util.SUtil;

public class Command_tradinghousetest implements CommandExecutor
{
    private static SMPTradingHouse plugin = SMPTradingHouse.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args)
    {
        if (!sender.isOp())
        {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length != 1)
        {
            return false;
        }
        if (args[0].equalsIgnoreCase("clear"))
        {
            for (String id : plugin.trading.getKeys(false))
            {
                plugin.trading.set(id, null);
            }
            plugin.trading.save();
            sender.sendMessage(ChatColor.GRAY + "Cleared all items from the house.");
            return true;
        }
        if (args[0].equalsIgnoreCase("add"))
        {
            if (sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(ChatColor.RED + "Console cannot use this command.");
                return true;
            }
            Player player = (Player) sender;
            TItem.createItem(SUtil.createNamedItem(Material.COARSE_DIRT, ChatColor.GOLD + "Test Dirt!"), 60, player).saveData();
            sender.sendMessage(ChatColor.GRAY + "Created a new test item");
            return true;
        }
        return false;
    }
}