package com.github.albardoo02.command;

import com.github.albardoo02.ChatLimit;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatLimitCommand implements CommandExecutor {

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
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &rConfigを再読み込みしました"));
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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &cあなたには権限がありません!"));
            }
        }
        return true;
    }
}
