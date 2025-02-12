package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class UnBan implements CommandExecutor
{
    private final JarUtils plugin;
    private boolean language;
    
    public UnBan(final JarUtils plugin) {
        this.language = JarUtils.lang.contains("en");
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("jarutils.unban") && args.length > 0) {
            Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);
            sender.sendMessage(this.plugin.prefix + (this.language ? ("Player " + args[0] + " has been unbanned.") : ("Il player " + args[0] + " e' stato unbannato.")));
            return true;
        }
        return false;
    }
}
