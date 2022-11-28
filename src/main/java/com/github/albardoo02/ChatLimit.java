package com.github.albardoo02;

import com.github.albardoo02.command.ChatLimitCommand;
import com.github.albardoo02.listener.PlayerChat;
import com.github.albardoo02.listener.PlayerCommandPreprocess;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ChatLimit extends JavaPlugin {

    public String prefix = ChatColor.translateAlternateColorCodes('&',getConfig().getString("prefix"));

    private File MessageConfigFile;
    private FileConfiguration MessageConfig;
    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"&bChatLimit &3v" + getConfig().getString("version")));
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"NGワード検出時にOPに通知: &a" + getConfig().getString("MessageToOP.Enabled")));
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"NGコマンド検出時にOPに通知: &a" + getConfig().getString("CommandToOP.Enabled")));
        this.saveDefaultConfig();
        createMessageConfig();

        getServer().getPluginManager().registerEvents(new PlayerChat(this),this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(this), this);

        getCommand("chatlimit").setExecutor(new ChatLimitCommand(this));
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
}
