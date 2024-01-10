package net.danh.petsplus;

import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {

    private final String prefix;

    public ConfigManager() {
        FileConfiguration config = SimpleConfigurationManager.get().get("config.yml");
        this.prefix = config.contains("messages.prefix") ? (this.getString("messages.prefix") + " ") : "";
    }

    public String getString(String key) {
        String msg = SimpleConfigurationManager.get().get("config.yml").getString(key);
        if (msg == null) {
            return ChatColor.RED + key;
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String getMessage(String key) {
        return prefix + this.getString("messages." + key);
    }

    public boolean getSetting(String key) {
        return SimpleConfigurationManager.get().get("config.yml").getBoolean("settings." + key);
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
        return SimpleConfigurationManager.get().get("config.yml");
    }
}
