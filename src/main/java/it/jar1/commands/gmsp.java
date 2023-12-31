package it.jar1.commands;

import it.jar1.JarUtils;
import it.jar1.commands.utils.ChangeGamemode;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gmsp implements CommandExecutor {
    private final JarUtils plugin;

    public gmsp(JarUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player && commandSender.hasPermission("jarutils.gmsp") && !(args.length >= 1)) {
            ChangeGamemode.changeGM(GameMode.SPECTATOR, commandSender);
            return true;
        } else if (args.length >= 1) {
            String player = args[0];
            ChangeGamemode.changeGM(GameMode.SPECTATOR, commandSender, player);
            return true;
        }
        return false;
    }
}
