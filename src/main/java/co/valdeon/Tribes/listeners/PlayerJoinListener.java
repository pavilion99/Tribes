package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.storage.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent e) {
        Database.loadPlayer(e.getPlayer());
    }

}
