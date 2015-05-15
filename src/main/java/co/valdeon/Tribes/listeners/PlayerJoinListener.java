package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.Tribes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent e) {
        Tribes.loadPlayer(e.getPlayer());
    }

}
