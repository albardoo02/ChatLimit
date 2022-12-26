package com.github.albardoo02;

import com.github.albardoo02.command.ChatLimitCommand;
import com.github.albardoo02.listener.PlayerChat;
import com.github.albardoo02.listener.PlayerCommandPreprocess;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class ChatLimit extends JavaPlugin {

    public DataManager data;


    public String version = getDescription().getVersion();
    public String getPrefix() {
        return getConfig().getString(ChatColor.translateAlternateColorCodes('&',"prefix"));
    }

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

        this.data = new DataManager(this);

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerChat(this),this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(this),this);

        getCommand("chatlimit").setExecutor(new ChatLimitCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("プラグインが停止しました");
        getLogger().info("Goodbye!");
    }
}
