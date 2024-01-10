package net.danh.petsplus.pet;

import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierSource;
import io.lumine.mythic.lib.player.modifier.ModifierType;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.danh.petsplus.ConfigManager;
import net.danh.petsplus.PetsPlus;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PetManager {

    private final Map<UUID, Pet> pets = new HashMap<>();
    private final Map<UUID, Pet> petEntities = new HashMap<>();
    private final HashMap<String, StatModifier> statModifiers = new HashMap<>();

    public void spawnPet(Player player, String type) {
        Pet pet = new Pet(player, new ConfigManager().getPetType(type + ".name"), new ConfigManager().getPetType(type + ".skullTexture"));
        pets.put(player.getUniqueId(), pet);
        petEntities.put(pet.getEntity().getUniqueId(), pet);
        if (new ConfigManager().getConfig().contains("pets." + type + ".stats")) {
            for (String stats : Objects.requireNonNull(new ConfigManager().getConfig().getConfigurationSection("pets." + type + ".stats")).getKeys(false)) {
                StatModifier statModifier = new StatModifier(PetsPlus.getInstance().getName(), stats.toUpperCase(), new ConfigManager().getConfig().getInt("pets." + type + ".stats." + stats), ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER);
                statModifier.register(PlayerData.get(player.getUniqueId()).getMMOPlayerData());
                statModifiers.put(player.getName() + ";" + stats, statModifier);
            }
        }
    }

    public boolean isPetActive(Player p) {
        return pets.containsKey(p.getUniqueId()) && petEntities.containsValue(pets.get(p.getUniqueId()));
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
        for (String s : statModifiers.keySet()) {
            String s1 = s.split(";")[0];
            if (s1.equalsIgnoreCase(player.getName())) {
                StatModifier statModifier = statModifiers.get(s);
                statModifier.unregister(PlayerData.get(player.getUniqueId()).getMMOPlayerData());
            }
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
