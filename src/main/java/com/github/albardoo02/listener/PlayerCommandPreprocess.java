package com.github.albardoo02.listener;

import com.github.albardoo02.ChatLimit;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class PlayerCommandPreprocess implements Listener {

    ChatLimit plugin;
    public PlayerCommandPreprocess(ChatLimit plugin){
        this.plugin = plugin;

    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getConfig().getBoolean("Command.Enabled")) {
            return;
        }
        if (player.hasPermission("ChatLimit.command.bypass")) {
            return;
        }
        List<String> cmd = plugin.getWordConfig().getStringList("Command");
        for (String command : cmd) {
            String reason = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("Command.Kick.Reason").replace("%player",player.getName()).replace("%command","/" + command));
            String notice = ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» "+ plugin.getConfig().getString("Command.SendToOP.Notice").replace("%player",player.getName()).replace("%command","/" + command));
            String console = ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» "+ plugin.getConfig().getString("Command.SendToConsole.Notice").replace("%player",player.getName()).replace("%command","/" + command));
            if (event.getMessage().equalsIgnoreCase("/" + command)) {
                if (!plugin.getConfig().getBoolean("Command.SendToOP.Enabled")) {
                    return;
                }
                for (Player ops : Bukkit.getServer().getOnlinePlayers()) {
                    if (ops.isOp()) {
                        TextComponent Message = new TextComponent(notice);
                        Message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c検出されたコマンド: §e/" + command + "\n§fConfigで設定を変更できます").create()));
                        ops.spigot().sendMessage(Message);
                    }
                }
                if (!plugin.getConfig().getBoolean("Command.SendToConsole.Enabled")) {
                    return;
                }
                Bukkit.getServer().getConsoleSender().sendMessage(console);
                if (!plugin.getConfig().getBoolean("Command.Cancel.Enabled")) {
                    return;
                }
                event.setCancelled(true);
                if (!plugin.getConfig().getBoolean("Command.Kick.Enabled")) {
                    return;
                }
                player.kickPlayer(reason);
            }
        }
    }
}
