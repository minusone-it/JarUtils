package it.jar1.commands;

import it.jar1.JarUtils;
import it.jar1.commands.utils.ChangeGamemode;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gma implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        JarUtils p = new JarUtils();
        String prefix = p.prefix;
        if(commandSender instanceof Player && commandSender.hasPermission("jarutils.gmc")) {
            ChangeGamemode.changeGM(GameMode.ADVENTURE, commandSender);
            return true;
        }
        return false;
    }
}
