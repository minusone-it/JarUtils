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
        if ()
    }
}
