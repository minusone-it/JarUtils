package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static it.jar1.JarUtils.lang;


public class Fly implements CommandExecutor {
    List<Player> flying_players = new ArrayList<>();
    private final JarUtils plugin;
    public Fly(JarUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player & sender.hasPermission("jarutils.fly") && !(args.length >= 1)) {
            Player p = (Player) sender;
            if (!flying_players.contains(p)) {
                flying_players.add(p);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage(lang.contains("en") ? plugin.prefix + "Fly §2Enabled." : plugin.prefix + "Fly §2Abilitata.");
            } else {
                flying_players.remove(p);
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage(lang.contains("en") ? plugin.prefix + "Fly §4Disabled." : plugin.prefix + "Fly §4Disabilitata.");
            }
            return true;
        } else if (sender.hasPermission("jarutils.fly") && args.length >= 1) {
            String targetPlayerName = args[0];
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer != null) {
                if (!flying_players.contains(targetPlayer)) {
                    flying_players.add(targetPlayer);
                    targetPlayer.setAllowFlight(true);
                    targetPlayer.setFlying(true);
                    targetPlayer.sendMessage(lang.contains("en") ? plugin.prefix + "Fly §2Enabled by " + sender.getName() + "." : plugin.prefix + "Fly §2Abilitata da " + sender.getName() + ".");
                    sender.sendMessage(lang.contains("en") ? plugin.prefix + "Fly §2Enabled for " + targetPlayer.getName() + "." : plugin.prefix + "Fly §2Abilitata per " + targetPlayer.getName() + ".");
                } else {
                    flying_players.remove(targetPlayer);
                    targetPlayer.setAllowFlight(false);
                    targetPlayer.setFlying(false);
                    targetPlayer.sendMessage(lang.contains("en") ? plugin.prefix + "Fly §4Disabled by " + sender.getName() + "." : plugin.prefix + "Fly §4Disabilitata da " + sender.getName() + ".");
                    sender.sendMessage(lang.contains("en") ? plugin.prefix + "Fly §4Disabled for " + targetPlayer.getName() + "." : plugin.prefix + "Fly §4Disabilitata per " + targetPlayer.getName() + ".");
                }
            } else {
                sender.sendMessage(lang.contains("en") ? plugin.prefix + "Player "+targetPlayerName+" not found" : plugin.prefix + "Player "+targetPlayerName+" non trovato");
            }
            return true;
        }
        return false;
    }
}
