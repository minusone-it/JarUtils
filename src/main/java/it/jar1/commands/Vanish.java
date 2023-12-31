package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static it.jar1.JarUtils.prefix;

public class Vanish implements CommandExecutor {
    private final JarUtils plugin;

    public Vanish(JarUtils plugin) {
        this.plugin = plugin;
    }
    private List<Player> vanished_players = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player && commandSender.hasPermission("jarutils.vanish")) {
            Player p = (Player) commandSender;
            if(vanished_players.contains(p)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(p);
                }
                vanished_players.remove(p);
                p.sendMessage(prefix + "Vanish §4Disabled!");
            } else if (!vanished_players.contains(p)) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(!player.hasPermission("jarutils.vanish.cansee"))
                        player.hidePlayer(p);
                }
                vanished_players.add(p);
                p.sendMessage(prefix + "Vanish §aEnabled!");
            }
            return true;
        } else if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou have to login as a player to execute this command!");
            return true;
        }
        return false;
    }
}
