package com.github.albardoo02;

import com.github.albardoo02.listener.CommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

public final class ChatLimit extends JavaPlugin implements Listener {
    String ServerVersion = getServer().getVersion();
    public String prefix = ChatColor.translateAlternateColorCodes('&',getConfig().getString("prefix"));

    private File MessageConfigFile;
    private FileConfiguration MessageConfig;
    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info(ChatColor.translateAlternateColorCodes('&', ""));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', " &2__"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2|__) &bChatLimit &3v1.0.0"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&2|__) &8" + ServerVersion));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', ""));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&eLoading Config..."));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aSuccess!"));

        this.saveDefaultConfig();
        createMessageConfig();

        getServer().getPluginManager().registerEvents(new CommandEvent(this), this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    public FileConfiguration getMessageConfig() {
        return this.MessageConfig;
    }

    private void createMessageConfig() {
        MessageConfigFile = new File(getDataFolder(), "message.yml");
        if (!MessageConfigFile.exists()) {
            MessageConfigFile.getParentFile().mkdirs();
            saveResource("message.yml", false);
        }

        MessageConfig = new YamlConfiguration();
        try {
            MessageConfig.load(MessageConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        saveConfig();
        getLogger().info("Plugin has been disabled");
        getLogger().info("Goodbye!");
    }


    @EventHandler
    public void onPlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String reason = ChatColor.translateAlternateColorCodes('&', getMessageConfig().getString("Reason"));
        String replace = ChatColor.translateAlternateColorCodes('&', getMessageConfig().getString("ReplaceMessage"));

        if (player.hasPermission("chat.bypass")) {
            return;
        }
        List<String> msg = getMessageConfig().getStringList("KickWords");
        for (String message : msg) {
            if (event.getMessage().contains(message)) {
                player.kickPlayer(reason);
                event.setMessage(replace);
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "&8&l» &r" + getMessageConfig().getString("MessageToOP").replace("%player",player.getName())));
                Bukkit.getOperators().stream().filter(OfflinePlayer::isOnline).forEach(p -> p.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "&8&l» &r" + getMessageConfig().getString("MessageToOP").replace("%player",player.getName()))));
            }
        }
    }
}
