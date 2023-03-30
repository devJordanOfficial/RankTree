package org.infamousmc.ranktree;

import org.infamousmc.ranktree.Data.Rank;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public class RankTreeAPI {

    private static Main plugin;

    public RankTreeAPI() {
        plugin = Main.getInstance();
    }

    public double getRankCost(Rank rank) {
        DecimalFormat df = new DecimalFormat("#.##");
        double cost;

        ConfigurationSection rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks." + rank.order);
        cost = rankConfig.getDouble("cost");

        cost = Double.parseDouble(df.format(cost));
        return cost;
    }

}
