package it.jar1.listeners;

import it.jar1.*;
import org.bukkit.event.player.*;
import java.util.*;
import org.bukkit.event.*;

public class ChatMessageListener implements Listener
{
    JarUtils plugin;
    
    public ChatMessageListener(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onChatMessage(final AsyncPlayerChatEvent event) {
        for (final String blocked_word : this.plugin.blocked_words) {
            if (event.getMessage().contains(blocked_word)) {
                event.setCancelled(true);
            }
        }
        if (this.plugin.muted_players.contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(this.plugin.prefix + "You have been muted for " + this.plugin.muted_players_duration.get(event.getPlayer()) + ". Reason: " + this.plugin.muted_players_reasons.get(event.getPlayer()));
        }
    }
}
