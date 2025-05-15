package it.jar1.commands;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

@SuppressWarnings("ALL")
public class UnMute implements CommandExecutor, PluginMessageListener {
    private final JarUtils plugin;
    private boolean language;

    public UnMute(final JarUtils plugin) {
        this.language = JarUtils.lang.contains("en");
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.hasPermission("jarutils.unmute")) {
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(this.plugin.prefix + (this.language ? "§cCan't unmute the player. Usage: /unmute <player>." : "§cImpossibile sbloccare il player. Per favore, fornisci il nome del giocatore."));
            return true;
        }

        String playerName = args[0];
        Player targetPlayer = sender.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            // If the player is offline, mute will not be applied
            sender.sendMessage(this.plugin.prefix + (this.language ? "Player not found." : "Giocatore non trovato."));
            return true;
        }

        // Unmute locally
        JarUtils.getInstance().muted_players.remove(targetPlayer);
        JarUtils.getInstance().muted_players_duration.remove(targetPlayer);
        JarUtils.getInstance().muted_players_reasons.remove(targetPlayer);

        // Send unmute message to BungeeCord
        sendUnMuteMessage((Player) sender, playerName);

        sender.sendMessage(this.plugin.prefix + (this.language ? ("The player " + playerName + " has been unmuted.") : ("Il giocatore " + playerName + " e' stato unmutato.")));
        targetPlayer.sendMessage(this.plugin.prefix + (this.language ? "You have been unmuted." : "Sei stato unmutato."));

        return true;
    }

    private void sendUnMuteMessage(Player sender, String playerName) {
        try {
            ByteArrayOutputStream msgBytes = new ByteArrayOutputStream();
            DataOutputStream msgOut = new DataOutputStream(msgBytes);
            msgOut.writeUTF(playerName);
            msgOut.writeUTF(sender.getName());

            ByteArrayOutputStream forwardBytes = new ByteArrayOutputStream();
            DataOutputStream forwardOut = new DataOutputStream(forwardBytes);

            forwardOut.writeUTF("Forward");
            forwardOut.writeUTF("ALL");
            forwardOut.writeUTF("jarutils:unmute");

            forwardOut.writeShort(msgBytes.toByteArray().length);
            forwardOut.write(msgBytes.toByteArray());

            sender.sendPluginMessage(plugin, "BungeeCord", forwardBytes.toByteArray());

            plugin.getLogger().info("[DEBUG] Sent unmute plugin message to BungeeCord for player: " + playerName);
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
            if (subChannel.equals("jarutils:unmute")) {
                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
                String playerName = msgin.readUTF();
                String staffer = msgin.readUTF();

                plugin.getLogger().info("[DEBUG] Received Unmute via Bungee: " + playerName);

                Player target = Bukkit.getPlayerExact(playerName);
                if (target != null) {
                    // Remove mute locally
                    JarUtils.getInstance().muted_players.remove(target);
                    JarUtils.getInstance().muted_players_duration.remove(target);
                    JarUtils.getInstance().muted_players_reasons.remove(target);

                    // Notify the player
                    target.sendMessage(this.plugin.prefix + (this.language ? "You have been unmuted." : "Sei stato unmutato."));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
