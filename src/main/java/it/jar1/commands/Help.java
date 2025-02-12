package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;

public class Help implements CommandExecutor
{
    private final JarUtils plugin;
    
    public Help(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        boolean newVersionAvailable = false;
        final String version = "1.0";
        if (!version.equals("1.0")) {
            newVersionAvailable = true;
        }
        if (args.length == 0) {
            commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "This Server is running §3§lJarUtils V1.1§r§7, by 1Jar." + (commandSender.hasPermission("jarutils.help") ? (newVersionAvailable ? (" (Version " + version + " Available!)") : "") : "")) : (this.plugin.prefix + "Questo server esegue il plugin §3§lJarUtils V1.1§r§7, fatto da 1Jar." + (commandSender.hasPermission("jarutils.help") ? (newVersionAvailable ? (" (La versione " + version + " \u00e8 ora disponibile!)") : "") : "")));
        }
        else if (commandSender.hasPermission("jarutils.help") && commandSender instanceof Player && args.length >= 1 && args[0].equalsIgnoreCase("help")) {
            commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Commands Available:") : (this.plugin.prefix + "Comandi Disponibili:"));
            commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "/jarutils help or /jarutils - Shows the info(s) of this plugin.") : (this.plugin.prefix + "/jarutils help o /jarutils - Permette di vedere i comandi di base."));
            commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "/vanish or /v - Enable or disable the vanish.") : (this.plugin.prefix + "/vanish o /v - Abilita o disabilita la vanish."));
            commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "/gm<gamemode> (<player>) - Sets gamemode of a player in the typed gamemode.") : (this.plugin.prefix + "/gm<gamemode> (<player>) - Setta la gamemode di un player in quella digitata."));
        }
        else if (commandSender.hasPermission("jarutils.reloadconfig") && args.length >= 1 && (args[0].equalsIgnoreCase("reload") | args[0].equalsIgnoreCase("rl"))) {
            try {
                this.plugin.loadConfig((Plugin)this.plugin);
                commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Config reloaded succesfully!") : (this.plugin.prefix + "Config reloadata correttamente!"));
            }
            catch (Exception e) {
                commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Config didn't reload correctly; did you typed correct syntax? Check console for eventual errors!") : (this.plugin.prefix + "Config non reloadata correttamente. Hai digitato correttamente la sintassi? Guarda anche la console per eventuali errori!"));
            }
        }
        else if (args.length >= 1) {
            commandSender.sendMessage(JarUtils.lang.contains("en") ? (this.plugin.prefix + "Arg not recognized.") : (this.plugin.prefix + "Arg non riconosciuto."));
        }
        return true;
    }
}
