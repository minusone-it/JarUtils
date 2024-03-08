package it.jar1.commands;

import it.jar1.JarUtils;
import it.jar1.commands.utils.ChangeGamemode;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gmc implements CommandExecutor {
    private final JarUtils plugin;

    public gmc(JarUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player && commandSender.hasPermission("jarutils.gmc") && !(args.length >= 1)) {
            ChangeGamemode.changeGM(GameMode.CREATIVE, commandSender);
            return true;
        } else if (args.length >= 1) {
            String player = args[0];
            ChangeGamemode.changeGM(GameMode.CREATIVE, commandSender, player);
            return true;
        }
        return false;
    }
}
