package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static it.jar1.JarUtils.*;

public class Announce implements CommandExecutor {
    private final JarUtils plugin;

    public Announce(JarUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.hasPermission("jarutils.announce") && args.length > 0) {
            String message = String.join(" ", args);
            message = message.replace("&", "§");

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(lang.equalsIgnoreCase("eng") ? announceTitleColor + "Announce: " + message : announceTitleColor + "Annuncio: " + message);
                p.sendTitle(lang.equalsIgnoreCase("eng") ? announceTitleColor + "Announce" : announceTitleColor + "Annuncio", message);
            }
            return true;
        } else {
            commandSender.sendMessage(lang.equalsIgnoreCase("eng") ? prefix + "Can't send message. Have you forgotten to insert an announcement message?" : prefix + "Impossibile mandare il messaggio. Ti sei per caso dimenticato di mettere il messaggio di annuncio?");
            return false;
        }
    }
}
