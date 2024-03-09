package it.jar1.listeners;

import it.jar1.JarUtils;
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
        for (String blocked_word : plugin.blocked_words) {
            if (event.getMessage().contains(blocked_word)) {
                event.setCancelled(true);
            }
        }
        if (plugin.muted_players.contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.prefix + "You have been muted for " + plugin.muted_players_reasons.get(event.getPlayer().getName()));
        }
        /*for (Player p : Bukkit.getOnlinePlayers()) {
            if (event.getMessage().contains("" + p.getName())) {
                event.setMessage(event.getMessage());
            }
        }*/
    }
}
