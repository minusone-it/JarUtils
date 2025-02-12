package it.jar1.listeners;

import it.jar1.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class QuitListener implements Listener
{
    private final JarUtils plugin;
    
    public QuitListener(final JarUtils plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final JarUtils plugin = this.plugin;
        if (JarUtils.joinQuitMessages) {
            event.setQuitMessage("");
        }
    }
}
