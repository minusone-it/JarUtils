package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class UnMute implements CommandExecutor
{
    JarUtils plugin;
    
    public UnMute(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("jarutils.unmute")) {
            if (args.length > 0) {
                if (this.plugin.muted_players.contains(Bukkit.getServer().getPlayer(args[0]))) {
                    this.plugin.muted_players.remove(Bukkit.getServer().getPlayer(args[0]));
                    this.plugin.muted_players_reasons.remove(Bukkit.getServer().getPlayer(args[0]));
                    this.plugin.muted_players_duration.remove(Bukkit.getServer().getPlayer(args[0]));
                    Mute.unmutePlayer(Bukkit.getServer().getPlayer(args[0]).getUniqueId());
                    sender.sendMessage(this.plugin.prefix + args[0] + " has been successfully unmuted.");
                }
                else {
                    sender.sendMessage(this.plugin.prefix + args[0] + " hasn't got a mute.");
                }
            }
            return true;
        }
        return false;
    }
}
