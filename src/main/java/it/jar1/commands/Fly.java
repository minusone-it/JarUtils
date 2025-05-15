package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Fly implements CommandExecutor
{
    List<Player> flying_players;
    private final JarUtils plugin;
    
    public Fly(final JarUtils plugin) {
        this.flying_players = new ArrayList<Player>();
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if ((sender instanceof Player & sender.hasPermission("jarutils.fly")) && args.length < 1) {
            final Player p = (Player)sender;
            if (!this.flying_players.contains(p)) {
                this.flying_players.add(p);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Fly §2Enabled.") : (this.plugin.prefix + "Fly §2Abilitata."));
            }
            else {
                this.flying_players.remove(p);
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Fly §4Disabled.") : (this.plugin.prefix + "Fly §4Disabilitata."));
            }
            return true;
        }
        if (sender.hasPermission("jarutils.fly") && args.length >= 1) {
            final String targetPlayerName = args[0];
            final Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
            if (targetPlayer != null) {
                if (!this.flying_players.contains(targetPlayer)) {
                    this.flying_players.add(targetPlayer);
                    targetPlayer.setAllowFlight(true);
                    targetPlayer.setFlying(true);
                    targetPlayer.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Fly §2Enabled by " + sender.getName() + ".") : (this.plugin.prefix + "Fly §2Abilitata da " + sender.getName() + "."));
                    sender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Fly §2Enabled for " + targetPlayer.getName() + ".") : (this.plugin.prefix + "Fly §2Abilitata per " + targetPlayer.getName() + "."));
                }
                else {
                    this.flying_players.remove(targetPlayer);
                    targetPlayer.setAllowFlight(false);
                    targetPlayer.setFlying(false);
                    targetPlayer.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Fly §4Disabled by " + sender.getName() + ".") : (this.plugin.prefix + "Fly §4Disabilitata da " + sender.getName() + "."));
                    sender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Fly §4Disabled for " + targetPlayer.getName() + ".") : (this.plugin.prefix + "Fly §4Disabilitata per " + targetPlayer.getName() + "."));
                }
            }
            else {
                sender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Player " + targetPlayerName + " not found") : (this.plugin.prefix + "Player " + targetPlayerName + " non trovato"));
            }
            return true;
        }
        return false;
    }
}
