package net.danh.mmocraft.GUI;

import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class PreCraftingGUI {


    public static @NotNull RyseInventory getPreCraftingGUI() {
        FileConfiguration file = File.getSetting();
        if (file == null) {
            MMOCraft.getMMOCraft().getLogger().log(Level.INFO, "File pre-gui-crafting.yml is null");
            return RyseInventory.builder().title("File pre-gui-crafting.yml is null - contact to admin").rows(1).provider(new InventoryProvider() {

            }).build(MMOCraft.getMMOCraft(), MMOCraft.getInventoryManager());
        }
        String title = Chat.colorize(Objects.requireNonNull(file.getString("title")));
        return RyseInventory.builder().title(title).rows(file.getInt("size")).provider(new InventoryProvider() {

            @Override
            public void init(Player p, InventoryContents contents) {
                Objects.requireNonNull(file.getConfigurationSection("crafting")).getKeys(false).forEach(item -> {
                    List<String> condition = file.getStringList("crafting." + item + ".condition");
                    String result = file.getString("crafting." + item + ".result");
                    int slot = file.getInt("crafting." + item + ".slot");
                    condition.forEach(c -> {
                        String[] cc = c.split(";");
                        if (cc[0].equalsIgnoreCase("MMOCORE")) {
                            if (cc[1].equalsIgnoreCase("CLASS")) {
                                if (MMOCore.plugin.classManager.has(cc[2])) {
                                    if (PlayerData.get(p).getProfess().getId().equalsIgnoreCase(cc[2])) {
                                        String[] result_string = new String[0];
                                        if (result != null) {
                                            result_string = result.split(";");
                                        }
                                        if (result_string[0].equalsIgnoreCase("MMOITEMS")) {
                                            ItemStack result_item = MMOItems.plugin.getItem(result_string[1], result_string[2]);
                                            if (result_item != null) {
                                                if (PlayerData.get(p).getLevel() >= Number.getInteger(cc[3])) {
                                                    contents.set(slot, result_item);
                                                } else {
                                                    ItemStack fill = new ItemBuilder(new ItemStack(Material.RED_STAINED_GLASS_PANE))
                                                            .displayName(Chat.colorize("&cChưa Mở Khóa"))
                                                            .lore(Chat.colorize("&6Bạn cần đạt đến cấp độ &a" + Number.getInteger(cc[3]), "&6Để có thể chế tạo được vật phẩm này"))
                                                            .build();
                                                    contents.set(slot, fill);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                });
                ItemStack fill = new ItemBuilder(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
                        .displayName(Chat.colorize("&7"))
                        .build();
                for (int i = 0; i < contents.slots().size(); i++) {
                    if (contents.get(i).isEmpty()) contents.set(i, fill);
                }
            }
        }).listener(new EventCreator<>(InventoryClickEvent.class, e -> {
            if (e.getInventory().getItem(e.getSlot()) != null) {
                NBTItem nbtItem = NBTItem.get(e.getInventory().getItem(e.getSlot()));
                if (nbtItem.hasType()) {
                    String type = nbtItem.getType();
                    String id = nbtItem.getString("MMOITEMS_ITEM_ID");
                    for (String item : Objects.requireNonNull(file.getConfigurationSection("crafting")).getKeys(false)) {
                        List<String> condition = file.getStringList("crafting." + item + ".condition");
                        String ingredient = file.getString("crafting." + item + ".ingredient");
                        String result = file.getString("crafting." + item + ".result");
                        condition.forEach(c -> {
                            String[] cc = c.split(";");
                            if (cc[0].equalsIgnoreCase("MMOCORE")) {
                                if (cc[1].equalsIgnoreCase("CLASS")) {
                                    String[] result_string = new String[0];
                                    if (result != null) {
                                        result_string = result.split(";");
                                    }
                                    if (result_string[0].equalsIgnoreCase("MMOITEMS")) {
                                        if (result_string[1].equalsIgnoreCase(type) && result_string[2].equalsIgnoreCase(id)) {
                                            getIngredientCraftingGUI(item, ingredient, result).open((Player) e.getWhoClicked());
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        })).build(MMOCraft.getMMOCraft(), MMOCraft.

                getInventoryManager());
    }

    public static @NotNull RyseInventory getIngredientCraftingGUI(String craft, String ingredient, String result) {
        FileConfiguration file = File.getCraftingGUI();
        if (file == null) {
            MMOCraft.getMMOCraft().getLogger().log(Level.INFO, "File gui-crafting.yml is null");
            return RyseInventory.builder().title("File gui-crafting.yml is null - contact to admin").rows(1).provider(new InventoryProvider() {

            }).build(MMOCraft.getMMOCraft(), MMOCraft.getInventoryManager());
        }
        String title = Chat.colorize(Objects.requireNonNull(file.getString("title")));
        return RyseInventory.builder().title(title).rows(file.getInt("size")).provider(new InventoryProvider() {
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
                            String[] ing = ingredient.split(";");
                            String line_1 = ing[0];
                            String line_2 = ing[1];
                            String line_3 = ing[2];
                            String[] l1 = line_1.split("-");
                            String[] l2 = line_2.split("-");
                            String[] l3 = line_3.split("-");
                            if (checkSlot(craft, l1, contents, 10, 11, 12)) {
                                if (checkSlot(craft, l2, contents, 19, 20, 21)) {
                                    if (checkSlot(craft, l3, contents, 28, 29, 30)) {
                                        String[] result_string = result.split(";");
                                        if (result_string[0].equalsIgnoreCase("MMOITEMS")) {
                                            ItemStack result_item = MMOItems.plugin.getItem(result_string[1], result_string[2]);
                                            if (result_item != null) {
                                                contents.set(24, result_item);
                                            }
                                        }
                                    }
                                }
                            }

                            for (int slot : slots) {
                                contents.set(slot, item_builder != null ? item_builder : new ItemStack(Material.STONE));
                            }
                        }
                    }
                }).

                build(MMOCraft.getMMOCraft(), MMOCraft.

                        getInventoryManager());
    }

    private static boolean checkSlot(String craft, String[] item, InventoryContents inventory, int item1, int item2, int item3) {
        if (File.getSetting().getString("crafting." + craft + ".item." + item[0]) != null) {
            if (File.getSetting().getString("crafting." + craft + ".item." + item[1]) != null) {
                if (File.getSetting().getString("crafting." + craft + ".item." + item[2]) != null) {
                    String item_l2_1 = File.getSetting().getString("crafting." + craft + ".item." + item[0]);
                    String item_l2_2 = File.getSetting().getString("crafting." + craft + ".item." + item[1]);
                    String item_l2_3 = File.getSetting().getString("crafting." + craft + ".item." + item[2]);
                    if (item_l2_1 != null && item_l2_2 != null && item_l2_3 != null) {
                        ItemStack itemStack_1 = setItem(item_l2_1);
                        ItemStack itemStack_2 = setItem(item_l2_2);
                        ItemStack itemStack_3 = setItem(item_l2_3);
                        if (itemStack_1 != null) {
                            inventory.set(item1, itemStack_1);
                        }
                        if (itemStack_2 != null) {
                            inventory.set(item2, itemStack_2);
                        }
                        if (itemStack_3 != null) {
                            inventory.set(item3, itemStack_3);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static ItemStack setItem(String item) {
        if (item.contains(";")) {
            String[] mmoitems = item.split(";");
            String type = mmoitems[1];
            String id = mmoitems[2];
            return MMOItems.plugin.getItem(type, id);
        } else return null;
    }
}
