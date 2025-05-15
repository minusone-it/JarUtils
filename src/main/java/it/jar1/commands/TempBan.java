package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.Date;

@SuppressWarnings("ALL")
public class TempBan implements CommandExecutor, PluginMessageListener
{
    private final JarUtils plugin;
    private boolean language;
    
    public TempBan(final JarUtils plugin) {
        this.language = JarUtils.lang.contains("en");
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
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
                sendTempBanMessage((Player) sender, playerName, durationStr, reason);
            } catch (Exception e) {
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
            sendTempBanMessage((Player) sender, playerName, durationStr, reason);
        } catch (Exception e2) {
            if (e2 instanceof NumberFormatException) {
                sender.sendMessage(this.plugin.prefix + "§cCould not ban the player. Usage: /tempban <player> <duration> <reason>.");
            }
        }

        return true;
    }

    public void sendTempBanMessage(Player sender, String playerName, String durationStr, String reason) {
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
            forwardOut.writeUTF("jarutils:tempban");

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
                throw new IllegalArgumentException((this.language ? "Unit not supported: " : "Unit\u00e0 non supportata: ") + unit);
            }
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
            if (subChannel.equals("jarutils:tempban")) {
                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
                String playerName = msgin.readUTF();
                String durationStr = msgin.readUTF();
                String reason = msgin.readUTF();
                String staffer = msgin.readUTF();

                plugin.getLogger().info("[DEBUG] Received TempBan via Bungee: " + playerName);

                long duration = parseDuration(durationStr);
                long expiration = System.currentTimeMillis() + duration * 1000L;
                Bukkit.getBanList(BanList.Type.NAME).addBan(playerName, reason, new Date(expiration), staffer);

                Player target = Bukkit.getPlayerExact(playerName);
                if (target != null) {
                    target.kickPlayer(this.plugin.prefix + (this.language ? ("You have been temporarily banned. The ban will expire in " + durationStr + ". Reason: " + reason) : ("Sei stato temporaneamente bannato. Il ban scadr\u00e0 in " + durationStr + ". Motivo: " + reason)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
