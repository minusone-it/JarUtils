package it.jar1.commands.utils;

import it.jar1.JarUtils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeGamemode {
    public static void changeGM(GameMode gm, CommandSender cmdSender) {
        Player p = (Player) cmdSender;
        JarUtils asdf = new JarUtils();
        String prefix = asdf.prefix;
        if(!p.getGameMode().equals(gm)) {
            p.setGameMode(gm);
            p.sendMessage(prefix + "Your gamemode has been changed to " + gm.toString().toLowerCase());
        }
    }
}
