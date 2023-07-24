package net.danh.petsplus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final String prefix;

    public ConfigManager() {
        FileConfiguration config = PetsPlus.getInstance().getConfig();
        this.prefix = config.contains("messages.prefix") ? (this.getString("messages.prefix") + " ") : "";
    }

    public String getString(String key) {
        String msg = PetsPlus.getInstance().getConfig().getString(key);
        if (msg == null) {
            return ChatColor.RED + key;
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String getMessage(String key) {
        return prefix + this.getString("messages." + key);
    }

    public boolean getSetting(String key) {
        return PetsPlus.getInstance().getConfig().getBoolean("settings." + key);
    }

    public String getGuiSetting(String key) {
        return this.getString("gui." + key);
    }

    public String getPetType(String key) {
        return this.getString("pets." + key);
    }
}
