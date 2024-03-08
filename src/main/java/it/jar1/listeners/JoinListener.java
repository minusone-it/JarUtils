package it.jar1.listeners;

import it.jar1.JarUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final JarUtils plugin;

    public JoinListener(JarUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.joinQuitMessages)
            event.setJoinMessage("");
    }
}
