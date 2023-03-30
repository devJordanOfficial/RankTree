package org.infamousmc.ranktree.UserInterface;

import org.infamousmc.ranktree.Data.Path;
import org.infamousmc.ranktree.Data.Rank;
import org.infamousmc.ranktree.Data.RankDisplay;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.infamousmc.ranktree.Main;

public class RanksInterface {
    private Main plugin;

    private Inventory inv;
    private RankDisplay rankDisplay;
    private Rank rank;

    public RanksInterface(Main plugin) {
        this.plugin = plugin;
        rankDisplay = new RankDisplay(plugin);
    }

    private void createInventory(Player player) {
        inv = Bukkit.createInventory(null, 27, plugin.format
                ("#22103a&l!!! #52227c&l" + Path.get(player).nationality + " Ranks #22103a&l!!!"));
        initializeItems(player);
    }

    private void initializeItems(Player player) {

        String topRank = null;

        for (Rank rank : Rank.values()) {
            for (String s : Rank.pathValues(rank)) {
                if (player.hasPermission("rank." + s)) {
                    topRank = s;
                }
            }
        }

        System.out.println();
        if (topRank == null)
            rank = Rank.FIRST;
        else
            rank = Rank.valueOf(Rank.valueOfName(topRank).order + 1);

        inv.setItem(1, rankDisplay.FIRST(player));
        inv.setItem(2, rankDisplay.SECOND(player));
        inv.setItem(3, rankDisplay.THIRD(player));
        inv.setItem(4, rankDisplay.FOURTH(player));
        inv.setItem(5, rankDisplay.FIFTH(player));
        inv.setItem(6, rankDisplay.SIXTH(player));
        inv.setItem(7, rankDisplay.SEVENTH(player));
        inv.setItem(12, rankDisplay.EIGHTH(player));
        inv.setItem(13, rankDisplay.NINTH(player));
        inv.setItem(14, rankDisplay.TENTH(player));

        inv.setItem(22, rankDisplay.ELEVENTH(player));

        plugin.fillSpacer(inv);
    }

    public void openGUI(Player player) {
        createInventory(player);
        plugin.openInv.put(player.getUniqueId(), inv);
        plugin.rankCache.put(player.getUniqueId(), rank);
        player.openInventory(inv);
    }
}
