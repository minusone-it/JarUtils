package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static it.jar1.JarUtils.lang;
import static it.jar1.JarUtils.prefix;

public class Report implements CommandExecutor {
    private final JarUtils plugin;
    public Report(JarUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player reporter = (Player) sender;
            if (!(args.length >= 1)) {
                reporter.sendMessage(prefix + (lang.contains("en") ?  "§cCan't send report to staffers. Have you forgotten to type the player to report?" : "§cHai per caso dimenticato di scrivere il nome del player da reportare?"));
            }
            if (args.length >= 1 && Bukkit.getPlayer(args[0]) != null) {
                Player cheater = Bukkit.getPlayer(args[0]);
                reporter.sendMessage(prefix + (lang.contains("en") ?  "Player reported successfully!" : "Player reportato correttamente!"));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("jaruitls.report.cansee")) {
                        player.sendMessage(prefix + (lang.contains("en") ? cheater.getDisplayName() + " has been reported!" : cheater + " è stato reportato!"));
                    }
                }
            } else {
                reporter.sendMessage(prefix + (lang.contains("en") ? "§cThe specified player isn't online." : "§cIl player specificato non è online."));
            }
            return true;
        }
        return false;
    }

}
