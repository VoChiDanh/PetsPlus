package net.danh.mmocraft.GUI;

import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.enums.DisabledEvents;
import io.github.rysefoxx.inventory.plugin.enums.DisabledInventoryClick;
import io.github.rysefoxx.inventory.plugin.other.EventCreator;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.danh.mmocraft.MMOCraft;
import net.danh.mmocraft.Resources.Chat;
import net.danh.mmocraft.Resources.File;
import net.danh.mmocraft.Utils.Number;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class SellingGUI {

    public static RyseInventory getSellingGUI() {
        return RyseInventory.builder()
                .title("Bán Đồ")
                .rows(3)
                .ignoreEvents(DisabledEvents.INVENTORY_DRAG)
                .ignoreClickEvent(DisabledInventoryClick.BOTH)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                    }
                })
                .listener(new EventCreator<>(InventoryCloseEvent.class, e -> {
                    if (e.getPlayer() instanceof Player p) {
                        for (int i = 0; i < e.getInventory().getSize(); i++) {
                            ItemStack itemStack = e.getInventory().getItem(i);
                            if (itemStack != null) {
                                sellItem(p, itemStack);
                            }
                        }
                    }
                }))
                .build(MMOCraft.getMMOCraft());
    }

    public static int getPrice(ItemStack itemStack) {
        NBTItem nbtItem = NBTItem.get(itemStack);
        if (nbtItem.hasType()) {
            String type = nbtItem.getType();
            String id = nbtItem.getString("MMOITEMS_ITEM_ID");
            return File.getConfig().getInt("sell.price." + type + ";" + id);
        }
        return 0;
    }

    public static int getItemPrice(ItemStack itemStack) {
        NBTItem nbtItem = NBTItem.get(itemStack);
        if (nbtItem.hasType()) {
            String mmo_pre = ChatColor.stripColor(Chat.colorize(nbtItem.get("MMOITEMS_NAME_PRE").toString().replace("[", "").replace("]", "").replace("\"", "")));
            List<String> list_bonus = Arrays.stream(mmo_pre.split(",")).toList();
            AtomicInteger integer = new AtomicInteger(getPrice(itemStack));
            list_bonus.forEach(bonus -> {
                if (bonus.contains("PD")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.PD.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.PD.PLUS")));
                    }
                }
                if (bonus.contains("MH")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.MH.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.MH.PLUS")));
                    }
                }
                if (bonus.contains("CSP")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.CSP.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.CSP.PLUS")));
                    }
                }
                if (bonus.contains("MM")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.MM.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.M.PLUS")));
                    }
                }
                if (bonus.contains("CSC")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.CSC.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.CSC.PLUS")));
                    }
                }
                if (bonus.contains("A")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.A.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.A.PLUS")));
                    }
                }
                if (bonus.contains("AD")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.AD.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.AD.PLUS")));
                    }
                }
                if (bonus.contains("HR")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.HR.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.HR.PLUS")));
                    }
                }
                if (bonus.contains("MD")) {
                    integer.addAndGet(Number.getInteger(File.getConfig().getString("sell.base.MD.NORMAL")));
                    if (bonus.contains("+")) {
                        int count = 0;
                        for (int i = 0; i < bonus.length(); i++) {
                            if (bonus.charAt(i) == '+') {
                                count++;
                            }
                        }
                        integer.addAndGet(count * Number.getInteger(File.getConfig().getString("sell.base.MD.PLUS")));
                    }
                }
            });
            return integer.get();
        }
        return 0;
    }

    public static void sellItem(Player p, ItemStack itemStack) {
        File.getConfig().getStringList("sell.command").forEach(cmd -> {
            int integer = getItemPrice(itemStack);
            String c = cmd.replace("<player>", p.getName()).replace("<price>", String.valueOf(integer));
            new BukkitRunnable() {
                @Override
                public void run() {
                    MMOCraft.getMMOCraft().getServer().dispatchCommand(MMOCraft.getMMOCraft().getServer().getConsoleSender(), c);
                    Chat.sendPlayerMessage(p, Objects.requireNonNull(File.getMessage().getString("ban_do")).replace("<name>", Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName()).replace("<price>", String.valueOf(integer)));
                    p.getInventory().setItemInMainHand(null);
                }
            }.runTask(MMOCraft.getMMOCraft());
        });
    }
}
