package org.infamousmc.ranktree.Data;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.infamousmc.ranktree.Main;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class RankDisplay {
    private Main plugin;
    private NumberFormat numberFormat = NumberFormat.getInstance();

    public RankDisplay(Main plugin) {
        this.plugin = plugin;
    }

    public ItemStack FIRST(Player player) {
        ConfigurationSection rank = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.1"); // !
        Rank rankEnum = Rank.FIRST; // !
        if (rank == null) return null;

        String rankName = null;
        if (player.hasPermission("rank.path.rome")) {
            rankName = rankEnum.rome;
        }
        if (player.hasPermission("rank.path.greece")) {
            rankName = rankEnum.greece;
        }
        if (player.hasPermission("rank.path.japan")) {
            rankName = rankEnum.japan;
        }
        if (rankName == null) return null;
        String rankTitle = rankName.substring(0,1).toUpperCase() + rankName.substring(1);

        // If the player has the requirements enchant the item and remove the "LOCKED" lore
        boolean hasRequirements = false;
        if (plugin.economy.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId())) >= rank.getDouble("cost"))
            hasRequirements = true;

        Material type = Material.RED_STAINED_GLASS_PANE;
        if (player.hasPermission("rank." + rankName)) {
            type = Material.LIME_STAINED_GLASS_PANE;
            hasRequirements = true;
        }

        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(plugin.format(parse(rank.getString("display.name"), rank, rankTitle)));
        if (hasRequirements) {
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (!player.hasPermission("rank." + rankName)) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                NamespacedKey tag = new NamespacedKey(plugin, "tag");
                container.set(tag, new ItemTagType(), ItemTag.HAS_REQUIREMENTS);
            }
        }

        ArrayList<String> lore = new ArrayList<>();

        if (player.hasPermission("rank.path.rome")) {
            lore.add(plugin.format("&7Pronounced \"" + rankEnum.pronounceRome + "\""));
        }
        if (player.hasPermission("rank.path.greece")) {
            lore.add(plugin.format("&7Pronounced \"" + rankEnum.pronounceGreece + "\""));
        }
        if (player.hasPermission("rank.path.japan")) {
            lore.add(plugin.format("&7Pronounced \"" + rankEnum.pronounceJapan + "\""));
        }

        // Sets the lore formatted and with placeholders
        List<String> list = rank.getStringList("display.lore");
        lore.addAll(list);
        String s;
        for (int i = 0; i < lore.size(); i++) {
            s = plugin.format(parse(lore.get(i), rank));
            lore.set(i, s);
        }

        if (!hasRequirements) {
            lore.add("");
            lore.add(plugin.format("&4&l!!! &c&lLOCKED &4&l!!!"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack SECOND(Player player) {
        return base(player, Rank.SECOND);
    }

    public ItemStack THIRD(Player player) {
        return base(player, Rank.THIRD);
    }

    public ItemStack FOURTH(Player player) {
        return base(player, Rank.FOURTH);
    }

    public ItemStack FIFTH(Player player) {
        return base(player, Rank.FIFTH);
    }

    public ItemStack SIXTH(Player player) {
        return base(player, Rank.SIXTH);
    }

    public ItemStack SEVENTH(Player player) {
        return base(player, Rank.SEVENTH);
    }

    public ItemStack EIGHTH(Player player) {
        return base(player, Rank.EIGHTH);
    }

    public ItemStack NINTH(Player player) {
        return base(player, Rank.NINTH);
    }

    public ItemStack TENTH(Player player) {
        return base(player, Rank.TENTH);
    }

    public ItemStack ELEVENTH(Player player) {
        ConfigurationSection rank = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.11");
        Rank previous = Rank.TENTH;
        if (rank == null) return null;

        String rankName = Rank.ELEVENTH.rome;
        String rankTitle = rankName.substring(0,1).toUpperCase() + rankName.substring(1);

        // If the player has the requirements enchant the item and remove the "LOCKED" lore
        boolean hasRequirements = false;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        for (String s : Rank.pathValues(previous)) {
            if (!player.hasPermission("rank." + s)) continue;
            if (plugin.economy.getBalance(offlinePlayer) >= rank.getDouble("cost")) {
                hasRequirements = true;
            }
        }

        // If the player already has "Infamous" show that
        Material type = Material.RED_STAINED_GLASS_PANE;
        if (player.hasPermission("rank." + rankName)) {
            type = Material.LIME_STAINED_GLASS_PANE;
            hasRequirements = true;
        }

        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(plugin.format(parse(rank.getString("display.name"), rank, rankTitle)));
        if (hasRequirements) {
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (!player.hasPermission("rank." + rankName)) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                NamespacedKey tag = new NamespacedKey(plugin, "tag");
                container.set(tag, new ItemTagType(), ItemTag.HAS_REQUIREMENTS);
            }
        }

        ArrayList<String> lore = new ArrayList<>();

        lore.add(plugin.format("&7Pronounced \"in-fAH-mUS\""));

        // Sets the lore formatted and with placeholders
        List<String> list = rank.getStringList("display.lore");
        lore.addAll(list);
        String s;
        for (int i = 0; i < lore.size(); i++) {
            s = plugin.format(parse(lore.get(i), rank));
            lore.set(i, s);
        }
        //

        if (!hasRequirements) {
            lore.add("");
            lore.add(plugin.format("&4&l!!! &c&lLOCKED &4&l!!!"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack base(Player player, Rank rank) {
        ConfigurationSection rankConfig = null;
        Rank previous = null;

        switch (rank) {
            case FIRST:
                throw new IllegalArgumentException("Rank 1 cannot be created through the \"base\" method");
            case SECOND:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.2");
                previous = Rank.FIRST;
                break;
            case THIRD:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.3");
                previous = Rank.SECOND;
                break;
            case FOURTH:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.4");
                previous = Rank.THIRD;
                break;
            case FIFTH:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.5");
                previous = Rank.FOURTH;
                break;
            case SIXTH:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.6");
                previous = Rank.FIFTH;
                break;
            case SEVENTH:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.7");
                previous = Rank.SIXTH;
                break;
            case EIGHTH:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.8");
                previous = Rank.SEVENTH;
                break;
            case NINTH:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.9");
                previous = Rank.EIGHTH;
                break;
            case TENTH:
                rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks.10");
                previous = Rank.NINTH;
                break;
            case ELEVENTH:
                throw new IllegalArgumentException("Rank 11 cannot be created through the \"base\" method");
        }

        if (rankConfig == null) return null;

        String rankName = null;
        if (player.hasPermission("rank.path.rome")) {
            rankName = rank.rome;
        }
        if (player.hasPermission("rank.path.greece")) {
            rankName = rank.greece;
        }
        if (player.hasPermission("rank.path.japan")) {
            rankName = rank.japan;
        }
        if (rankName == null) return null;
        String rankTitle = rankName.substring(0,1).toUpperCase() + rankName.substring(1);

        // If the player has the requirements enchant the item and remove the "LOCKED" lore
        boolean hasRequirements = false;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        for (String s : Rank.pathValues(previous)) {
            if (!player.hasPermission("rank." + s)) continue;
            if (plugin.economy.getBalance(offlinePlayer) >= rankConfig.getDouble("cost")) {
                hasRequirements = true;
            }
        }

        Material type = Material.RED_STAINED_GLASS_PANE;
        if (player.hasPermission("rank." + rankName)) {
            type = Material.LIME_STAINED_GLASS_PANE;
            hasRequirements = true;
        }

        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(plugin.format(parse(rankConfig.getString("display.name"), rankConfig, rankTitle)));
        if (hasRequirements) {
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (!player.hasPermission("rank." + rankName)) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                NamespacedKey tag = new NamespacedKey(plugin, "tag");
                container.set(tag, new ItemTagType(), ItemTag.HAS_REQUIREMENTS);
            }
        }

        ArrayList<String> lore = new ArrayList<>();

        if (player.hasPermission("rank.path.rome")) {
            lore.add(plugin.format("&7Pronounced \"" + rank.pronounceRome + "\""));
        }
        if (player.hasPermission("rank.path.greece")) {
            lore.add(plugin.format("&7Pronounced \"" + rank.pronounceGreece + "\""));
        }
        if (player.hasPermission("rank.path.japan")) {
            lore.add(plugin.format("&7Pronounced \"" + rank.pronounceJapan + "\""));
        }

        // Sets the lore formatted and with placeholders
        List<String> list = rankConfig.getStringList("display.lore");
        lore.addAll(list);
        String s;
        for (int i = 0; i < lore.size(); i++) {
            s = plugin.format(parse(lore.get(i), rankConfig));
            lore.set(i, s);
        }

        if (!hasRequirements) {
            lore.add("");
            lore.add(plugin.format("&4&l!!! &c&lLOCKED &4&l!!!"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private String parse(String string, ConfigurationSection section) {
        String cost = numberFormat.format(section.getDouble("cost"));
        string = string.replace("%cost%", cost);
        return string;
    }

    private String parse(String string, ConfigurationSection section, String title) {
        string = this.parse(string, section);
        string = string.replace("%name%", title);
        return string;
    }
}
