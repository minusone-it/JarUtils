package it.jar1.commands;

import it.jar1.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import it.jar1.database.*;

public class Mute implements CommandExecutor
{
    private static final JarUtils plugin;
    private final Map<UUID, Long> muteExpirations;
    
    public Mute() {
        this.muteExpirations = new HashMap<UUID, Long>();
        Bukkit.getScheduler().runTaskTimer((Plugin)Mute.plugin, this::checkMutes, 0L, 20L);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("jarutils.mute")) {
            if (args.length >= 3) {
                final Player targetPlayer = Bukkit.getServer().getPlayerExact(args[0]);
                if (targetPlayer != null) {
                    try {
                        final long duration = this.parseDuration(args[2]);
                        final long expiration = System.currentTimeMillis() + duration * 1000L;
                        final UUID targetUUID = targetPlayer.getUniqueId();
                        this.mutePlayer(targetUUID, args[1], duration);
                        this.muteExpirations.put(targetUUID, expiration);
                        sender.sendMessage(Mute.plugin.prefix + args[0] + " has been successfully muted.");
                        return true;
                    }
                    catch (NumberFormatException e) {
                        sender.sendMessage(Mute.plugin.prefix + "§cUsage: /mute <player> <reason> <duration>");
                        return false;
                    }
                }
                sender.sendMessage(Mute.plugin.prefix + args[0] + "§c is not online.");
            }
            else {
                sender.sendMessage(Mute.plugin.prefix + "§cUsage: /mute <player> <reason> <duration>");
            }
        }
        return false;
    }
    
    private long parseDuration(final String durationStr) {
        final char unit = durationStr.charAt(durationStr.length() - 1);
        final long durationValue = Long.parseLong(durationStr.substring(0, durationStr.length() - 1));
        switch (unit) {
            case 'd': {
                return durationValue * 24L * 60L * 60L;
            }
            case 'h': {
                return durationValue * 60L * 60L;
            }
            case 'm': {
                return durationValue * 60L;
            }
            case 's': {
                return durationValue;
            }
            default: {
                throw new IllegalArgumentException("Unit not supported: " + unit);
            }
        }
    }
    
    private void mutePlayer(final UUID playerUUID, final String reason, final long duration) {
        Database.sendValue(playerUUID.toString() + "_muted", true);
        Database.sendValue(playerUUID.toString() + "_reason", reason);
        Database.sendValue(playerUUID.toString() + "_duration", duration);
    }
    
    public static void unmutePlayer(final UUID playerUUID) {
        Database.sendValue(playerUUID.toString() + "_muted", false);
        Database.sendValue(playerUUID.toString() + "_reason", null);
        Database.sendValue(playerUUID.toString() + "_duration", null);
        Mute.plugin.muted_players.remove(Bukkit.getPlayer(playerUUID));
    }
    
    private void checkMutes() {
        final long currentTime = System.currentTimeMillis();
        final UUID playerUUID;
        final long expirationTime;
        final long n;
        this.muteExpirations.entrySet().removeIf(entry -> {
            playerUUID = entry.getKey();
            expirationTime = (long)entry.getValue();
            if (expirationTime <= n) {
                unmutePlayer(playerUUID);
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    static {
        plugin = JarUtils.getInstance();
    }
}
