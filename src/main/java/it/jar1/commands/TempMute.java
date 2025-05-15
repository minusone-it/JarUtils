package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.*;

public class TempMute implements CommandExecutor, PluginMessageListener {
    private final JarUtils plugin;
    private final boolean language;

    // Map to store mute duration expiration time
    private final Map<String, Long> mutedPlayersExpiration = new HashMap<>();

    public TempMute(final JarUtils plugin) {
        this.language = JarUtils.lang.contains("en");
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);

        // Example: Schedule a task to check if any mute has expired
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::checkMutes, 20L, 20L);
    }

    private void checkMutes() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<String, Long> entry : mutedPlayersExpiration.entrySet()) {
            String playerName = entry.getKey();
            long expirationTime = entry.getValue();

            if (currentTime >= expirationTime) {
                // Mute expired, unmute the player
                Player player = Bukkit.getPlayer(playerName);
                if (player != null) {
                    unmutePlayer(player);
                    mutedPlayersExpiration.remove(playerName);
                    player.sendMessage(this.plugin.prefix + (this.language ? "You have been unmuted as your mute duration has expired." : "Sei stato unmutato poiché la durata del mute è scaduta."));
                }
            }
        }
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.hasPermission("jarutils.mute")) {
            return false;
        }

        if (args.length < 3) {
            sender.sendMessage(this.plugin.prefix + (this.language ? "§cCan't mute the player. Usage: /tempmute <player> <duration> <reason>." : "§cImpossibile mutare il player. Per favore, fornisci il nome del giocatore, la durata e la motivazione."));
            return true;
        }

        final String playerName = args[0];
        final String durationStr = args[1];
        final String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        final Player targetPlayer = sender.getServer().getPlayer(playerName);

        try {
            final long duration = this.parseDuration(durationStr);
            final long expiration = System.currentTimeMillis() + duration * 1000L;

            // Mute the player
            mutePlayer(playerName, durationStr, expiration, reason, sender);

            // Save the mute expiration time
            mutedPlayersExpiration.put(playerName, expiration);

            sender.sendMessage(this.plugin.prefix + (this.language ? ("The player " + playerName + " has been temporarily muted for " + durationStr + ".") : ("Il giocatore " + playerName + " è stato temporaneamente mutato per " + durationStr + ".")));
            targetPlayer.sendMessage(this.plugin.prefix + (this.language ? ("You have been temporarily muted. The mute will expire in " + durationStr + ". Reason: " + reason) : ("Sei stato temporaneamente mutato. Il mute scadrà in " + durationStr + ". Motivo: " + reason)));

            sendTempMuteMessage((Player) sender, playerName, durationStr, reason);
        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                // I dont want to add italian even tho I am lol
                sender.sendMessage(this.plugin.prefix + "§cCould not mute the player. Usage: /tempmute <player> <duration> <reason>.");
            }
        }

        return true;
    }

    private void mutePlayer(String playerName, String durationStr, long expiration, String reason, CommandSender sender) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) player = Bukkit.getOfflinePlayer(playerName).getPlayer();
        if (player == null) return;
        JarUtils.getInstance().muted_players.add(player);
        JarUtils.getInstance().muted_players_duration.put(player, durationStr);
        JarUtils.getInstance().muted_players_reasons.put(player, reason);
    }

    public void sendTempMuteMessage(Player sender, String playerName, String durationStr, String reason) {
        try {
            ByteArrayOutputStream msgBytes = new ByteArrayOutputStream();
            DataOutputStream msgOut = new DataOutputStream(msgBytes);
            msgOut.writeUTF(playerName);
            msgOut.writeUTF(durationStr);
            msgOut.writeUTF(reason);
            msgOut.writeUTF(sender.getName());

            ByteArrayOutputStream forwardBytes = new ByteArrayOutputStream();
            DataOutputStream forwardOut = new DataOutputStream(forwardBytes);

            forwardOut.writeUTF("Forward");
            forwardOut.writeUTF("ALL");
            forwardOut.writeUTF("jarutils:tempmute");

            forwardOut.writeShort(msgBytes.toByteArray().length);
            forwardOut.write(msgBytes.toByteArray());

            sender.sendPluginMessage(plugin, "BungeeCord", forwardBytes.toByteArray());

            plugin.getLogger().info("[DEBUG] Sent plugin message to BungeeCord. ID: " + forwardBytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                throw new IllegalArgumentException((this.language ? "Unit not supported: " : "Unità non supportata: ") + unit);
            }
        }
    }

    private void unmutePlayer(Player player) {
        JarUtils.getInstance().muted_players.remove(player);
        JarUtils.getInstance().muted_players_duration.remove(player);
        JarUtils.getInstance().muted_players_reasons.remove(player);
        try {
            ByteArrayOutputStream msgBytes = new ByteArrayOutputStream();
            DataOutputStream msgOut = new DataOutputStream(msgBytes);
            msgOut.writeUTF(player.getName());
            msgOut.writeUTF(player.getName());

            ByteArrayOutputStream forwardBytes = new ByteArrayOutputStream();
            DataOutputStream forwardOut = new DataOutputStream(forwardBytes);

            forwardOut.writeUTF("Forward");
            forwardOut.writeUTF("ALL");
            forwardOut.writeUTF("jarutils:unmute");

            forwardOut.writeShort(msgBytes.toByteArray().length);
            forwardOut.write(msgBytes.toByteArray());

            player.sendPluginMessage(plugin, "BungeeCord", forwardBytes.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subChannel = in.readUTF();
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);
            if (subChannel.equals("jarutils:tempmute")) {
                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
                String playerName = msgin.readUTF();
                String durationStr = msgin.readUTF();
                String reason = msgin.readUTF();
                String staffer = msgin.readUTF();

                plugin.getLogger().info("[DEBUG] Received TempMute via Bungee: " + playerName);

                long duration = parseDuration(durationStr);
                long expiration = System.currentTimeMillis() + duration * 1000L;

                mutePlayer(playerName, durationStr, expiration, reason, Bukkit.getPlayer(staffer));

                // Save mute expiration time for BungeeCord communication
                mutedPlayersExpiration.put(playerName, expiration);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
