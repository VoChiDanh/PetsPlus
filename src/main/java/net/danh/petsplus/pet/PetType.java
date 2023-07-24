package net.danh.petsplus.pet;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.danh.petsplus.PetsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public enum PetType {

    BEE("BEE", Sound.ENTITY_BEE_LOOP),
    IRON_GOLEM("IRON_GOLEM", Sound.ENTITY_IRON_GOLEM_STEP),
    CHICKEN("CHICKEN", Sound.ENTITY_CHICKEN_AMBIENT),
    COW("COW", Sound.ENTITY_COW_AMBIENT),
    MUSHROOM("MUSHROOM_COW", Sound.ENTITY_COW_AMBIENT),
    SHEEP("SHEEP", Sound.ENTITY_SHEEP_AMBIENT),
    RABBIT("RABBIT", Sound.ENTITY_RABBIT_AMBIENT),
    OCELOT("OCELOT", Sound.ENTITY_OCELOT_AMBIENT),
    PIG("PIG", Sound.ENTITY_PIG_AMBIENT),
    FOX("FOX", Sound.ENTITY_FOX_AMBIENT),
    CAT("CAT", Sound.ENTITY_CAT_AMBIENT),
    WOLF("WOLF", Sound.ENTITY_WOLF_AMBIENT),
    POLAR_BEAR("POLAR_BEAR", Sound.ENTITY_POLAR_BEAR_WARNING);

    private final Sound sound;
    private EntityType type;
    private boolean isUnsupported = false;

    PetType(String typeName, Sound sound) {
        this.sound = sound;

        try {
            this.type = EntityType.valueOf(typeName);
        } catch (IllegalArgumentException e) {
            this.isUnsupported = true;
            PetsPlus.getInstance().getLogger().info("It looks like this version of Minecraft does not support " + typeName + ", disabling this pet type");
        }
    }

    public boolean isEnabled() {
        return !isUnsupported && this.getConfigSection().getBoolean("enabled");
    }

    public String getName() {
        return this.getConfigSection().getString("name");
    }

    public ItemStack createSkull(boolean hasPermission) {
        String skullTexture = this.getConfigSection().getString("skullTexture");
        if (skullTexture == null)
            return new ItemStack(Material.BARRIER);

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", skullTexture, null));

        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        meta.setDisplayName((hasPermission ? ChatColor.GREEN : ChatColor.RED) + this.getName());

        item.setItemMeta(meta);
        return item;
    }

    private ConfigurationSection getConfigSection() {
        return PetsPlus.getInstance().getConfig().getConfigurationSection("petTypes." + this.name().toLowerCase());
    }

    public String getPermission() {
        return "petsplus.pet." + this.name().toLowerCase();
    }

    public EntityType getEntityType() {
        return type;
    }

    public Sound getSound() {
        return sound;
    }

}
