package com.github.albardoo02.listener;

import com.github.albardoo02.ChatLimit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandEvent implements Listener {

    ChatLimit plugin;
    public CommandEvent(ChatLimit plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        String reason = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Reason"));
        if(player.hasPermission("ChatLimit.command.bypass")){
            return;
        }
        List<String> cmd = plugin.getMessageConfig().getStringList("KickCommands");
        for(String command : cmd){
            if(event.getMessage().equalsIgnoreCase("/" + command)){
                event.setCancelled(true);
                player.kickPlayer(reason);
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + " &8&l» &r" + plugin.getConfig().getString("CommandToOP").replace("%player", player.getName()).replace("%command",event.getMessage())));
                Bukkit.getOperators().stream().filter(OfflinePlayer::isOnline).forEach(p -> p.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + " &8&l» &r" + plugin.getConfig().getString("CommandToOP").replace("%player", player.getName()).replace("%command",event.getMessage()))));
            }
        }
    }
}
