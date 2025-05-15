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
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(final PlayerJoinEvent event) {
        if (JarUtils.joinQuitMessages)
            event.setJoinMessage(null);
        if (plugin.onMaintenance && !event.getPlayer().hasPermission("jarutils.maintenance"))
            event.getPlayer().kickPlayer("§cMAINTENANCE ON. §7See more info on https://dsc.gg/power-pixel/");

    }
}
