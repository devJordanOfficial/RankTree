package org.infamousmc.ranktree.Data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public enum Rank {
    ZERO("Wanderer", "wanderer", "wanderer", "wanderer", "wanderer" ,"wanderer" ,"wanderer", 0),
    FIRST("'Unpaid Worker'", "servus", "duolos", "dorei", "sER-vus", "doo-lEE-uus", "dohr-ai", 1),
    SECOND("Worker", "operarius", "chorikos", "rodo-sha", "oo-per-AIR-ee-OOS", "chOR-ee-khoes", "roe-doe-sah", 2),
    THIRD("Craftsman", "faber", "tekton", "shokunin", "fEYE-berg", "tehk-tawn", "shoh-koo-neen", 3),
    FOURTH("Merchant", "mercator", "emporos", "shonin", "merr-KA-tohr", "em-pohr-oes", "shAW-neen", 4),
    FIFTH("Soldier", "legionnaire", "hoplite", "heishi", "lEE-juh-nehr", "hAHp-liet", "hAY-see", 5),
    SIXTH("Judge", "praetor", "dikaste", "saibankan", "prEE-tr", "dEE-kahs-tay", "sie-bAn-kAn", 6),
    SEVENTH("Noble", "equestrian", "archon", "samurai", "uh-kewh-stree-uhn", "AHR-kahn", "sA-mr-ai", 7),
    EIGHTH("Priest", "sacerdos", "presbyteros", "saishi", "sa-chAIR-dohs", "prAY-bite-roes", "sIE-sEE", 8),
    NINTH("Senator", "senator", "plousios", "join-giin", "seh-nuh-tr", "plOO-see-uus", "joyn-gEEn", 9),
    TENTH("Emperor", "caesar", "anax", "ten'no", "SEE-zr", "an-ikhs", "tEH-nOH", 10),
    ELEVENTH("Infamous", "infamous", "infamous", "infamous", "in-fUH-mUHs", "in-fUH-mUHs", "in-fUH-mUHs", 11),
    MAX("Max", "max", "max", "max", "max", "max", "max", 12);

    public final String english;
    public final String rome;
    public final String greece;
    public final String japan;
    public final String pronounceRome;
    public final String pronounceGreece;
    public final String pronounceJapan;
    public final int order;

    private static final Map<String, Rank> BY_ROME = new HashMap<>();
    private static final Map<String, Rank> BY_GREECE = new HashMap<>();
    private static final Map<String, Rank> BY_JAPAN = new HashMap<>();
    private static final Map<Integer, Rank> BY_VALUE = new HashMap<>();

    static {
        for (Rank r : values()) {
            BY_ROME.put(r.rome, r);
            BY_GREECE.put(r.greece, r);
            BY_JAPAN.put(r.japan, r);
            BY_VALUE.put(r.order, r);
        }
    }

    private Rank(String english, String rome, String greece, String japan, String pronounceRome, String pronounceGreece, String pronounceJapan, int order) {
        this.english = english;
        this.rome = rome;
        this.greece = greece;
        this.japan = japan;
        this.pronounceRome = pronounceRome;
        this.pronounceGreece = pronounceGreece;
        this.pronounceJapan = pronounceJapan;
        this.order = order;
    }

    public static Rank valueOfName(String name) {
        if (BY_ROME.containsKey(name))
            return BY_ROME.get(name);
        if (BY_GREECE.containsKey(name))
            return BY_GREECE.get(name);
        if (BY_JAPAN.containsKey(name))
            return BY_JAPAN.get(name);

        return null;
    }

    public static Rank valueOf(int value) {
        return BY_VALUE.get(value);
    }

    public static String[] pathValues(Rank rank) {
        return new String[] {rank.rome, rank.greece, rank.japan};
    }

    public static Rank getNextRank(Player player) {
        Rank rank = getCurrentRank(player);

        if (rank == MAX)
            return MAX;

        return valueOf(getCurrentRank(player).order + 1);
    }

    public static Rank getCurrentRank(Player player) {
        if (player == null) {
            throw new NullPointerException("Player cannot be null");
        }

        Rank rank;
        String topRank = null;

        for (Rank r : Rank.values()) {
            for (String s : Rank.pathValues(r)) {
                if (player.hasPermission("rank." + s)) {
                    topRank = s;
                }
            }
        }

        if (topRank == null)
            rank = Rank.ZERO;
        else
            rank = Rank.valueOf(Rank.valueOfName(topRank).order);

        return rank;
    }
}
