package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

import static it.jar1.JarUtils.*;

public class Help implements CommandExecutor {
    private final JarUtils plugin;

    public Help(JarUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0)
            commandSender.sendMessage(prefix + "This Server is running §3§lJarUtils V1.0§r§7, by 1Jar." + (commandSender.hasPermission("jarutils.help") ? (newVersionAvailable ? " (Version "+versionAvailable+" Available!)" : "") : ""));
        else if(commandSender.hasPermission("jarutils.help") && commandSender instanceof Player && args.length >= 1 && args[0].equalsIgnoreCase("help")) {
            commandSender.sendMessage(prefix + "Commands Available:");
            commandSender.sendMessage(prefix + "/jarutilshelp or /jarutils - Shows the info(s) of this plugin.");
            commandSender.sendMessage(prefix + "/vanish or /v - Enable or disable the vanish.");
            commandSender.sendMessage(prefix + "/gm<gamemode> <player> - Sets gamemode of a player / yourself in the typed gamemode.");
        } else if (commandSender.hasPermission("jarutils.reloadconfig") && args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            try {
                plugin.loadConfig(plugin);
                commandSender.sendMessage(prefix + "Config reloaded succesfully!");
            } catch (Exception e) {
                commandSender.sendMessage(prefix + "Config didn't reload correctly; did you typed correct syntax? Check console too!");
            }
        } else if (args.length >= 1) {
            commandSender.sendMessage(prefix + "Subcommand not recognized.");
        }
        return true;
    }

}
