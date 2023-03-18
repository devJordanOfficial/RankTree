package org.infamousmc.ranktree.Data;

import org.bukkit.entity.Player;

public enum Path {
    ROME("Roman"),
    GREECE("Greek"),
    JAPAN("Japanese");

    public final String nationality;

    private Path(String nationality) {
        this.nationality = nationality;
    }

    public static Path get(Player player) {
        if (player.hasPermission("rank.path.rome"))
            return ROME;
        if (player.hasPermission("rank.path.greece"))
            return GREECE;
        if (player.hasPermission("rank.path.japan"))
            return JAPAN;
        return null;
    }
}
