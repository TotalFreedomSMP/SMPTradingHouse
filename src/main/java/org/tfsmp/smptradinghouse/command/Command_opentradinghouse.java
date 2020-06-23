package org.tfsmp.smptradinghouse.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.tfsmp.smptradinghouse.gui.TradingHouseGUI;

public class Command_opentradinghouse implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args)
    {
        if (!(sender instanceof ConsoleCommandSender) && !sender.isOp())
        {
            sender.sendMessage(ChatColor.RED + "Only console can send this command.");
            return true;
        }

        if (args.length != 1)
        {
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(ChatColor.GRAY + "Player not found.");
            return true;
        }
        new TradingHouseGUI().open(player);
        return true;
    }
}