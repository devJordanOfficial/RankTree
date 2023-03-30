package org.infamousmc.ranktree.Commands;

import org.infamousmc.ranktree.Data.Texture;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.infamousmc.ranktree.Main;
import org.infamousmc.ranktree.UserInterface.RankpathInterface;

public class RankpathCommand implements CommandExecutor {
    private Main plugin;

    public RankpathCommand(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("rankpath") || label.equalsIgnoreCase("path")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;

            if (player.hasPermission("rank.path.rome") ||
                    player.hasPermission("rank.path.greece") ||
                    player.hasPermission("rank.path.japan")) {

                // CREATE TOKEN
                player.sendMessage(plugin.format("&c&lOOPS! &7You have already chosen your path."));
                return false;
            }

            if (plugin.hdb.getItemHead(Texture.ROME.getID()) == null) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.format(
                        "&c&lOOPS! &7The server is still loading, please try again"
                )));
                return false;
            }

            RankpathInterface rankpathInterface = new RankpathInterface(plugin);
            rankpathInterface.openGUI(player);
        }

        return false;
    }
}
