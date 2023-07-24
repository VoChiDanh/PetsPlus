package net.danh.petsplus.pet;

import net.danh.petsplus.ConfigManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PetManager {

    private final Map<UUID, Pet> pets = new HashMap<>();
    private final Map<UUID, Pet> petEntities = new HashMap<>();

    public void spawnPet(Player player, String type) {
        Pet pet = new Pet(player, new ConfigManager().getPetType(type + ".name"), new ConfigManager().getPetType(type + ".skullTexture"));
        pets.put(player.getUniqueId(), pet);
        petEntities.put(pet.getEntity().getUniqueId(), pet);
    }

    public void despawnPet(Player player) {
        Pet pet = this.getPet(player);
        if (pet == null) return;
        if (pets.get(player.getUniqueId()) != null) {
            pets.remove(player.getUniqueId());
        }
        if (petEntities.get(pet.getEntity().getUniqueId()) != null) {
            petEntities.remove(pet.getEntity().getUniqueId());
        }
        pet.despawn();
    }

    public Pet getPet(Player player) {
        return pets.get(player.getUniqueId());
    }

    public Pet getPet(Entity entity) {
        return petEntities.get(entity.getUniqueId());
    }

    public void despawnAll() {
        for (Pet pet : pets.values()) {
            pet.despawn();
        }
        pets.clear();
    }

    public void tick() {
        for (Pet pet : pets.values()) {
            Location playerLocation = pet.getOwner().getLocation();
            pet.getEntity().teleport(playerLocation.add(1, 1, 0));
        }
    }

}
