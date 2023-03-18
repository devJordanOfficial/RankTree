package org.infamousmc.ranktree.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.infamousmc.ranktree.Main;
import org.infamousmc.ranktree.UserInterface.RanksInterface;

public class RanksCommand implements CommandExecutor {
    private Main plugin;

    public RanksCommand(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("ranks")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;

            if (!player.hasPermission("rank.path.rome") &&
                    !player.hasPermission("rank.path.greece") &&
                    !player.hasPermission("rank.path.japan")) {

                player.sendMessage(plugin.format("&c&lOOPS! &7You must select a path first - &c/path"));
                return false;
            }

            RanksInterface ranksInterface = new RanksInterface(plugin);
            ranksInterface.openGUI(player);
        }

        return false;
    }
}
