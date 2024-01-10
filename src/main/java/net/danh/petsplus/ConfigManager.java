package net.danh.petsplus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

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

    public String color(String key) {
        return ChatColor.translateAlternateColorCodes('&', key);
    }

    public List<String> listColor(List<String> key) {
        return key.stream().map(this::color).collect(Collectors.toList());
    }

    public String getPetType(String key) {
        return this.getString("pets." + key);
    }

    public FileConfiguration getConfig() {
        return PetsPlus.getInstance().getConfig();
    }
}
