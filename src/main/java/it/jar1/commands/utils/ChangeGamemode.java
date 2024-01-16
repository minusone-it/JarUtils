package it.jar1.commands.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static it.jar1.JarUtils.prefix;

public class ChangeGamemode {
    private static boolean lang = it.jar1.JarUtils.lang.contains("en");
    public static void changeGM(GameMode gm, CommandSender cmdSender) {
        Player p = (Player) cmdSender;
        if(!p.getGameMode().equals(gm)) {
            p.setGameMode(gm);
            p.sendMessage(lang ? prefix + "You are now in " + gm.toString().toLowerCase() : prefix + "Ora sei in " + gm.toString().toLowerCase());
        } else {
            p.sendMessage(lang ? prefix + "You are already in "+ gm.toString().toLowerCase() +"!" : prefix + "Sei già in "+ gm.toString().toLowerCase() +"!");
        }
    }
    public static void changeGM(GameMode gm, CommandSender cmdSender, String pl) {
        Player p = (Player) cmdSender;

        Player targetPlayer = Bukkit.getPlayer(pl);

        if (targetPlayer == null) {
            p.sendMessage(lang ? prefix + "The player " + pl + " does not exist!" : prefix + "Il player " + pl + " è inesistente!");
            return;
        }
        if (!targetPlayer.getGameMode().equals(gm)) {
            targetPlayer.setGameMode(gm);
            targetPlayer.sendMessage(lang ? prefix + "You are now in " + gm.toString().toLowerCase() + " by " + p.getDisplayName() : prefix + "Sei ora in " + gm.toString().toLowerCase() + " grazie a " + p.getDisplayName());
            p.sendMessage(lang ? prefix + "Now " + pl + " is in " + gm.toString().toLowerCase() : prefix + "Ora " + pl + " è in " + gm.toString().toLowerCase());
        } else {
            p.sendMessage(lang ? prefix + "The player " + pl + " already is this gamemode!" : prefix + "Il player " + pl + " è già in questa gamemode!");
        }
    }
}
