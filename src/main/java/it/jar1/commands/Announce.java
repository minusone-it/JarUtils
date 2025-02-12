package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class Announce implements CommandExecutor
{
    private final JarUtils plugin;
    
    public Announce(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (commandSender.hasPermission("jarutils.announce") && args.length > 0) {
            String message = String.join(" ", (CharSequence[])args);
            message = message.replace("&", "§");
            for (final Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(JarUtils.lang.contains("en") ? (JarUtils.announceTitleColor + "Announce:§r " + message) : (JarUtils.announceTitleColor + "Annuncio:§r " + message));
                p.sendTitle(JarUtils.lang.contains("en") ? (JarUtils.announceTitleColor + "Announce") : (JarUtils.announceTitleColor + "Annuncio"), message);
                if (JarUtils.playAnnounceSound) {
                    p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
                }
            }
            return true;
        }
        commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "§cCan't send message. Have you forgotten to insert an announcement message?") : (this.plugin.prefix + "§cImpossibile mandare il messaggio. Ti sei per caso dimenticato di mettere il messaggio di annuncio?"));
        return false;
    }
}
