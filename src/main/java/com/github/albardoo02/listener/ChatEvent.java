package com.github.albardoo02.listener;

import com.github.albardoo02.ChatLimit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.List;

public class ChatEvent implements Listener {
    ChatLimit plugin;
    public ChatEvent(ChatLimit plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String reason = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Reason"));
        String replace = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReplaceMessage"));

        if (player.hasPermission("ChatLimit.chat.bypass")) {
            return;
        }
        List<String> msg = plugin.getMessageConfig().getStringList("KickWords");
        for (String message : msg) {
            if (event.getMessage().contains(message)) {
                player.kickPlayer(reason);
                event.setMessage(replace);
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&8&l» &r" + plugin.getMessageConfig().getString("MessageToOP").replace("%player",player.getName())));
                Bukkit.getOperators().stream().filter(OfflinePlayer::isOnline).forEach(p -> p.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &r" + plugin.getMessageConfig().getString("MessageToOP").replace("%player",player.getName()))));
            }
        }
    }
}
