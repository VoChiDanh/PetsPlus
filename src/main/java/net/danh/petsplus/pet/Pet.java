package net.danh.petsplus.pet;

import net.danh.petsplus.ConfigManager;
import net.danh.petsplus.util.ItemBuilder;
import net.danh.petsplus.util.SkullCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Pet {

    private final String id;
    private final String name;
    private ArmorStand entity;
    private Player owner;

    protected Pet(Player owner, String name, String id) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        ArmorStand petBase = (ArmorStand) owner.getWorld().spawnEntity(owner.getLocation(), EntityType.ARMOR_STAND);
        petBase.setBasePlate(false);
        petBase.setArms(false);
        petBase.setGravity(false);
        petBase.setVisible(false);
        petBase.setCustomName(name);
        petBase.setCustomNameVisible(true);
        petBase.setHelmet(new ItemBuilder(SkullCreator.itemFromBase64(id)).setName(new ConfigManager().getString(name)).get());
        this.entity = petBase;
    }

    public void despawn() {
        if (entity != null) {
            entity.remove();
        }
        this.owner = null;
        this.entity = null;
    }

    public ArmorStand getEntity() {
        return entity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }
}
