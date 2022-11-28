package com.github.albardoo02.command;

import com.github.albardoo02.ChatLimit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChatLimitCommand implements CommandExecutor, TabCompleter {

    ChatLimit plugin;

    public ChatLimitCommand(ChatLimit plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("chatlimit")) {
            if (args.length == 0) {
                if (player.hasPermission("ChatLimit.command.help")) {
                    for (String line : plugin.getConfig().getStringList("Message.help")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                    }
                }
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    if (player.hasPermission("ChatLimit.command.help")) {
                        for (String line : plugin.getConfig().getStringList("Message.help")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &cあなたには権限がありません!"));
                    }
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("ChatLimit.command.reload")) {
                        plugin.reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + " &8&l» &fConfigを再読込しました"));
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &cあなたには権限がありません!"));
                    }
                }
                if (args[0].equalsIgnoreCase("version")) {
                    if (player.hasPermission("ChatLimit.command.version")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cChatLimit &ev" + plugin.getConfig().getString("version")));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6ソースコード: &fhttps://github.com/albardoo02/ChatLimit"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/chat help でヘルプを表示します"));
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &cあなたには権限がありません!"));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equalsIgnoreCase("chatlimit")) {
            if (args.length <= 1) {
                if (args[0].length() == 0) {
                    return Arrays.asList("help", "reload", "version");
                }
                else {
                    if ("help".startsWith(args[0]) && "reload".startsWith(args[0]) && "version".startsWith(args[0])) {
                        return Arrays.asList("list", "reload", "version","register");
                    } else if ("help".startsWith(args[0])) {
                        return Collections.singletonList("help");
                    } else if ("reload".startsWith(args[0])) {
                        return Collections.singletonList("reload");
                    } else if ("version".startsWith(args[0])) {
                        return Collections.singletonList("version");
                    }
                }
            }
        }
        return null;
    }

}
