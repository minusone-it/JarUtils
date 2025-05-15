package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SponsorChannel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("group.youtuber") || player.hasPermission("group.tiketetokete")) {
                if (args.length < 1) {
                    player.sendMessage(JarUtils.getInstance().prefix + "You need to add your channel's link or your live's link.");
                    return true;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    p.sendMessage("%luckperms_prefix% %player% IS STREAMING RN ON " + args[0]);
            }
        }
        return false;
    }
}
