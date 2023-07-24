package net.danh.petsplus.gui;

import net.danh.petsplus.PetsPlus;
import net.danh.petsplus.pet.Pet;
import net.danh.petsplus.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PetOptions extends Gui {

    public PetOptions() {
        super(PetsPlus.getInstance().getConfigManager().getGuiSetting("titles.options"), 27);
    }

    @Override
    public void populateInventory(Player player, Inventory inv) {
        Pet pet = PetsPlus.getInstance().getPetManager().getPet(player);

        inv.setItem(10, new ItemBuilder(Material.JUKEBOX).setName(PetsPlus.guiSetting("makeSound")).get());
        inv.setItem(12, new ItemBuilder(Material.SADDLE).setName(PetsPlus.guiSetting("ridePet")).get());
        inv.setItem(16, new ItemBuilder(Material.BARRIER).setName(PetsPlus.guiSetting("removePet")).get());

        if (PetsPlus.getInstance().getConfigManager().getSetting("allowAgeChange") && pet.hasBabyOption()) {
            inv.setItem(14, new ItemBuilder(Material.WHEAT).setName(PetsPlus.guiSetting(pet.isBaby() ? "changeToAdult" : "changeToBaby")).get());
        }
    }

    @Override
    public void onClick(Player player, ItemStack item) {
        if (item.getType() == Material.JUKEBOX) {
            player.closeInventory();

            Pet pet = PetsPlus.getInstance().getPetManager().getPet(player);
            for (Player p2 : Bukkit.getOnlinePlayers()) {
                p2.playSound(pet.getLocation(), pet.getType().getSound(), SoundCategory.AMBIENT, 1f, 1f);
            }

            return;
        }

        if (item.getType() == Material.SADDLE) {
            player.closeInventory();
            PetsPlus.getInstance().getPetManager().getPet(player).addPassenger();
            return;
        }

        if (item.getType() == Material.WHEAT) {
            player.closeInventory();

            Pet pet = PetsPlus.getInstance().getPetManager().getPet(player);
            pet.setBaby(!pet.isBaby());
            return;
        }

        if (item.getType() == Material.BARRIER) {
            Pet pet = PetsPlus.getInstance().getPetManager().getPet(player);
            PetsPlus.getInstance().getPetManager().despawnPet(player);

            player.sendMessage(PetsPlus.messageArgs("despawnedPet", pet.getType().getName()));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5, 10);
            player.closeInventory();
        }
    }

}
