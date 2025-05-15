package it.jar1.listeners;

import it.jar1.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatMessageListener implements Listener {
    JarUtils plugin;

    public ChatMessageListener(JarUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {
        if (plugin.muted_players.contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.prefix + "You have been muted for " + JarUtils.getInstance().muted_players_duration.get(event.getPlayer()) + ". Reason: " + plugin.muted_players_reasons.get(event.getPlayer()));
            return;
        }

        String message = event.getMessage();

        for (String a : JarUtils.getInstance().blocked_words) {
            if (message.contains(a)) {
                event.setCancelled(true);
                return;
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            String playerName = p.getName();
            String lowerMessage = message.toLowerCase();
            String lowerName = playerName.toLowerCase();

            if (lowerMessage.contains(lowerName)) {
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 1);

                message = message.replaceAll("(?i)" + playerName, "§e§o" + playerName + "§r");
            }
        }

        event.setMessage(message);
    }
}
