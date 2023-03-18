package org.infamousmc.ranktree.Commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.infamousmc.ranktree.Data.Texture;
import org.infamousmc.ranktree.Main;
import org.infamousmc.ranktree.UserInterface.RankupInterface;

public class RankupCommand implements CommandExecutor {
    private Main plugin;

    public RankupCommand(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("rankup")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;

            if (!player.hasPermission("rank.path.rome") &&
                    !player.hasPermission("rank.path.greece") &&
                    !player.hasPermission("rank.path.japan")) {

                player.sendMessage(plugin.format("&c&lOOPS! &7You must select a path first - &c/path"));
                return false;
            }

            if (plugin.hdb.getItemHead(Texture.ROME.getID()) == null) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.format(
                        "&c&lOOPS! &7The server is still loading, please try again"
                )));
                return false;
            }

            if (!player.hasPermission("rank.infamous")) {
                RankupInterface rankupInterface = new RankupInterface(plugin);
                rankupInterface.openGUI(player);
            } else {
                player.sendMessage("Prestige Menu");
            }
            return true;
        }

        return false;
    }
}
