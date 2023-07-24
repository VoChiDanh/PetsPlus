package net.danh.petsplus.listener;

import net.danh.petsplus.PetsPlus;
import net.danh.petsplus.gui.PetOptions;
import net.danh.petsplus.pet.Pet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        PetsPlus.getInstance().getPetManager().despawnPet(e.getPlayer());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        Pet pet = PetsPlus.getInstance().getPetManager().getPet(e.getEntity());
        if (pet != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        Pet pet = PetsPlus.getInstance().getPetManager().getPet(e.getRightClicked());
        if (pet != null) {
            e.setCancelled(true);

            if (pet.getOwner() == e.getPlayer()) {
                PetsPlus.getInstance().getGuiManager().getGui(PetOptions.class).open(e.getPlayer());
            }
        }
    }
}
