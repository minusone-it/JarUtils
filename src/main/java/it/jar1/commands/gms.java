package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import it.jar1.commands.utils.*;

public class gms implements CommandExecutor
{
    private final JarUtils plugin;
    
    public gms(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (commandSender instanceof Player && commandSender.hasPermission("jarutils.gms") && args.length < 1) {
            ChangeGamemode.changeGM(GameMode.SURVIVAL, commandSender);
            return true;
        }
        if (args.length >= 1) {
            final String player = args[0];
            ChangeGamemode.changeGM(GameMode.SURVIVAL, commandSender, player);
            return true;
        }
        return false;
    }
}
