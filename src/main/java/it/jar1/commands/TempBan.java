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

public class TempBan implements CommandExecutor {
    private final JarUtils plugin;

    public TempBan(JarUtils plugin) {
        this.plugin = plugin;
    }

    private boolean language = lang.contains("en");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("jarutils.ban")) {
            if (args.length < 3) {
                sender.sendMessage(prefix + (language ? "§cCan't ban the player. Please provide player, duration, and reason." : "§cImpossibile bannare il player. Per favore, fornisci il nome del giocatore, la durata e la motivazione."));
                return true;
            }

            String playerName = args[0];
            Player targetPlayer = sender.getServer().getPlayer(playerName);

            if (targetPlayer == null) {
                sender.sendMessage(prefix + (language ? "§cThe specified player isn't online." : "§cIl player specificato non è online."));
                return true;
            }

            String durationStr = args[1];
            String reason = String.join(" ", args).substring(playerName.length() + durationStr.length() + 2);

            long duration = parseDuration(durationStr);
            long expiration = System.currentTimeMillis() + (duration * 1000);
            Bukkit.getBanList(BanList.Type.NAME).addBan(playerName, reason, new java.util.Date(expiration), sender.getName());

            targetPlayer.kickPlayer(prefix + (language ? "You have been temporarily banned. The ban will expire in " + durationStr + ". Reason: " + reason : "Sei stato temporaneamente bannato. Il ban scadrà in " + durationStr + ". Motivo: " + reason));

            sender.sendMessage(prefix + (language ? "The player " + playerName + " has been temporarily banned for " + durationStr + "." : "Il giocatore " + playerName + " è stato temporaneamente bannato per " + durationStr + "."));
            return true;
        } else
            return false;
    }

    private long parseDuration(String durationStr) {
        char unit = durationStr.charAt(durationStr.length() - 1);
        long durationValue = Long.parseLong(durationStr.substring(0, durationStr.length() - 1));

        switch (unit) {
            case 'd':
                return durationValue * 24 * 60 * 60;
            case 's':
                return durationValue;
            case 'm':
                return durationValue * 60;
            default:
                throw new IllegalArgumentException((language ? "Unit not supported: " : "Unità non supportata: ") + unit);
        }
    }
}
