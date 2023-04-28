package org.infamousmc.ranktree.Events;

import net.luckperms.api.track.PromotionResult;
import org.infamousmc.ranktree.Data.*;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.track.Track;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.infamousmc.ranktree.Main;

import java.util.UUID;

public class GuiHandler implements Listener {
    private Main plugin;

    public GuiHandler(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        if (!plugin.openInv.containsKey(uuid)) return;
        if (event.getView().getTopInventory() != plugin.openInv.get(uuid)) return;
        event.setCancelled(true);
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey tag = new NamespacedKey(plugin, "tag");
        if (!container.has(tag, new ItemTagType())) return;

        // PATH
        if (container.get(tag, new ItemTagType()) == ItemTag.ROME) {
            player.closeInventory();
            addPermission(uuid, "rank.path.rome");
            promote(player, "rome");
            player.teleport(new Location(
                    Bukkit.getWorld(plugin.mainConfig.getConfig().getString("roman-spawn.world")),
                    plugin.mainConfig.getConfig().getDouble("roman-spawn.x"),
                    plugin.mainConfig.getConfig().getDouble("roman-spawn.y"),
                    plugin.mainConfig.getConfig().getDouble("roman-spawn.z"),
                    plugin.mainConfig.getConfig().getInt("roman-spawn.yaw"),
                    plugin.mainConfig.getConfig().getInt("roman-spawn.pitch")
            ));
            rankup(player, "rome");
            effect(player, Path.ROME);
            return;
        }
        if (container.get(tag, new ItemTagType()) == ItemTag.GREECE) {
            player.closeInventory();
            addPermission(uuid, "rank.path.greece");
            promote(player, "greece");
            player.teleport(new Location(
                    Bukkit.getWorld(plugin.mainConfig.getConfig().getString("greek-spawn.world")),
                    plugin.mainConfig.getConfig().getDouble("greek-spawn.x"),
                    plugin.mainConfig.getConfig().getDouble("greek-spawn.y"),
                    plugin.mainConfig.getConfig().getDouble("greek-spawn.z"),
                    plugin.mainConfig.getConfig().getInt("greek-spawn.yaw"),
                    plugin.mainConfig.getConfig().getInt("greek-spawn.pitch")
            ));
            rankup(player, "greece");
            effect(player, Path.GREECE);
            return;
        }
        if (container.get(tag, new ItemTagType()) == ItemTag.JAPAN) {
            player.closeInventory();
            addPermission(uuid, "rank.path.japan");
            promote(player, "japan");
            player.teleport(new Location(
                    Bukkit.getWorld(plugin.mainConfig.getConfig().getString("japanese-spawn.world")),
                    plugin.mainConfig.getConfig().getDouble("japanese-spawn.x"),
                    plugin.mainConfig.getConfig().getDouble("japanese-spawn.y"),
                    plugin.mainConfig.getConfig().getDouble("japanese-spawn.z"),
                    plugin.mainConfig.getConfig().getInt("japanese-spawn.yaw"),
                    plugin.mainConfig.getConfig().getInt("japanese-spawn.pitch")
            ));
            rankup(player, "japan");
            effect(player, Path.JAPAN);
            return;
        }

        // RANKUP
        if (container.get(tag, new ItemTagType()) == ItemTag.ACCEPT) {
            Rank rank = plugin.rankCache.get(uuid);

            ConfigurationSection rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks." + rank.order);
            if (plugin.economy.getBalance(Bukkit.getOfflinePlayer(uuid)) >= rankConfig.getDouble("cost")) {
                plugin.economy.withdrawPlayer(Bukkit.getOfflinePlayer(uuid), rankConfig.getDouble("cost"));

                String path = null;
                if (player.hasPermission("rank.path.rome"))
                    path = "rome";
                if (player.hasPermission("rank.path.greece"))
                    path = "greece";
                if (player.hasPermission("rank.path.japan"))
                    path = "japan";

                rankup(player, path, rank);
                return;
            }
        }

        if (container.get(tag, new ItemTagType()) == ItemTag.REJECT) {
            player.closeInventory();
            return;
        }

        // RANKS
        if (container.get(tag, new ItemTagType()) == ItemTag.HAS_REQUIREMENTS) {
            Rank rank = plugin.rankCache.get(uuid);

            ConfigurationSection rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks." + rank.order);
            if (plugin.economy.getBalance(Bukkit.getOfflinePlayer(uuid)) >= rankConfig.getDouble("cost")) {
                plugin.economy.withdrawPlayer(Bukkit.getOfflinePlayer(uuid), rankConfig.getDouble("cost"));

                String path = null;
                if (player.hasPermission("rank.path.rome"))
                    path = "rome";
                if (player.hasPermission("rank.path.greece"))
                    path = "greece";
                if (player.hasPermission("rank.path.japan"))
                    path = "japan";

                rankup(player, path, rank);
            }
            return;
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        plugin.openInv.remove(uuid);
        plugin.rankCache.remove(uuid);
    }

    private void effect(Player player, Path path) {

        if (path == Path.ROME)
            player.sendTitle(plugin.format("&c&lWELCOME"), plugin.format("&7to the &cRoman &7civilization!"), 10, 30, 10);
        if (path == Path.GREECE)
            player.sendTitle(plugin.format("&a&lWELCOME"), plugin.format("&7to the &aGreek &7civilization!"), 10, 30, 10);
        if (path == Path.JAPAN)
            player.sendTitle(plugin.format("&e&lWELCOME"), plugin.format("&7to the &eJapanese &7civilization!"), 10, 30, 10);

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }

    private void addPermission(UUID uuid, String permission) {
        plugin.lp.getUserManager().modifyUser(uuid, user -> {
            user.data().add(Node.builder(permission).build());
        });
    }

    // Unused atm - Will be used when prestige is added
    private void removePermission(UUID uuid, String permission) {
        plugin.lp.getUserManager().modifyUser(uuid, user -> {
            user.data().remove(Node.builder(permission).build());
        });
    }

    private void promote(Player player, String path) {
        User user = plugin.lp.getPlayerAdapter(Player.class).getUser(player);
        Track track = plugin.lp.getTrackManager().getTrack(path);
        track.promote(user, ImmutableContextSet.empty());
    }

    private void rankup(Player player, String path) {
        rankup(player, path, null);
    }

    private void rankup(Player player, String path, Rank toRank) {

        User user = plugin.lp.getPlayerAdapter(Player.class).getUser(player);
        Track track = plugin.lp.getTrackManager().getTrack(path);
        PromotionResult result = track.promote(user, ImmutableContextSet.empty());
        Logger.log(player.getName() + " ranked up with a status of '" + result.getStatus() + "'.");
        if (!result.wasSuccessful()) Logger.severe("The previous rankup was unsuccessful");
        plugin.lp.getUserManager().saveUser(user);

        if (toRank == null) return;

        String rankName = null;
        if (player.hasPermission("rank.path.rome")) {
            rankName = StringUtils.capitalize(toRank.rome);
        }
        if (player.hasPermission("rank.path.greece")) {
            rankName = StringUtils.capitalize(toRank.greece);
        }
        if (player.hasPermission("rank.path.japan")) {
            rankName = StringUtils.capitalize(toRank.japan);
        }

        player.closeInventory();

        Bukkit.broadcastMessage(plugin.format("&5&l#A800A8&lR#B41BB4&lA#C036C0&lN#CD51CC&lK#D96DD8&lU#E688E4&lP#F2A3F0&l! &d" + player.getName()
                + "&7 has ranked up to &d" + rankName + "&7&o (" + toRank.english + ")"));

        for (int i = 0; i < 3; i++) {
            Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
            FireworkMeta meta = firework.getFireworkMeta();
            meta.addEffect(FireworkEffect.builder()
                    .with(FireworkEffect.Type.STAR)
                    .trail(true)
                    .withColor(Color.PURPLE, Color.FUCHSIA)
                    .withFade(Color.AQUA)
                    .build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }
    }
}
