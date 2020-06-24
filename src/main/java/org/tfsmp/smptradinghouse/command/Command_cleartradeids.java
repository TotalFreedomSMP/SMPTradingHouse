package org.tfsmp.smptradinghouse.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.tfsmp.smptradinghouse.SMPTradingHouse;

import java.util.ArrayList;

public class Command_cleartradeids implements CommandExecutor
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
        String name = args[0];
        if (!plugin.players.contains(name.toLowerCase()))
        {
            sender.sendMessage(ChatColor.GRAY + "That name does not have data.");
            return true;
        }
        plugin.players.set(name.toLowerCase() + ".tradeIDs", new ArrayList<>());
        plugin.players.save();
        sender.sendMessage(ChatColor.GREEN + "Cleared all trade IDs from the name: " + name);
        return true;
    }
}