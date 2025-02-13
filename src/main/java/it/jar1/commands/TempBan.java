package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.entity.*;

@SuppressWarnings("ALL")
public class TempBan implements CommandExecutor
{
    private final JarUtils plugin;
    private boolean language;
    
    public TempBan(final JarUtils plugin) {
        this.language = JarUtils.lang.contains("en");
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.hasPermission("jarutils.ban")) {
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage(this.plugin.prefix + (this.language ? "§cCan't ban the player. Usage: /tempban <player> <duration> <reason>." : "§cImpossibile bannare il player. Per favore, fornisci il nome del giocatore, la durata e la motivazione."));
            return true;
        }
        final String playerName = args[0];
        final Player targetPlayer = sender.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            final String durationStr = args[1];
            final String reason = String.join(" ", (CharSequence[])args).substring(playerName.length() + durationStr.length() + 2);
            try {
                final long duration = this.parseDuration(durationStr);
                final long expiration = System.currentTimeMillis() + duration * 1000L;
                Bukkit.getBanList(BanList.Type.NAME).addBan(playerName, reason, new Date(expiration), sender.getName());
                sender.sendMessage(this.plugin.prefix + (this.language ? ("The player " + playerName + " has been temporarily banned for " + durationStr + ".") : ("Il giocatore " + playerName + " \u00e8 stato temporaneamente bannato per " + durationStr + ".")));
            }
            catch (Exception e) {
                if (e instanceof NumberFormatException) {
                    sender.sendMessage(this.plugin.prefix + "Could not ban the player. Usage: /tempban <player> <duration> <reason>.");
                }
            }
            return true;
        }
        final String durationStr = args[1];
        try {
            final String reason = String.join(" ", (CharSequence[])args).substring(playerName.length() + durationStr.length() + 2);
            final long duration = this.parseDuration(durationStr);
            final long expiration = System.currentTimeMillis() + duration * 1000L;
            Bukkit.getBanList(BanList.Type.NAME).addBan(playerName, reason, new Date(expiration), sender.getName());
            targetPlayer.kickPlayer(this.plugin.prefix + (this.language ? ("You have been temporarily banned. The ban will expire in " + durationStr + ". Reason: " + reason) : ("Sei stato temporaneamente bannato. Il ban scadr\u00e0 in " + durationStr + ". Motivo: " + reason)));
            sender.sendMessage(this.plugin.prefix + (this.language ? ("The player " + playerName + " has been temporarily banned for " + durationStr + ".") : ("Il giocatore " + playerName + " \u00e8 stato temporaneamente bannato per " + durationStr + ".")));
        }
        catch (Exception e2) {
            if (e2 instanceof NumberFormatException) {
                sender.sendMessage(this.plugin.prefix + "§cCould not ban the player. Usage: /tempban <player> <duration> <reason>.");
            }
        }
        return true;
    }
    
    private long parseDuration(final String durationStr) {
        final char unit = durationStr.charAt(durationStr.length() - 1);
        final long durationValue = Long.parseLong(durationStr.substring(0, durationStr.length() - 1));
        switch (unit) {
            case 'd': {
                return durationValue * 24L * 60L * 60L;
            }
            case 's': {
                return durationValue;
            }
            case 'm': {
                return durationValue * 60L;
            }
            default: {
                throw new IllegalArgumentException((this.language ? "Unit not supported: " : "Unit\u00e0 non supportata: ") + unit);
            }
        }
    }
}
