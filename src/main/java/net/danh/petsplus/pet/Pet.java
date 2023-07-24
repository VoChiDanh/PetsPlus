package net.danh.petsplus.pet;

import net.danh.petsplus.PetsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Pet {

    private final PetType type;
    private Player owner;
    private Creature entity;

    private boolean isBaby;

    @SuppressWarnings("deprecation")
    protected Pet(Player owner, PetType type) {
        this.owner = owner;
        this.type = type;

        this.entity = (Creature) owner.getWorld().spawnEntity(owner.getLocation(), type.getEntityType());
        entity.setSilent(PetsPlus.getInstance().getConfigManager().getSetting("isSilent"));

        if (entity instanceof Ageable ageable && PetsPlus.getInstance().getConfigManager().getSetting("isBabyDefault")) {
            ageable.setBaby();
            ageable.setAgeLock(true);
        }

        if (PetsPlus.getInstance().getConfigManager().getSetting("hasNametag")) {
            entity.setCustomName(ChatColor.GOLD + owner.getName() + "'s " + type.getName());
            entity.setCustomNameVisible(true);
        }
    }

    public boolean isBaby() {
        return isBaby;
    }

    @SuppressWarnings("deprecation")
    public void setBaby(boolean isBaby) {
        this.isBaby = isBaby;

        if (entity instanceof Ageable ageable) {
            ageable.setAgeLock(true);

            if (isBaby) ageable.setBaby();
            else ageable.setAdult();
        }
    }

    public boolean hasBabyOption() {
        return entity instanceof Ageable;
    }

    @SuppressWarnings("deprecation")
    public void tick() {
        if (entity == null || owner == null || entity.isDead()) {
            return;
        }

        AttributeInstance maxh = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxh == null) return;
        double maxHealth = maxh.getValue();
        if (entity.getHealth() < maxHealth) {
            entity.setHealth(maxHealth);
        }

        if (entity.getTarget() != null) {
            entity.setTarget(null);
        }

        if (entity.getPassengers().contains(owner))
            return;

        double distance = entity.getWorld() != owner.getWorld() ? Double.MAX_VALUE : entity.getLocation().distanceSquared(owner.getLocation());
        if (distance > 510.0 && owner.isOnGround()) {
            entity.teleport(owner.getLocation().add(1, 0, 0));
        } else if (distance > 10.0) {
            walkTo(owner.getLocation().add(1, 0, 0));
        }
    }

    private void walkTo(Location targetLocation) {
        org.bukkit.util.Vector direction = targetLocation.subtract(entity.getLocation()).toVector().normalize(); // get the normalized direction vector from the creature to the player
        org.bukkit.util.Vector velocity = direction.multiply((double) 2); // set the velocity to the direction vector multiplied by 1
        velocity.add(new Vector(0.0, 0.5, 0.0)); // add an upward vector of 0.5 to make the creature jump
        entity.setVelocity(velocity); // set the new velocity
    }

    public void despawn() {
        entity.remove();
        this.owner = null;
        this.entity = null;
    }

    protected Creature getEntity() {
        return entity;
    }

    public void addPassenger() {
        entity.addPassenger(owner);
    }

    public Location getLocation() {
        return entity.getLocation();
    }

    public Player getOwner() {
        return owner;
    }

    public PetType getType() {
        return type;
    }

}
