package org.infamousmc.ranktree.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.infamousmc.ranktree.Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager {

    private Main plugin;
    private FileConfiguration ranksConfig = null;
    private File configFile = null;
    private String fileName;

    public ConfigManager(Main plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), fileName);

        ranksConfig = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            ranksConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (ranksConfig == null)
            reloadConfig();
        return ranksConfig;
    }

    public void saveConfig() {
        if (ranksConfig == null || configFile == null)
            return;

        try {
            getConfig().save(configFile);
        } catch(IOException e) {
            Logger.severe("&7Could not save config to " + configFile);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), fileName);
        }
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

}
