package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnMute implements CommandExecutor {
    JarUtils plugin;
    public UnMute(JarUtils plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("jarutils.unmute")) {
            if (args.length > 0) {
                if (plugin.muted_players.contains(args[0])) {
                    plugin.muted_players.remove(Bukkit.getServer().getPlayer(args[0]));
                    plugin.muted_players_reasons.remove(args[0]);
                    sender.sendMessage(plugin.prefix + args[0] + " has been successfully unmuted.");
                } else {
                    sender.sendMessage(plugin.prefix + args[0] + " hasn't got a mute.");
                }
            }
            return true;
        }
        return false;
    }
}
