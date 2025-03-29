package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static it.jar1.JarUtils.lang;

public class Help implements CommandExecutor {
    private final JarUtils plugin;

    public Help(JarUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean newVersionAvailable = false;
        String version = "";
        //try {
            version = "1.0";//getWebContent(url);
            if (!version.equals("1.0")) {
                newVersionAvailable = true;
            }
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}
        if (args.length == 0)
            commandSender.sendMessage(lang.contains("en") ? (plugin.prefix + "This Server is running §3§lJarUtils V1.1§r§7, by _MinusOne_." + (commandSender.hasPermission("jarutils.help") ? (newVersionAvailable ? " (Version "+version+" Available!)" : "") : "")) : (plugin.prefix + "Questo server esegue il plugin §3§lJarUtils V1.1§r§7, fatto da _MinusOne_." + (commandSender.hasPermission("jarutils.help") ? (newVersionAvailable ? " (La versione "+version+" è ora disponibile!)" : "") : "")));
        else if (commandSender.hasPermission("jarutils.help") && commandSender instanceof Player && args.length >= 1 && args[0].equalsIgnoreCase("help")) {
            commandSender.sendMessage(lang.contains("en") ? plugin.prefix + "Commands Available:" : plugin.prefix + "Comandi Disponibili:");
            commandSender.sendMessage(lang.contains("en") ? plugin.prefix + "/jarutils help or /jarutils - Shows the info(s) of this plugin." : plugin.prefix + "/jarutils help o /jarutils - Permette di vedere i comandi di base.");
            commandSender.sendMessage(lang.contains("en") ? plugin.prefix + "/vanish or /v - Enable or disable the vanish." : plugin.prefix + "/vanish o /v - Abilita o disabilita la vanish.");
            commandSender.sendMessage(lang.contains("en") ? plugin.prefix + "/gm<gamemode> (<player>) - Sets gamemode of a player in the typed gamemode." : plugin.prefix + "/gm<gamemode> (<player>) - Setta la gamemode di un player in quella digitata.");
        } else if (commandSender.hasPermission("jarutils.reloadconfig") && args.length >= 1 && args[0].equalsIgnoreCase("reload") | args[0].equalsIgnoreCase("rl")) {
            try {
                plugin.loadConfig(plugin);
                commandSender.sendMessage(lang.contains("en") ? plugin.prefix + "Config reloaded succesfully!" : plugin.prefix + "Config reloadata correttamente!");
            } catch (Exception e) {
                commandSender.sendMessage(lang.contains("en") ? plugin.prefix + "Config didn't reload correctly; did you typed correct syntax? Check console for eventual errors!" : plugin.prefix + "Config non reloadata correttamente. Hai digitato correttamente la sintassi? Guarda anche la console per eventuali errori!");
            }
        } else if (args.length >= 1) {
            commandSender.sendMessage(lang.contains("en") ? plugin.prefix + "Arg not recognized." : plugin.prefix + "Arg non riconosciuto.");
        }
        return true;
    }

}
