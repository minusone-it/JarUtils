package it.jar1.commands.utils;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeGamemode {
    private static final JarUtils plugin = JarUtils.getInstance();
    private static boolean lang = it.jar1.JarUtils.lang.equalsIgnoreCase("en") || it.jar1.JarUtils.lang.equalsIgnoreCase("eng");
    public static void changeGM(GameMode gm, CommandSender cmdSender) {
        Player p = (Player) cmdSender;
        if (!p.getGameMode().equals(gm)) {
            p.setGameMode(gm);
            p.sendMessage(lang ? plugin.prefix + "You are now in " + gm.toString().toLowerCase() : plugin.prefix + "Ora sei in " + gm.toString().toLowerCase());
        } else {
            p.sendMessage(lang ? plugin.prefix + "You are already in "+ gm.toString().toLowerCase() +"!" : plugin.prefix + "Sei già in "+ gm.toString().toLowerCase() +"!");
        }
    }
    public static void changeGM(GameMode gm, CommandSender cmdSender, String pl) {
        Player p = (Player) cmdSender;

        Player targetPlayer = Bukkit.getPlayer(pl);

        if (targetPlayer == null) {
            p.sendMessage(lang ? plugin.prefix + "The player " + pl + " does not exist!" : plugin.prefix + "Il player " + pl + " è inesistente!");
            return;
        }
        if (!targetPlayer.getGameMode().equals(gm)) {
            targetPlayer.setGameMode(gm);
            targetPlayer.sendMessage(lang ? plugin.prefix + "You are now in " + gm.toString().toLowerCase() + " by " + p.getDisplayName() : plugin.prefix + "Sei ora in " + gm.toString().toLowerCase() + " grazie a " + p.getDisplayName());
            p.sendMessage(lang ? plugin.prefix + "Now " + pl + " is in " + gm.toString().toLowerCase() : plugin.prefix + "Ora " + pl + " è in " + gm.toString().toLowerCase());
        } else {
            p.sendMessage(lang ? plugin.prefix + "The player " + pl + " already is this gamemode!" : plugin.prefix + "Il player " + pl + " è già in questa gamemode!");
        }
    }
}
