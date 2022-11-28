package com.github.albardoo02.listener;

import com.github.albardoo02.ChatLimit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.List;

public class PlayerChat implements Listener {

    ChatLimit plugin;
    public PlayerChat(ChatLimit plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String reason = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Reason").replace("%player",event.getPlayer().getName()).replace("%message",event.getMessage()));
        String replace = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReplaceMessage"));
        if (player.hasPermission("ChatLimit.chat.bypass")) {
            return;
        }
        List<String> msg = plugin.getMessageConfig().getStringList("KickWords");
        for (String message : msg) {
            if (event.getMessage().contains(message)) {
                for (Player ops : Bukkit.getServer().getOnlinePlayers()) {
                    if(ops.isOp()){
                        ops.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &r" + plugin.getConfig().getString("MessageToOP.message").replace("%player",event.getPlayer().getName()).replace("%message",event.getMessage())));
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &r" + plugin.getConfig().getString("MessageToOP.message").replace("%player",event.getPlayer().getName()).replace("%message",event.getMessage())));
                    }
                }
                event.setMessage(replace);
            }
        }
    }
}
