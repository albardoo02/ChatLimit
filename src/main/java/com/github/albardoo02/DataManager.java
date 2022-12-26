package com.github.albardoo02;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {

    private ChatLimit plugin;
    private FileConfiguration WordConfig = null;
    private File configFile = null;

    public DataManager(ChatLimit plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "word.yml");
        this.WordConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("word.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.WordConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if (this.WordConfig == null)
            reloadConfig();
        return this.WordConfig;
    }

    public void saveConfig() {
        if (this.WordConfig == null || this.configFile == null)
            return;
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "word.yml");
        if (!this.configFile.exists()) {
            this.plugin.saveResource("word.yml", false);
        }
    }
}
