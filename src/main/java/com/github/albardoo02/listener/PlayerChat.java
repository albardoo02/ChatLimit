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
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
public class PlayerChat implements Listener {

    ChatLimit plugin;
    public PlayerChat(ChatLimit plugin){
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerChatEvent(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        String message = event.getMessage();
        String reason = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("Chat.Kick.Reason").replace("%player",player.getName()).replace("%word",message));
        String notice = ChatColor.translateAlternateColorCodes('&',plugin.getPrefix() + " &8&l» "+ plugin.getConfig().getString("Chat.SendToOP.Notice").replace("%player",player.getName()).replace("%word",message));
        String console = ChatColor.translateAlternateColorCodes('&',plugin.getPrefix() + " &8&l» "+ plugin.getConfig().getString("Chat.SendToConsole.Notice").replace("%player",player.getName()).replace("%word",message));
        if (!plugin.getConfig().getBoolean("Chat.Enabled")){
            return;
        }
        if (player.hasPermission("ChatLimit.chat.bypass")) {
            return;
        }
        List<String> msg = plugin.data.getConfig().getStringList("Word");
        for (String word : msg){
            if (message.contains(word)) {
                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    public void run() {
                        if (!plugin.getConfig().getBoolean("Chat.Kick.Enabled")) {
                            return;
                        }
                        player.kickPlayer(reason);
                    }
                });
                if (!plugin.getConfig().getBoolean("Chat.SendToConsole.Enabled")) {
                    return;
                }
                Bukkit.getServer().getConsoleSender().sendMessage(console);
                if (!plugin.getConfig().getBoolean("Chat.Cancel.Enabled")) {
                    return;
                }
                event.setCancelled(true);
                if (!plugin.getConfig().getBoolean("Chat.SendToOP.Enabled")) {
                    return;
                }
                for (Player ops : Bukkit.getServer().getOnlinePlayers()) {
                    if (ops.isOp()) {
                        TextComponent Message = new TextComponent(notice);
                        Message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c検出されたワード: §e" + message + "\n§fConfigで設定を変更できます").create()));
                        ops.spigot().sendMessage(Message);
                    }
                }
            }

        }

    }
}
