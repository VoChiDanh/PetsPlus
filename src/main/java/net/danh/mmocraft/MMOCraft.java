package net.danh.mmocraft;

import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import net.danh.mmocraft.CMD.MMOCraft_CraftingGUI;
import net.danh.mmocraft.Resources.File;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MMOCraft extends JavaPlugin {

    private static MMOCraft mmoCraft;
    private static SimpleConfigurationManager simpleConfigurationManager;
    private static InventoryManager inventoryManager;

    public static MMOCraft getMMOCraft() {
        return mmoCraft;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static SimpleConfigurationManager getSimpleConfigurationManager() {
        return simpleConfigurationManager;
    }

    @Override
    public void onEnable() {
        mmoCraft = this;
        SimpleConfigurationManager.register(mmoCraft);
        if (SimpleConfigurationManager.get() != null) {
            simpleConfigurationManager = SimpleConfigurationManager.get();
            getLogger().log(Level.FINE, "Registered XConfig System");
        }
        inventoryManager = new InventoryManager(mmoCraft);
        inventoryManager.invoke();
        new MMOCraft_CraftingGUI();
        File.createFiles(Bukkit.getConsoleSender());
    }

    @Override
    public void onDisable() {
        File.saveFiles(Bukkit.getConsoleSender());
    }
}
