package net.danh.petsplus.gui;

import net.danh.petsplus.PetsPlus;
import net.danh.petsplus.pet.Pet;
import net.danh.petsplus.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PetOptions extends Gui {

    public PetOptions() {
        super(PetsPlus.getInstance().getConfigManager().getGuiSetting("titles.options.title"), Integer.parseInt(PetsPlus.getInstance().getConfigManager().getGuiSetting("titles.options.size")));
    }

    @Override
    public void populateInventory(Player player, Inventory inv) {
        inv.setItem(11, new ItemBuilder(Material.SADDLE).setName(PetsPlus.guiSetting("ridePet")).get());
        inv.setItem(15, new ItemBuilder(Material.BARRIER).setName(PetsPlus.guiSetting("removePet")).get());
    }

    @Override
    public void onClick(Player player, ItemStack item) {

        if (item.getType() == Material.SADDLE) {
            player.closeInventory();
            PetsPlus.getInstance().getPetManager().getPet(player).getEntity().addPassenger(player);
            return;
        }

        if (item.getType() == Material.BARRIER) {
            Pet pet = PetsPlus.getInstance().getPetManager().getPet(player);
            PetsPlus.getInstance().getPetManager().despawnPet(player);

            player.sendMessage(PetsPlus.messageArgs("despawnedPet", pet.getName()));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5, 10);
            player.closeInventory();
        }
    }
}
