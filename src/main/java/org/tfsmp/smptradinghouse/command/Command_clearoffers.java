package org.tfsmp.smptradinghouse.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.tfsmp.smptradinghouse.item.TItem;

public class Command_clearoffers implements CommandExecutor
{
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
        int id;
        try
        {
            id = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex)
        {
            sender.sendMessage(ChatColor.RED + "Please enter a valid ID!");
            return true;
        }
        TItem item = TItem.getItem(id);
        if (item == null)
        {
            sender.sendMessage(ChatColor.GRAY + "That item does not exist.");
            return true;
        }
        item.clearOffers();
        item.saveData();
        sender.sendMessage(ChatColor.GREEN + "Cleared all active offers on the item with the ID: " + id);
        return true;
    }
}