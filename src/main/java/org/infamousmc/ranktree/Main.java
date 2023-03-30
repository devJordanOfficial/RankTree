package org.infamousmc.ranktree;

import org.infamousmc.ranktree.Commands.RankpathCommand;
import org.infamousmc.ranktree.Commands.RanksCommand;
import org.infamousmc.ranktree.Commands.RankupCommand;
import org.infamousmc.ranktree.Data.ConfigManager;
import org.infamousmc.ranktree.Data.Rank;
import org.infamousmc.ranktree.Events.GuiHandler;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.infamousmc.ranktree.Commands.RanktreeCommands;
import org.infamousmc.ranktree.Data.Logger;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main extends JavaPlugin {

    private static Main instance;

    public HeadDatabaseAPI hdb;
    public LuckPerms lp;
    public Economy economy;

    public ConfigManager mainConfig;
    public ConfigManager ranksConfig;

    public HashMap<UUID, Inventory> openInv = new HashMap<>();
    public HashMap<UUID, Rank> rankCache = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager pluginManager = getServer().getPluginManager();

        if (pluginManager.getPlugin("HeadDatabase") == null) {
            noAPI("HeadDatabase");
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        if (pluginManager.getPlugin("LuckPerms") == null) {
            noAPI("LuckPerms");
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        if (pluginManager.getPlugin("Vault") == null) {
            noAPI("Vault");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        // Commands
        this.getCommand("ranktree").setExecutor(new RanktreeCommands(this));
        this.getCommand("rankup").setExecutor(new RankupCommand(this));
        this.getCommand("rankpath").setExecutor(new RankpathCommand(this));
        this.getCommand("ranks").setExecutor(new RanksCommand(this));
        // Events
        pluginManager.registerEvents(new GuiHandler(this), this);

        hdb = new HeadDatabaseAPI();
        lp = LuckPermsProvider.get();
        if (!setupEconomy()) {
            Logger.severe("&7Vault economy failed to initialize! This plugin relies on Vault and therefore " +
                    "has been disabled.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        mainConfig = new ConfigManager(this, "config.yml");
        ranksConfig = new ConfigManager(this, "ranks.yml");

        if (mainConfig.getConfig().getString("roman-spawn.world") == null) {
            Logger.severe("World is null for roman spawn therefore the plugin has been disabled. Please " +
                    "check your config.yml and restart the server.");
            getServer().getPluginManager().disablePlugin(this);
        }

        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("test")) {
            Player player = (Player) sender;


        }
        return true;
    }

    private void noAPI(String plugin) {
        Logger.severe("&7" + plugin + " was not found! Please add the plugin to the server. " +
                "RankTree does not work without it.");
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        economy = rsp.getProvider();
        return true;
    }

    public void fillSpacer(Inventory inv) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(format("&r"));
        item.setItemMeta(meta);
        for (int slot = 0; slot < inv.getSize(); slot++) {
            if (inv.getItem(slot) == null)
                inv.setItem(slot, item);
        }
    }

    public String format(String message) {
        final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            CharSequence input;
            matcher = pattern.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Main getInstance() {
        return instance;
    }

}
