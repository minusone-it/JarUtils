package it.jar1.commands.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static it.jar1.JarUtils.prefix;

public class ChangeGamemode {
    public static void changeGM(GameMode gm, CommandSender cmdSender) {
        Player p = (Player) cmdSender;
        if(!p.getGameMode().equals(gm)) {
            p.setGameMode(gm);
            p.sendMessage(prefix + "Your gamemode has been changed to " + gm.toString().toLowerCase());
        } else {
            p.sendMessage(prefix + "You are already in this gamemode!");
        }
    }
    public static void changeGM(GameMode gm, CommandSender cmdSender, String pl) {
        Player p = (Player) cmdSender;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getGameMode().equals(gm) && player.getDisplayName().equalsIgnoreCase(pl)) {
                player.setGameMode(gm);
                player.sendMessage(prefix + "Your gamemode has been changed to " + gm.toString().toLowerCase() + " by " + p.getDisplayName());
                player.sendMessage(prefix + "The gamemode of "+pl+" has been changed to " + gm.toString().toLowerCase());
            } else if (!player.getDisplayName().equalsIgnoreCase(pl)) {
                p.sendMessage(prefix + "The player "+pl+" does not exist!");
            } else if (player.getGameMode().equals(gm)) {
                p.sendMessage(prefix + "The player "+pl+" has already this gamemode!");
            }
        }
    }
}
