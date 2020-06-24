package org.tfsmp.smptradinghouse.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.tfsmp.smptradinghouse.gui.TradingMenuGUI;

public class Command_trade implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args)
    {
        if (args.length > 1)
        {
            return false;
        }
        if (args.length == 1)
        {
            if (!(sender instanceof ConsoleCommandSender) && !sender.isOp())
            {
                sender.sendMessage(ChatColor.RED + "Only console and ops can send this command.");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            new TradingMenuGUI().open(player);
            return true;
        }
        if (sender instanceof ConsoleCommandSender)
        {
            sender.sendMessage(ChatColor.RED + "Console cannot use this command.");
            return true;
        }
        Player player = (Player) sender;
        new TradingMenuGUI().open(player);
        return true;
    }
}