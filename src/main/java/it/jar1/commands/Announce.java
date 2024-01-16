package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Sound;
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
                p.sendMessage(lang.contains("en") ? announceTitleColor + "Announce:§r " + message : announceTitleColor + "Annuncio:§r " + message);
                p.sendTitle(lang.contains("en") ? announceTitleColor + "Announce" : announceTitleColor + "Annuncio", message, 5, 130, 5);
                if(playAnnounceSound)
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0f, 1.0f);
            }
            return true;
        } else {
            commandSender.sendMessage(lang.contains("en") ? prefix + "§cCan't send message. Have you forgotten to insert an announcement message?" : prefix + "§cImpossibile mandare il messaggio. Ti sei per caso dimenticato di mettere il messaggio di annuncio?");
            return false;
        }
    }

}
