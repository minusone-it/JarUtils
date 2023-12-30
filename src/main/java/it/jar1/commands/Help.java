package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static it.jar1.JarUtils.*;

public class Help implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        JarUtils p = new JarUtils();
        String prefix = p.prefix;
        commandSender.sendMessage(prefix + "This Server is running §3§lJarUtils V1.0§r§7, by 1Jar." + (commandSender.hasPermission("jarutils.help") ? (newVersionAvailable ? " (Version "+versionAvailable+" Available!)" : "") : ""));
        if(commandSender.hasPermission("jarutils.help") && commandSender instanceof Player) {
            commandSender.sendMessage(prefix + "Commands Available:");
            commandSender.sendMessage(prefix + "/jarutilshelp or /jarutils - Shows the info(s) of this plugin.");
            commandSender.sendMessage(prefix + "/vanish or /v - Enable or disable the vanish.");
        }
        return true;
    }
}
