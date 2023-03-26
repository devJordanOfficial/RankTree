package org.infamousmc.ranktree.UserInterface;

import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.track.Track;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.infamousmc.ranktree.Data.*;
import org.infamousmc.ranktree.Main;


public class RankupInterface {
    private Main plugin;

    private Inventory inv;
    private RankDisplay rankDisplay;

    private Rank rank;

    public RankupInterface(Main main) {
        this.plugin = main;
        rankDisplay = new RankDisplay(plugin);
    }

    public void createInventory(Player player) {
        inv = Bukkit.createInventory(null, 27, "Rankup GUI");
        initializeItems(player);
    }

    private void initializeItems(Player player) {

        String skullID = null;

        if (player.hasPermission("rank.path.rome")) {
            skullID = Texture.ROME.getID();
        } else if (player.hasPermission("rank.path.greece")) {
            skullID = Texture.GREECE.getID();
        } else if (player.hasPermission("rank.path.japan")) {
            skullID = Texture.JAPAN.getID();
        } else {
            skullID = Texture.ERROR.getID();
        }

        String topRank = null;

        for (Rank rank : Rank.values()) {
            for (String s : Rank.pathValues(rank)) {
                if (player.hasPermission("rank." + s)) {
                    topRank = s;
                }
            }
        }

        if (topRank == null)
            rank = Rank.FIRST;
        else
            rank = Rank.valueOf(Rank.valueOfName(topRank).order + 1);

        ItemStack template = null;

        switch (rank) {
            case FIRST:
                template = rankDisplay.FIRST(player);
                break;
            case SECOND:
                template = rankDisplay.SECOND(player);
                break;
            case THIRD:
                template = rankDisplay.THIRD(player);
                break;
            case FOURTH:
                template = rankDisplay.FOURTH(player);
                break;
            case FIFTH:
                template = rankDisplay.FIFTH(player);
                break;
            case SIXTH:
                template = rankDisplay.SIXTH(player);
                break;
            case SEVENTH:
                template = rankDisplay.SEVENTH(player);
                break;
            case EIGHTH:
                template = rankDisplay.EIGHTH(player);
                break;
            case NINTH:
                template = rankDisplay.NINTH(player);
                break;
            case TENTH:
                template = rankDisplay.TENTH(player);
                break;
            case ELEVENTH:
                template = rankDisplay.ELEVENTH(player);
                break;
        }

        ItemStack item;
        ItemMeta meta;

        boolean hasRequirements;

        ConfigurationSection rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks." + rank.order);
        if (plugin.economy.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId())) >= rankConfig.getDouble("cost")) {
            hasRequirements = true;

            item = plugin.hdb.getItemHead(skullID);
//            if (item == null) return;
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

            skullMeta.setDisplayName(template.getItemMeta().getDisplayName());
            skullMeta.setLore(template.getItemMeta().getLore());

            item.setItemMeta(skullMeta);
        } else {
            hasRequirements = false;

            item = new ItemStack(Material.BARRIER);
            meta = item.getItemMeta();

            meta.setDisplayName(template.getItemMeta().getDisplayName());
            meta.setLore(template.getItemMeta().getLore());

            item.setItemMeta(meta);
        }

        inv.setItem(13, item);

        item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey tag = new NamespacedKey(plugin, "tag");

        if (hasRequirements) {
            meta.setDisplayName(plugin.format("#82ff54Accept"));
            container.set(tag, new ItemTagType(), ItemTag.ACCEPT);
            item.setItemMeta(meta);
            inv.setItem(11, item);
        } else {
            item.setType(Material.RED_STAINED_GLASS_PANE);
            meta.setDisplayName(plugin.format("#ff5454Reject"));
            container.set(tag, new ItemTagType(), ItemTag.REJECT);
            item.setItemMeta(meta);
            inv.setItem(11, item);
        }

        item.setType(Material.RED_STAINED_GLASS_PANE);
        meta.setDisplayName(plugin.format("#ff5454Reject"));
        container.set(tag, new ItemTagType(), ItemTag.REJECT);
        item.setItemMeta(meta);
        inv.setItem(15, item);

        plugin.fillSpacer(inv);
    }

    public void openGUI(Player player) {
        createInventory(player);
        plugin.openInv.put(player.getUniqueId(), inv);
        plugin.rankCache.put(player.getUniqueId(), rank);
        player.openInventory(inv);
    }

    private void promoteUser(Player player, String path) {
        User user = plugin.lp.getPlayerAdapter(Player.class).getUser(player);
        Track track = plugin.lp.getTrackManager().getTrack(path);
        track.promote(user, ImmutableContextSet.empty());
    }
}
