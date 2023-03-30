package org.infamousmc.ranktree;

import org.bukkit.configuration.ConfigurationSection;
import org.infamousmc.ranktree.Data.Rank;

import java.text.DecimalFormat;

public class RankTreeAPI {

    private static final Main plugin = Main.getPlugin(Main.class);

    private RankTreeAPI() {}

    public static double getRankCost(Rank rank) {
        DecimalFormat df = new DecimalFormat("#.##");
        double cost;

        ConfigurationSection rankConfig = plugin.ranksConfig.getConfig().getConfigurationSection("ranks." + rank.order);
        cost = rankConfig.getDouble("cost");

        cost = Double.parseDouble(df.format(cost));
        return cost;
    }

}
