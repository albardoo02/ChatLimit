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

public class PlayerCommandPreprocess implements Listener {

    ChatLimit plugin;
    public PlayerCommandPreprocess(ChatLimit plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        if (!plugin.getConfig().getBoolean("CommandToOP.Enabled")){
            return;
        }
        if(player.hasPermission("ChatLimit.command.bypass")){
            return;
        }
        List<String> cmd = plugin.getMessageConfig().getStringList("KickCommands");
        for(String command : cmd){
            if(event.getMessage().equalsIgnoreCase("/" + command)){
                event.setCancelled(true);
                for (Player ops : Bukkit.getServer().getOnlinePlayers()) {
                    if(ops.isOp()){
                        ops.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &r" + plugin.getConfig().getString("CommandToOP.message").replace("%player",event.getPlayer().getName()).replace("%command",event.getMessage())));
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + " &8&l» &r" + plugin.getConfig().getString("CommandToOP.message").replace("%player",event.getPlayer().getName()).replace("%command",event.getMessage())));
                    }
                }
            }
        }
    }

}
