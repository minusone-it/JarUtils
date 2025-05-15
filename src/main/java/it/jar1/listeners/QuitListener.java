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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(final PlayerQuitEvent event) {
        if (JarUtils.joinQuitMessages)
            event.setQuitMessage(null);
    }
}
