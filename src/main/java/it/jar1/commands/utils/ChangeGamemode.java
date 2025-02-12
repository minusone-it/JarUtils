package it.jar1.commands.utils;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class ChangeGamemode
{
    private static JarUtils plugin;
    private static boolean lang;
    
    public static void changeGM(final GameMode gm, final CommandSender cmdSender) {
        final Player p = (Player)cmdSender;
        if (!p.getGameMode().equals((Object)gm)) {
            p.setGameMode(gm);
            p.sendMessage(ChangeGamemode.lang ? (ChangeGamemode.plugin.prefix + "You are now in " + gm.toString().toLowerCase()) : (ChangeGamemode.plugin.prefix + "Ora sei in " + gm.toString().toLowerCase()));
        }
        else {
            p.sendMessage(ChangeGamemode.lang ? (ChangeGamemode.plugin.prefix + "You are already in " + gm.toString().toLowerCase() + "!") : (ChangeGamemode.plugin.prefix + "Sei gi\u00e0 in " + gm.toString().toLowerCase() + "!"));
        }
    }
    
    public static void changeGM(final GameMode gm, final CommandSender cmdSender, final String pl) {
        final Player p = (Player)cmdSender;
        final Player targetPlayer = Bukkit.getPlayer(pl);
        if (targetPlayer == null) {
            p.sendMessage(ChangeGamemode.lang ? (ChangeGamemode.plugin.prefix + "The player " + pl + " does not exist!") : (ChangeGamemode.plugin.prefix + "Il player " + pl + " \u00e8 inesistente!"));
            return;
        }
        if (!targetPlayer.getGameMode().equals((Object)gm)) {
            targetPlayer.setGameMode(gm);
            targetPlayer.sendMessage(ChangeGamemode.lang ? (ChangeGamemode.plugin.prefix + "You are now in " + gm.toString().toLowerCase() + " by " + p.getDisplayName()) : (ChangeGamemode.plugin.prefix + "Sei ora in " + gm.toString().toLowerCase() + " grazie a " + p.getDisplayName()));
            p.sendMessage(ChangeGamemode.lang ? (ChangeGamemode.plugin.prefix + "Now " + pl + " is in " + gm.toString().toLowerCase()) : (ChangeGamemode.plugin.prefix + "Ora " + pl + " \u00e8 in " + gm.toString().toLowerCase()));
        }
        else {
            p.sendMessage(ChangeGamemode.lang ? (ChangeGamemode.plugin.prefix + "The player " + pl + " already is this gamemode!") : (ChangeGamemode.plugin.prefix + "Il player " + pl + " \u00e8 gi\u00e0 in questa gamemode!"));
        }
    }
    
    static {
        ChangeGamemode.plugin = JarUtils.getInstance();
        ChangeGamemode.lang = (JarUtils.lang.equalsIgnoreCase("en") || JarUtils.lang.equalsIgnoreCase("eng"));
    }
}
