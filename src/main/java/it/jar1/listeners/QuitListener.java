package it.jar1.listeners;

import it.jar1.JarUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    private final JarUtils plugin;

    public QuitListener(JarUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.joinQuitMessages)
            event.setQuitMessage("");
    }
}
