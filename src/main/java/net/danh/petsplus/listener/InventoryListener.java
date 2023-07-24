package net.danh.petsplus.listener;

import net.danh.petsplus.PetsPlus;
import net.danh.petsplus.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        for (Gui gui : PetsPlus.getInstance().getGuiManager().getGuis()) {
            if (gui.getTitle().equals(e.getView().getTitle())) {
                e.setCancelled(true);
                gui.onClick((Player) e.getWhoClicked(), e.getCurrentItem());
                return;
            }
        }
    }

}
