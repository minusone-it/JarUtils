package it.jar1.listeners;

import it.jar1.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class JoinListener implements Listener
{
    private final JarUtils plugin;
    
    public JoinListener(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final JarUtils plugin = this.plugin;
        if (JarUtils.joinQuitMessages) {
            event.setJoinMessage("");
        }
    }
}
