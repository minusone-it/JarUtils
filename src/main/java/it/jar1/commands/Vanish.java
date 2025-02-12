package it.jar1.commands;

import it.jar1.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;
import java.util.*;

public class Vanish implements CommandExecutor
{
    private final JarUtils plugin;
    private List<Player> vanished_players;
    
    public Vanish(final JarUtils plugin) {
        this.vanished_players = new ArrayList<Player>();
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (commandSender instanceof Player && commandSender.hasPermission("jarutils.vanish")) {
            final Player p = (Player)commandSender;
            if (this.vanished_players.contains(p)) {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(p);
                }
                this.vanished_players.remove(p);
                p.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Vanish §4Disabled!") : (this.plugin.prefix + "Vanish §4Disabilitata!"));
            }
            else if (!this.vanished_players.contains(p)) {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPermission("jarutils.vanish.cansee")) {
                        player.hidePlayer(p);
                    }
                }
                this.vanished_players.add(p);
                p.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Vanish §aEnabled!") : (this.plugin.prefix + "Vanish §2Abilitata!"));
            }
            return true;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(this.plugin.prefix + (JarUtils.lang.contains("en") ? "§cYou have to login as a player to execute this command!" : "§cDevi essere un player per eseguire questo comando!"));
            return true;
        }
        return false;
    }
}
