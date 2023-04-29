package net.danh.mmocraft.GUI;

import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.enums.Action;
import io.github.rysefoxx.inventory.plugin.enums.DisabledInventoryClick;
import io.github.rysefoxx.inventory.plugin.other.EventCreator;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.danh.mmocraft.MMOCraft;
import net.danh.mmocraft.Resources.Chat;
import net.danh.mmocraft.Resources.File;
import net.danh.mmocraft.Utils.Number;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;

public class CraftingGUI {

    public static @NotNull RyseInventory getCraftingGUI() {
        FileConfiguration file = File.getCraftingGUI();
        if (file == null) {
            MMOCraft.getMMOCraft().getLogger().log(Level.INFO, "File gui-crafting.yml is null");
            return RyseInventory.builder().title("File gui-crafting.yml is null - contact to admin").rows(1).provider(new InventoryProvider() {

            }).build(MMOCraft.getMMOCraft(), MMOCraft.getInventoryManager());
        }
        String title = Chat.colorize(Objects.requireNonNull(file.getString("title")));
        return RyseInventory.builder().title(title).rows(file.getInt("size")).provider(new InventoryProvider() {
                    @Override
                    public void update(Player player, InventoryContents contents) {
                        for (String item : Objects.requireNonNull(file.getConfigurationSection("items")).getKeys(false)) {
                            List<Integer> slots = new ArrayList<>();
                            if (file.contains("items." + item + ".slots")) {
                                slots = file.getIntegerList("items." + item + ".slots");
                            } else if (file.contains("items." + item + ".slot")) {
                                slots = Collections.singletonList(file.getInt("items." + item + ".slot"));
                            }
                            String materialTypeS = file.getString("items." + item + ".material_type");
                            ItemStack item_builder = null;
                            String materialS = file.getString("items." + item + ".material");
                            if (materialTypeS != null && materialTypeS.equalsIgnoreCase("VANILLA")) {
                                if (materialS != null) {
                                    Material material = Material.getMaterial(materialS);
                                    int amount = file.getInt("items." + item + ".amount");
                                    String displayName = file.getString("items." + item + ".display");
                                    List<String> lore = file.getStringList("items." + item + ".lore");
                                    if (amount <= 0) amount = 1;
                                    if (displayName != null && !lore.isEmpty()) {
                                        item_builder = new ItemBuilder(material != null ? material : Material.STONE).amount(amount).displayName(Chat.colorize(displayName)).lore(Chat.colorize(lore)).flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE).build();
                                    } else if (displayName != null) {
                                        item_builder = new ItemBuilder(material != null ? material : Material.STONE).amount(amount).displayName(Chat.colorize(displayName)).flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE).build();
                                    } else {
                                        item_builder = new ItemBuilder(material != null ? material : Material.STONE).amount(amount).flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE).build();

                                    }
                                }
                            }
                            for (int slot : slots) {
                                contents.update(slot, item_builder != null ? item_builder : new ItemStack(Material.STONE));
                            }
                        }
                    }

