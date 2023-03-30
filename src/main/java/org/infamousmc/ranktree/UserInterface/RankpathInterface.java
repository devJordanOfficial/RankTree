package org.infamousmc.ranktree.UserInterface;

import org.infamousmc.ranktree.Data.ItemTag;
import org.infamousmc.ranktree.Data.Texture;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.infamousmc.ranktree.Data.ItemTagType;
import org.infamousmc.ranktree.Main;

import java.util.ArrayList;

public class RankpathInterface {
    private Main plugin;

    private Inventory inv;

    public RankpathInterface(Main plugin) {
        this.plugin = plugin;
    }

    private void createInventory(Player player) {
        inv = Bukkit.createInventory(null, 27, plugin.format
                ("#22103a&l!!! #52227c&lChoose a Rank Path #22103a&l!!!"));
        initializeItems(player);
    }

    private void initializeItems(Player player) {

        inv.setItem(11, romeSkull());
        inv.setItem(13, greeceSkull());
        inv.setItem(15, japanSkull());
        inv.setItem(26, info());

        plugin.fillSpacer(inv);
    }

    public void openGUI(Player player) {
        createInventory(player);
        plugin.openInv.put(player.getUniqueId(), inv);
        player.openInventory(inv);
    }

    private ItemStack romeSkull() {
        ItemStack item = plugin.hdb.getItemHead(Texture.ROME.getID());
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(plugin.format("&c&lRome"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(plugin.format("&7The empire of Strength"));
        lore.add("");
        lore.add(plugin.format("#c6c1f5&l>#c6c1f5&m=  #5a278d&l Ranks #c6c1f5&m         "));
        lore.add(plugin.format(" &f&l1 #c6c1f5&l> &fServus"));
        lore.add(plugin.format(" &f&l2 #c6c1f5&l> &fOperarius"));
        lore.add(plugin.format(" &f&l3 #c6c1f5&l> &fFaber"));
        lore.add(plugin.format(" &f&l4 #c6c1f5&l> &fMercator"));
        lore.add(plugin.format(" &f&l5 #c6c1f5&l> &fLegionnaire"));
        lore.add(plugin.format(" &f&l6 #c6c1f5&l> &fPraetor"));
        lore.add(plugin.format(" &f&l7 #c6c1f5&l> &fEquestrian"));
        lore.add(plugin.format(" &f&l8 #c6c1f5&l> &fSacerdos"));
        lore.add(plugin.format(" &f&l9 #c6c1f5&l> &fSenator"));
        lore.add(plugin.format("&f&l10 #c6c1f5&l> &fCaesar"));
        lore.add(plugin.format("&f&l11 #c6c1f5&l> &fInfamous"));
//        lore.add("");
//        lore.add(plugin.format("#c6c1f5&l>#c6c1f5&m=  #5a278d&l Additional Perks #c6c1f5&m         "));
//        lore.add(plugin.format("#c6c1f5&l> &f10% MCMMO EXP Boost in..."));
//        lore.add(plugin.format("#c6c1f5&l-> &fArchery"));
//        lore.add(plugin.format("#c6c1f5&l-> &fAxes"));
//        lore.add(plugin.format("#c6c1f5&l-> &fSwords")); // Disabled until MCMMO is implemented
        meta.setLore(lore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey tag = new NamespacedKey(plugin, "tag");
        container.set(tag, new ItemTagType(), ItemTag.ROME);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack greeceSkull() {
        ItemStack item = plugin.hdb.getItemHead(Texture.GREECE.getID());
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(plugin.format("&a&lGreece"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(plugin.format("&7The empire of Ingenuity"));
        lore.add("");
        lore.add(plugin.format("#c6c1f5&l>#c6c1f5&m=  #5a278d&l Ranks #c6c1f5&m         "));
        lore.add(plugin.format(" &f&l1 #c6c1f5&l> &fDuolos"));
        lore.add(plugin.format(" &f&l2 #c6c1f5&l> &fChorikos"));
        lore.add(plugin.format(" &f&l3 #c6c1f5&l> &fTektok"));
        lore.add(plugin.format(" &f&l4 #c6c1f5&l> &fEmporos"));
        lore.add(plugin.format(" &f&l5 #c6c1f5&l> &fHoplite"));
        lore.add(plugin.format(" &f&l6 #c6c1f5&l> &fDikaste"));
        lore.add(plugin.format(" &f&l7 #c6c1f5&l> &fArchon"));
        lore.add(plugin.format(" &f&l8 #c6c1f5&l> &fPresbyteros"));
        lore.add(plugin.format(" &f&l9 #c6c1f5&l> &fPlousios"));
        lore.add(plugin.format("&f&l10 #c6c1f5&l> &fAnax"));
        lore.add(plugin.format("&f&l11 #c6c1f5&l> &fInfamous"));
//        lore.add("");
//        lore.add(plugin.format("#c6c1f5&l>#c6c1f5&m=  #5a278d&l Additional Perks #c6c1f5&m         "));
//        lore.add(plugin.format("#c6c1f5&l> &f10% MCMMO EXP Boost in..."));
//        lore.add(plugin.format("#c6c1f5&l-> &fExcavation"));
//        lore.add(plugin.format("#c6c1f5&l-> &fMining"));
//        lore.add(plugin.format("#c6c1f5&l-> &fWoodcutting")); // Disabled until MCMMO is implemented
        meta.setLore(lore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey tag = new NamespacedKey(plugin, "tag");
        container.set(tag, new ItemTagType(), ItemTag.GREECE);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack japanSkull() {
        ItemStack item = plugin.hdb.getItemHead(Texture.JAPAN.getID());
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(plugin.format("&e&lJapan"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(plugin.format("&7The empire of Intelligence"));
        lore.add("");
        lore.add(plugin.format("#c6c1f5&l>#c6c1f5&m=  #5a278d&l Ranks #c6c1f5&m         "));
        lore.add(plugin.format(" &f&l1 #c6c1f5&l> &fDorei"));
        lore.add(plugin.format(" &f&l2 #c6c1f5&l> &fRodo-sha"));
        lore.add(plugin.format(" &f&l3 #c6c1f5&l> &fShokunin"));
        lore.add(plugin.format(" &f&l4 #c6c1f5&l> &fShonin"));
        lore.add(plugin.format(" &f&l5 #c6c1f5&l> &fHeishi"));
        lore.add(plugin.format(" &f&l6 #c6c1f5&l> &fSaibankan"));
        lore.add(plugin.format(" &f&l7 #c6c1f5&l> &fSamurai"));
        lore.add(plugin.format(" &f&l8 #c6c1f5&l> &fSaishi"));
        lore.add(plugin.format(" &f&l9 #c6c1f5&l> &fJoin-giin"));
        lore.add(plugin.format("&f&l10 #c6c1f5&l> &fTen'no"));
        lore.add(plugin.format("&f&l11 #c6c1f5&l> &fInfamous"));
//        lore.add("");
//        lore.add(plugin.format("#c6c1f5&l>#c6c1f5&m=  #5a278d&l Additional Perks #c6c1f5&m         "));
//        lore.add(plugin.format("#c6c1f5&l> &f10% MCMMO EXP Boost in..."));
//        lore.add(plugin.format("#c6c1f5&l-> &fAcrobatics"));
//        lore.add(plugin.format("#c6c1f5&l-> &fAlchemy"));
//        lore.add(plugin.format("#c6c1f5&l-> &fRepair")); // Disabled until MCMMO is implemented
        meta.setLore(lore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey tag = new NamespacedKey(plugin, "tag");
        container.set(tag, new ItemTagType(), ItemTag.JAPAN);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack info() {
        ItemStack item = plugin.hdb.getItemHead(Texture.INFO.getID());
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(plugin.format("#5a278dInformation"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(plugin.format("&7Rank paths have a few key differences;"));
        lore.add(plugin.format("&7those being different rank names (besides"));
        lore.add(plugin.format("&7the final \"Infamous\" rank); and the spawnpoint,"));
        lore.add(plugin.format("&7however all spawnpoints are interconnected,"));
        lore.add(plugin.format("&7you will still have access to each spawnpoint"));
        lore.add(plugin.format("&7and their specific advantages."));//; and specific"));
//        lore.add(plugin.format("&7MCMMO EXP boosts."));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
