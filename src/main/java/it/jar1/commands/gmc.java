package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import it.jar1.commands.utils.*;

public class gmc implements CommandExecutor
{
    private final JarUtils plugin;
    
    public gmc(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (commandSender instanceof Player && commandSender.hasPermission("jarutils.gmc") && args.length < 1) {
            ChangeGamemode.changeGM(GameMode.CREATIVE, commandSender);
            return true;
        }
        if (args.length >= 1) {
            final String player = args[0];
            ChangeGamemode.changeGM(GameMode.CREATIVE, commandSender, player);
            return true;
        }
        return false;
    }
}
