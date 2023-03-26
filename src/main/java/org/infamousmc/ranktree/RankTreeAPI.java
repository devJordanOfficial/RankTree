package org.infamousmc.ranktree;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.infamousmc.ranktree.Data.Rank;

public class RankTreeAPI {

    Main plugin = Main.getPlugin(Main.class);

    public double getRankCost(Rank rank) {
        ConfigurationSection rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks." + rank.order);
        return rankConfig.getDouble("cost");
    }

}
