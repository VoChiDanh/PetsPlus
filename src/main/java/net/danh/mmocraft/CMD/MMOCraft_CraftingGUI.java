package net.danh.mmocraft.CMD;

import net.danh.mmocraft.GUI.CraftingGUI;
import net.danh.mmocraft.GUI.PreCraftingGUI;
import net.danh.mmocraft.GUI.SellingGUI;
import net.danh.mmocraft.Resources.Chat;
import net.danh.mmocraft.Resources.File;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MMOCraft_CraftingGUI extends CMDBase {
    public MMOCraft_CraftingGUI() {
        super("MMOCraft_CraftingGUI");
    }

    @Override
    public void execute(CommandSender c, String[] args) {
        if (c.hasPermission("mmocraft.admin")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    File.reloadFiles(c);
                }
            }
        }
        if (args.length >= 1 && args.length <= 2) {
            if (args[0].equalsIgnoreCase("sell")) {
                if (c instanceof Player p) {
                    SellingGUI.getSellingGUI().open(p);
                    Chat.sendPlayerMessage(p, "&bHãy đặt vật phẩm cần bán vào GUI và thoát gui ra để bán!");
                    Chat.sendPlayerMessage(p, "&bVui lòng không bán bừa bãi dẫn đến mất vật phẩm admin không chịu trách nhiệm");
                }
            }
            if (args[0].equalsIgnoreCase("craft")) {
                if (c instanceof Player p) {
                    CraftingGUI.getCraftingGUI().open(p);
                }
                if (c instanceof ConsoleCommandSender) {
                    if (args[1] != null) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p != null) {
                            CraftingGUI.getCraftingGUI().open(p);
                        }
                    }
                }
            }
            if (args[0].equalsIgnoreCase("preview")) {
                if (c instanceof Player) {
                    PreCraftingGUI.getPreCraftingGUI().open((Player) c);
                }
                if (c instanceof ConsoleCommandSender) {
                    if (args[1] != null) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p != null) {
                            PreCraftingGUI.getPreCraftingGUI().open(p);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> TabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("mmocraft.admin")) {
                commands.add("reload");
            }
            commands.add("craft");
            commands.add("preview");
            commands.add("sell");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
