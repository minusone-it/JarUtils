package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Mute implements CommandExecutor {
    JarUtils plugin;
    public Mute(JarUtils plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("jarutils.mute")) {
            if (args.length > 1) {
                if (!(plugin.muted_players.contains(args[0]))) {
                    try {
                        plugin.muted_players.add(Bukkit.getServer().getPlayerExact(args[0]));
                        plugin.muted_players_reasons.put(Bukkit.getServer().getPlayerExact(args[0]), args[1]);
                        sender.sendMessage(plugin.prefix + args[0] + " has been successfully muted.");
                    } catch (Exception e) {
                        sender.sendMessage(plugin.prefix + " Could not find " + args[0] + ".");
                    }
                } else {
                    sender.sendMessage(plugin.prefix + args[0] + " is already muted.");
                }
            }
            return true;
        }
        return false;
    }
}
