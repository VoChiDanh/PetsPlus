package net.danh.petsplus.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class Gui {

    private final String title;
    private final int size;

    public Gui(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, size, this.getTitle());
        populateInventory(player, inv);
        player.openInventory(inv);
    }

    public abstract void populateInventory(Player player, Inventory inv);

    public abstract void onClick(Player player, ItemStack item);

    public String getTitle() {
        return title;
    }

}
