package com.github.albardoo02.listener;

import com.github.albardoo02.ChatLimit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    ChatLimit plugin;
    public CommandEvent(ChatLimit plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
        String message = event.getMessage();
        String[] array = message.split(" ");
        Player player = event.getPlayer();
        if(player.hasPermission("chat.command.bypass")){
            return;
        }
        if(array[0].equalsIgnoreCase("/gamemode")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eこのコマンドは禁止されています"));
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + " &8&l» &r" + plugin.getMessageConfig().getString("CommandToOP").replace("%player", player.getName())));
            Bukkit.getOperators().stream().filter(OfflinePlayer::isOnline).forEach(p -> p.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.prefix + " &8&l» &r"+ plugin.getMessageConfig().getString("CommandToOP").replace("%player", player.getName()))));
        }
    }
}
