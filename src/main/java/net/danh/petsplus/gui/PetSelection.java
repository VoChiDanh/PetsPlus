package net.danh.petsplus.gui;

import net.danh.petsplus.ConfigManager;
import net.danh.petsplus.PetsPlus;
import net.danh.petsplus.util.ItemBuilder;
import net.danh.petsplus.util.SkullCreator;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PetSelection extends Gui {

    private final HashMap<Integer, String> slot = new HashMap<>();
    private final HashMap<ItemStack, Integer> item = new HashMap<>();

    public PetSelection() {
        super(PetsPlus.getInstance().getConfigManager().getGuiSetting("titles.main.title"), Integer.parseInt(PetsPlus.getInstance().getConfigManager().getGuiSetting("titles.main.size")));
    }

    @Override
    public void populateInventory(Player p, Inventory inv) {
        int index = 0;
        for (String type : PetsPlus.getInstance().getConfig().getConfigurationSection("pets").getKeys(false)) {
            boolean enable = Boolean.parseBoolean(new ConfigManager().getPetType(type + ".enable"));
            String name = new ConfigManager().getPetType(type + ".name");
            String id = new ConfigManager().getPetType(type + ".skullTexture");
            inv.setItem(index, new ItemBuilder(SkullCreator.itemFromBase64(id)).setName(new ConfigManager().getString(name)).get());
            item.put(new ItemBuilder(SkullCreator.itemFromBase64(id)).setName(new ConfigManager().getString(name)).get(), index);
            slot.put(index, type);
            index++;
        }
    }

    @Override
    public void onClick(Player player, ItemStack item) {
        if (this.item.containsKey(item)) {
            String type = slot.get(this.item.get(item));
            boolean enable = Boolean.parseBoolean(new ConfigManager().getPetType(type + ".enable"));
            String dname = new ConfigManager().getPetType(type + ".name");
            String id = new ConfigManager().getPetType(type + ".skullTexture");
            if (!player.hasPermission("pet." + type.toLowerCase())) {
                player.sendMessage(PetsPlus.getInstance().getConfigManager().getMessage("noPermission"));
                player.closeInventory();
                return;
            }

            if (PetsPlus.getInstance().getPetManager().getPet(player) != null) {
                return;
            }

            PetsPlus.getInstance().getPetManager().spawnPet(player, type);

            player.sendMessage(PetsPlus.messageArgs("spawnedPet", dname));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5, 10);
            player.closeInventory();
        }
    }

}