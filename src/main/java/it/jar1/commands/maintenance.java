package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class maintenance implements CommandExecutor
{
    private final JarUtils plugin;
    private boolean language;

    public maintenance(final JarUtils plugin) {
        this.language = JarUtils.lang.contains("en");
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("jarutils.maintenance")) {
            plugin.onMaintenance = !plugin.onMaintenance;
            sender.sendMessage(language ? "Maintenance setted on " + plugin.onMaintenance + "." : "Manutenzione impostata su " + plugin.onMaintenance + ".");
            return true;
        }
        return false;
    }
}
