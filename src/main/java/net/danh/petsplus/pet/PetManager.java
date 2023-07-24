package net.danh.petsplus.pet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PetManager {

    private final Map<UUID, Pet> pets = new HashMap<>();
    private final Map<Entity, Pet> petEntities = new HashMap<>();

    public void spawnPet(Player player, PetType type) {
        Pet pet = new Pet(player, type);
        pets.put(player.getUniqueId(), pet);
        petEntities.put(pet.getEntity(), pet);
    }

    public void despawnPet(Player player) {
        Pet pet = this.getPet(player);
        if (pet == null)
            return;

        pet.despawn();
        pets.remove(player.getUniqueId());
        petEntities.remove(pet.getEntity());
    }

    public Pet getPet(Player player) {
        return pets.get(player.getUniqueId());
    }

    public Pet getPet(Entity entity) {
        return petEntities.get(entity);
    }

    public void despawnAll() {
        for (Pet pet : pets.values()) {
            pet.despawn();
        }
        pets.clear();
    }

    public void tick() {
        for (Pet pet : pets.values()) {
            pet.tick();
        }
    }

}
