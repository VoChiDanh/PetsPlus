package net.danh.petsplus.gui;

import net.danh.petsplus.PetsPlus;
import net.danh.petsplus.pet.PetType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PetSelection extends Gui {

    public PetSelection() {
        super(PetsPlus.getInstance().getConfigManager().getGuiSetting("titles.main"), 36);
    }

    @Override
    public void populateInventory(Player p, Inventory inv) {
        int index = 10;
        for (PetType type : PetType.values()) {
            if (!type.isEnabled())
                continue;

            if (index == 17)
                index += 2;

            inv.setItem(index, type.createSkull(p.hasPermission(type.getPermission())));
            index++;
        }
    }

    @Override
    public void onClick(Player player, ItemStack item) {
        if (item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getType() == Material.PLAYER_HEAD) {
            String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());

            for (PetType type : PetType.values()) {
                if (type.getName().equals(name)) {
                    if (!player.hasPermission(type.getPermission())) {
                        player.sendMessage(PetsPlus.getInstance().getConfigManager().getMessage("noPermission"));
                        player.closeInventory();
                        return;
                    }

                    if (PetsPlus.getInstance().getPetManager().getPet(player) != null) {
                        return;
                    }

                    PetsPlus.getInstance().getPetManager().spawnPet(player, type);

                    player.sendMessage(PetsPlus.messageArgs("spawnedPet", type.getName()));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5, 10);
                    player.closeInventory();
                }
            }
        }
    }

}