package tech.spencercolton.tribes.Listeners;

import tech.spencercolton.tribes.Storage.SQLite.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(PlayerJoinEvent e) {
        Database.loadPlayer(e.getPlayer());
    }

}
