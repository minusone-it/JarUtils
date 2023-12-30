package it.jar1.commands;

import it.jar1.commands.utils.ChangeGamemode;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gma implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player && commandSender.hasPermission("jarutils.gma") && !(args.length >= 1)) {
            ChangeGamemode.changeGM(GameMode.ADVENTURE, commandSender);
            return true;
        } else if (args.length >= 1) {
            String player = args[0];
            ChangeGamemode.changeGM(GameMode.ADVENTURE, commandSender, player);
            return true;
        }
        return false;
    }
}
