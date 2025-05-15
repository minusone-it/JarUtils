package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Report implements CommandExecutor
{
    private final JarUtils plugin;
    
    public Report(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player reporter = (Player)sender;
            if (args.length < 1) {
                reporter.sendMessage(this.plugin.prefix + (JarUtils.lang.contains("en") ? "§cCan't send report to staffers. Have you forgotten to type the player to report?" : "§cHai per caso dimenticato di scrivere il nome del player da reportare?"));
            }
            if (args.length >= 1 && Bukkit.getPlayer(args[0]) != null) {
                final Player cheater = Bukkit.getPlayer(args[0]);
                reporter.sendMessage(this.plugin.prefix + (JarUtils.lang.contains("en") ? "Player reported successfully!" : "Player reportato correttamente!"));
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("jaruitls.report.cansee")) {
                        player.sendMessage(this.plugin.prefix + (JarUtils.lang.contains("en") ? (cheater.getDisplayName() + " has been reported!") : (cheater + " \u00e8 stato reportato!")));
                    }
                }
            }
            else {
                reporter.sendMessage(this.plugin.prefix + (JarUtils.lang.contains("en") ? "§cThe specified player isn't online." : "§cIl player specificato non \u00e8 online."));
            }
            return true;
        }
        return false;
    }
}
