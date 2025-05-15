package it.jar1.commands;

import it.jar1.*;
import org.bukkit.command.*;
import org.bukkit.*;
import java.io.*;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class UnBan implements CommandExecutor, PluginMessageListener {
    private final JarUtils plugin;
    private final boolean language;

    public UnBan(final JarUtils plugin) {
        this.language = JarUtils.lang.contains("en");
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("jarutils.unban") && args.length > 0) {
            String playerName = args[0];

            // Unban locally
            Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);

            sender.sendMessage(this.plugin.prefix + (this.language ? ("Player " + playerName + " has been unbanned.") : ("Il player " + playerName + " e' stato unbannato.")));

            // Send unban message to BungeeCord
            sendUnBanMessage((Player) sender, playerName);

            return true;
        }
        return false;
    }

    private void sendUnBanMessage(Player sender, String playerName) {
        try {
            ByteArrayOutputStream msgBytes = new ByteArrayOutputStream();
            DataOutputStream msgOut = new DataOutputStream(msgBytes);
            msgOut.writeUTF(playerName);
            msgOut.writeUTF(sender.getName());

            ByteArrayOutputStream forwardBytes = new ByteArrayOutputStream();
            DataOutputStream forwardOut = new DataOutputStream(forwardBytes);

            forwardOut.writeUTF("Forward");
            forwardOut.writeUTF("ALL");
            forwardOut.writeUTF("jarutils:unban");

            forwardOut.writeShort(msgBytes.toByteArray().length);
            forwardOut.write(msgBytes.toByteArray());

            sender.sendPluginMessage(plugin, "BungeeCord", forwardBytes.toByteArray());

            plugin.getLogger().info("[DEBUG] Sent unban plugin message to BungeeCord for player: " + playerName);
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
            if (subChannel.equals("jarutils:unban")) {
                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
                String playerName = msgin.readUTF();
                String staffer = msgin.readUTF();

                plugin.getLogger().info("[DEBUG] Received Unban via Bungee: " + playerName);

                Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);

                // Optionally, you can send a message to the unbanned player (if online)
                Player target = Bukkit.getPlayerExact(playerName);
                if (target != null) {
                    target.sendMessage(this.plugin.prefix + (this.language ? "You have been unbanned!" : "Sei stato unbannato!"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
