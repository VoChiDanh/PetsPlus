package net.danh.mmocraft.Resources;

import net.danh.mmocraft.MMOCraft;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class File {

    public static FileConfiguration getSetting() {
        return MMOCraft.getSimpleConfigurationManager().get("settings.yml");
    }

    public static FileConfiguration getConfig() {
        return MMOCraft.getSimpleConfigurationManager().get("config.yml");
    }

    public static FileConfiguration getMessage() {
        return MMOCraft.getSimpleConfigurationManager().get("message.yml");
    }

    public static FileConfiguration getCraftingGUI() {
        return MMOCraft.getSimpleConfigurationManager().get("gui-crafting.yml");
    }

    public static void reloadFiles(CommandSender c) {
        MMOCraft.getSimpleConfigurationManager().reload("settings.yml");
        MMOCraft.getSimpleConfigurationManager().reload("config.yml");
        MMOCraft.getSimpleConfigurationManager().reload("message.yml");
        MMOCraft.getSimpleConfigurationManager().reload("gui-crafting.yml");
        Chat.sendCommandSenderMessage(c, "&aReloaded");
    }

    public static void createFiles(CommandSender c) {
        MMOCraft.getSimpleConfigurationManager().build("", "settings.yml", false);
        MMOCraft.getSimpleConfigurationManager().build("", "config.yml", false);
        MMOCraft.getSimpleConfigurationManager().build("", "message.yml", false);
        MMOCraft.getSimpleConfigurationManager().build("", "gui-crafting.yml", false);
        Chat.sendCommandSenderMessage(c, "&6Created Files");
    }

    public static void saveFiles(CommandSender c) {
        MMOCraft.getSimpleConfigurationManager().save("settings.yml");
        MMOCraft.getSimpleConfigurationManager().save("config.yml");
        MMOCraft.getSimpleConfigurationManager().save("message.yml");
        MMOCraft.getSimpleConfigurationManager().save("gui-crafting.yml");
        Chat.sendCommandSenderMessage(c, "&6Saved Files");
    }
}