                    @Override
                    public void init(Player player, InventoryContents contents) {
                        for (String item : Objects.requireNonNull(file.getConfigurationSection("items")).getKeys(false)) {
                            List<Integer> slots = new ArrayList<>();
                            if (file.contains("items." + item + ".slots")) {
                                slots = file.getIntegerList("items." + item + ".slots");
                            } else if (file.contains("items." + item + ".slot")) {
                                slots = Collections.singletonList(file.getInt("items." + item + ".slot"));
                            }
                            String materialTypeS = file.getString("items." + item + ".material_type");
                            ItemStack item_builder = null;
                            String materialS = file.getString("items." + item + ".material");
                            if (materialTypeS != null && materialTypeS.equalsIgnoreCase("VANILLA")) {
                                if (materialS != null) {
                                    Material material = Material.getMaterial(materialS);
                                    int amount = file.getInt("items." + item + ".amount");
                                    String displayName = file.getString("items." + item + ".display");
                                    List<String> lore = file.getStringList("items." + item + ".lore");
                                    if (amount <= 0) amount = 1;
                                    if (displayName != null && !lore.isEmpty()) {
                                        item_builder = new ItemBuilder(material != null ? material : Material.STONE).amount(amount).displayName(Chat.colorize(displayName)).lore(Chat.colorize(lore)).flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE).build();
                                    } else if (displayName != null) {
                                        item_builder = new ItemBuilder(material != null ? material : Material.STONE).amount(amount).displayName(Chat.colorize(displayName)).flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE).build();
                                    } else {
                                        item_builder = new ItemBuilder(material != null ? material : Material.STONE).amount(amount).flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE).build();

                                    }
                                }
                            }
                            for (int slot : slots) {
                                contents.set(slot, item_builder != null ? item_builder : new ItemStack(Material.STONE));
                            }
                        }
                    }
                }).ignoredSlots(10, 11, 12, 19, 20, 21, 28, 29, 30, 24)
                .enableAction(Action.MOVE_TO_OTHER_INVENTORY, Action.DOUBLE_CLICK)
                .ignoreClickEvent(DisabledInventoryClick.BOTTOM)
                .listener(new EventCreator<>(InventoryCloseEvent.class, e -> {
                    for (int slot : Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30, 24)) {
                        if (e.getInventory().getItem(slot) != null) {
                            e.getPlayer().getInventory().addItem(e.getInventory().getItem(slot));
                        }
                    }
                }))
                .listener(new EventCreator<>(InventoryClickEvent.class, e -> {
                    for (String item : Objects.requireNonNull(file.getConfigurationSection("items")).getKeys(false)) {
                        List<Integer> slots = new ArrayList<>();
                        if (file.contains("items." + item + ".slots")) {
                            slots = file.getIntegerList("items." + item + ".slots");
                        } else if (file.contains("items." + item + ".slot")) {
                            slots = Collections.singletonList(file.getInt("items." + item + ".slot"));
                        }
                        for (int slot : slots) {
                            if (e.getWhoClicked() instanceof Player p) {
                                if (e.getSlot() == slot) {
                                    if (slot == 25) {
                                        for (String craft : Objects.requireNonNull(File.getSetting().getConfigurationSection("crafting")).getKeys(false)) {
                                            List<String> condition = File.getSetting().getStringList("crafting." + craft + ".condition");
                                            String ingredient = File.getSetting().getString("crafting." + craft + ".ingredient");
                                            String result = File.getSetting().getString("crafting." + craft + ".result");
                                            if (ingredient != null && result != null) {
                                                condition.forEach(c -> {
                                                    String[] cc = c.split(";");
                                                    if (cc[0].equalsIgnoreCase("MMOCORE")) {
                                                        if (cc[1].equalsIgnoreCase("CLASS")) {
                                                            if (MMOCore.plugin.classManager.has(cc[2])) {
                                                                if (PlayerData.get(p).getProfess().getId().equalsIgnoreCase(cc[2])) {
                                                                    if (PlayerData.get(p).getLevel() >= Number.getInteger(cc[3])) {
                                                                        String[] ing = ingredient.split(";");
                                                                        String line_1 = ing[0];
                                                                        String line_2 = ing[1];
                                                                        String line_3 = ing[2];
                                                                        String[] l1 = line_1.split("-");
                                                                        String[] l2 = line_2.split("-");
                                                                        String[] l3 = line_3.split("-");
                                                                        if (checkSlot(craft, l1, e.getInventory(), 10, 11, 12)) {
                                                                            if (checkSlot(craft, l2, e.getInventory(), 19, 20, 21)) {
                                                                                if (checkSlot(craft, l3, e.getInventory(), 28, 29, 30)) {
                                                                                    String[] result_string = result.split(";");
                                                                                    if (result_string[0].equalsIgnoreCase("MMOITEMS")) {
                                                                                        ItemStack result_item = MMOItems.plugin.getItem(result_string[1], result_string[2]);
                                                                                        if (result_item != null) {
                                                                                            if (e.getInventory().getItem(24) == null) {
                                                                                                for (int clear_slot : Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30)) {
                                                                                                    ItemStack i = e.getInventory().getItem(clear_slot);
                                                                                                    if (i != null) {
                                                                                                        if (i.getAmount() == 1) {
                                                                                                            e.getInventory().setItem(clear_slot, null);
                                                                                                        }
                                                                                                        if (i.getAmount() > 1) {
                                                                                                            i.setAmount(i.getAmount() - 1);
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                e.getInventory().setItem(24, result_item);
                                                                                            } else
                                                                                                Chat.sendPlayerMessage(p, File.getMessage().getString("da_co_vat_pham"));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                })).

                build(MMOCraft.getMMOCraft(), MMOCraft.

                        getInventoryManager());
    }

    private static boolean checkSlot(String craft, String[] item, Inventory inventory, int item1, int item2, int item3) {
        if (File.getSetting().getString("crafting." + craft + ".item." + item[0]) != null) {
            if (File.getSetting().getString("crafting." + craft + ".item." + item[1]) != null) {
                if (File.getSetting().getString("crafting." + craft + ".item." + item[2]) != null) {
                    String item_l2_1 = File.getSetting().getString("crafting." + craft + ".item." + item[0]);
                    String item_l2_2 = File.getSetting().getString("crafting." + craft + ".item." + item[1]);
                    String item_l2_3 = File.getSetting().getString("crafting." + craft + ".item." + item[2]);
                    return (item_l2_1 != null && checkItem(item_l2_1, inventory.getItem(item1))) && (item_l2_2 != null && checkItem(item_l2_2, inventory.getItem(item2))) && (item_l2_3 != null && checkItem(item_l2_3, inventory.getItem(item3)));
                }
            }
        }
        return false;
    }


    private static boolean checkItem(String item, ItemStack itemStack) {
        if (item.contains(";")) {
            String[] mmoitems = item.split(";");
            String mmo = mmoitems[0];
            String type = mmoitems[1];
            String id = mmoitems[2];
            NBTItem nbtItem = NBTItem.get(itemStack);
            return mmo.equalsIgnoreCase("MMOITEMS") && nbtItem.hasType() && nbtItem.getType().equalsIgnoreCase(type) && nbtItem.getString("MMOITEMS_ITEM_ID").equalsIgnoreCase(id);
        } else return (item.equalsIgnoreCase("AIR") && itemStack == null);

    }
}
