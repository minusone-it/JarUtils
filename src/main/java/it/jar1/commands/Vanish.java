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


public class Vanish implements CommandExecutor {
    private final JarUtils plugin;

    public Vanish(JarUtils plugin) {
        this.plugin = plugin;
    }
    private List<Player> vanished_players = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player && commandSender.hasPermission("jarutils.vanish")) {
            Player p = (Player) commandSender;
            if (vanished_players.contains(p)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(p);
                }
                vanished_players.remove(p);
                p.sendMessage(lang.contains("en") ? plugin.prefix + "Vanish §4Disabled!" : plugin.prefix + "Vanish §4Disabilitata!");
            } else if (!vanished_players.contains(p)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPermission("jarutils.vanish.cansee"))
                        player.hidePlayer(p);
                }
                vanished_players.add(p);
                p.sendMessage(lang.contains("en") ? plugin.prefix + "Vanish §aEnabled!" : plugin.prefix + "Vanish §2Abilitata!");
            }
            return true;
        } else if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.prefix + (lang.contains("en") ? "§cYou have to login as a player to execute this command!" : "§cDevi essere un player per eseguire questo comando!"));
            return true;
        }
        return false;
    }
}
