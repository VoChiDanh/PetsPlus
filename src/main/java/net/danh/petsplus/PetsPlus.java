package net.danh.petsplus;

import net.danh.petsplus.gui.GuiManager;
import net.danh.petsplus.gui.PetOptions;
import net.danh.petsplus.gui.PetSelection;
import net.danh.petsplus.listener.EntityListener;
import net.danh.petsplus.listener.InventoryListener;
import net.danh.petsplus.pet.PetManager;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PetsPlus extends JavaPlugin {

    private static PetsPlus instance;

    private ConfigManager configManager;
    private PetManager petManager;
    private GuiManager guiManager;

    public static PetsPlus getInstance() {
        return instance;
    }

    public static String guiSetting(String key) {
        return instance.getConfigManager().getGuiSetting(key);
    }

    public static String message(String key) {
        return instance.getConfigManager().getMessage(key);
    }

    public static String messageArgs(String key, Object... args) {
        String msg = instance.getConfigManager().getMessage(key);
        for (Object arg : args)
            msg = msg.replaceFirst("\\{\\}", arg.toString());
        return msg;
    }

    @Override
    public void onEnable() {
        instance = this;
        SimpleConfigurationManager.register(this);
        SimpleConfigurationManager.get().build("", false, "config.yml");
        this.configManager = new ConfigManager();
        this.petManager = new PetManager();
        this.guiManager = new GuiManager();

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new EntityListener(), this);

        guiManager.registerGui(PetSelection.class, new PetSelection());
        guiManager.registerGui(PetOptions.class, new PetOptions());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (petManager.isPetActive(p)) {
                    petManager.tick();
                }
            }
        }, 5, 5);
    }


    @Override
    public void onDisable() {
        petManager.despawnAll();
        SimpleConfigurationManager.get().save("config.yml");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(configManager.getMessage("playersOnly"));
                return true;
            }

            guiManager.getGui(petManager.getPet(player) != null ? PetOptions.class : PetSelection.class).open(player);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5, 10);
            return true;

        }
        if (args.length == 1) {
            if (sender.hasPermission("pp.admin")) {
                if (args[0].equalsIgnoreCase("reload")) {
                    SimpleConfigurationManager.get().reload("config.yml");
                    sender.sendMessage(new ConfigManager().color("&aReloaded"));
                    return true;
                }
            }
        }
        return true;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
