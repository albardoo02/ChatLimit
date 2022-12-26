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
    public String version = getDescription().getVersion();
    public String prefix = ChatColor.translateAlternateColorCodes('&',getConfig().getString("message.prefix"));


    private File WordConfigFile;
    private FileConfiguration WordConfig;

    @Override
    public void onEnable() {
        getLogger().info("");
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"&cChatLimit &bv" + version));
        if (getConfig().getString("Chat.Enabled").equals("true")) {
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "チャット制限: &atrue"));
        }
        else {
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "チャット制限: &cfalse"));
        }
        if (getConfig().getString("Command.Enabled").equals("true")) {
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "コマンド制限: &atrue"));
        }
        else {
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "コマンド制限: &cfalse"));
        }
        getLogger().info("");

        saveDefaultConfig();
        createWordConfig();
        getServer().getPluginManager().registerEvents(new PlayerChat(this),this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(this),this);

        getCommand("chatlimit").setExecutor(new ChatLimitCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("プラグインが停止しました");
        getLogger().info("Goodbye!");
    }

    public FileConfiguration getWordConfig() {
        return this.WordConfig;
    }

    private void createWordConfig() {
        WordConfigFile = new File(getDataFolder(), "word.yml");
        if (!WordConfigFile.exists()) {
            WordConfigFile.getParentFile().mkdirs();
            saveResource("word.yml", false);
        }

        WordConfig = new YamlConfiguration();
        try {
            WordConfig.load(WordConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
