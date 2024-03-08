package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static it.jar1.JarUtils.lang;
import static it.jar1.JarUtils.prefix;

public class UnBan implements CommandExecutor {
    private final JarUtils plugin;

    public UnBan(JarUtils plugin) {
        this.plugin = plugin;
    }

    private boolean language = lang.contains("en");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("jarutils.unban") && args.length > 0) {
            Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);
            sender.sendMessage(prefix + (language ? "Player " + args[0] + " has been unbanned." : "Il player " + args[0] + " e' stato unbannato."));
            return true;
        } else
            return false;
    }
}
