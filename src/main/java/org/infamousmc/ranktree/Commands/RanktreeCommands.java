package org.infamousmc.ranktree.Commands;

import org.infamousmc.ranktree.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RanktreeCommands implements CommandExecutor {

    private Main plugin;

    public RanktreeCommands(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) return false;
        if (args[0].equalsIgnoreCase("reload")) {

            if (!sender.hasPermission("ranktree.admin")) {
                sender.sendMessage(plugin.format("&c&lOOPS! &7You do not have the permission &cranktree.admin&7!"));
                return true;
            }

            plugin.mainConfig.reloadConfig();
            sender.sendMessage(plugin.format("&a&lSUCCESS! &7The config has been reloaded!"));
        }
            return false;
    }
}
