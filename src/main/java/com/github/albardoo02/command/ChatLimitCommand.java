package com.github.albardoo02.command;

import com.github.albardoo02.ChatLimit;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChatLimitCommand implements CommandExecutor, TabCompleter {

    ChatLimit plugin;
    public ChatLimitCommand(ChatLimit plugin){
        this.plugin = plugin;

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("chatlimit")) {
            if (args.length <= 0){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6コマンドヘルプ: &f/chatlimit"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6詳細: &fChatLimitのコマンド"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6使用方法: &f/chatlimit <command>"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6エイリアス: &f/chat , / limit , /cl"));
                return true;
            }
            if (sender.hasPermission("chatlimit.command.reload")) {
                if(args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    plugin.saveConfig();
                    plugin.data.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getPrefix() + " &8&l» &rConfigを再読み込みしました"));
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&',plugin.getPrefix() + " &8&l» &rConfigを再読み込みしました"));
                }
            }
            if (sender.hasPermission("chatlimit.command.version")) {
                if(args[0].equalsIgnoreCase("version")) {
                    TextComponent Message = new TextComponent("ソースコードはここをクリック!");
                    Message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://github.com/albardoo02/ChatLimit"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"========== &cChatLimit &ev" + plugin.getDescription().getVersion() + " &r=========="));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/chat help &dでヘルプを表示します"));
                    sender.spigot().sendMessage(Message);
                }
            }
            if (sender.hasPermission("chatlimit.command.help")) {
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6コマンドヘルプ: &f/chatlimit"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6詳細: &fChatLimitのコマンド"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6使用方法: &f/chatlimit <command>"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6エイリアス: &f/chat , / limit , /cl"));
                }
            }
            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getPrefix() + " &8&l» &cあなたには権限がありません!"));
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
                        return Arrays.asList("help", "reload", "version");
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
