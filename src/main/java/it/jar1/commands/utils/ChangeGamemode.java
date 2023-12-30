package it.jar1.commands.utils;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeGamemode {
    public static void changeGM(GameMode gm, CommandSender cmdSender) {
        Player p = (Player) cmdSender;
        if(!p.getGameMode().equals(gm))
            p.setGameMode(gm);
    }
}
