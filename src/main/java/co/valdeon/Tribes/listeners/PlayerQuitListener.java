package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.Tribes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onEvent(PlayerQuitEvent e) {
        Tribes.Players.remove(e.getPlayer());
    }

}
