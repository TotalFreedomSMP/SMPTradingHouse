package org.tfsmp.smptradinghouse.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUI
{
    // name of the gui
    private String name;

    // slot count
    private int slots;

    // its contents
    private List<ItemStack> contents;

    // the actual inventory
    @Getter
    private Inventory gui;

    public GUI(String name, int slots)
    {
        this.name = name;
        this.slots = slots;
        this.contents = new ArrayList<>();
        this.gui = Bukkit.createInventory(null, slots, name);
    }

    public void setSlot(int i, ItemStack stack)
    {
        contents.add(stack);
        gui.setItem(i, stack);
    }

    public void open(Player player)
    {
        player.openInventory(gui);
    }
}