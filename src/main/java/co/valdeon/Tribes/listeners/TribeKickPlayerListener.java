package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.events.TribeKickPlayerEvent;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TribeKickPlayerListener implements Listener {

    @EventHandler
    public void onEvent(TribeKickPlayerEvent e) {
        if(e.isCancelled())
            return;

        if(e.getKickee().isOnline()) {
            Message.message(e.getKickee().getPlayer(), Message.format(Config.kicked, Config.colorOne, Config.colorTwo, e.getTribe().getName(), e.getKicker().getName()));
        }

        if(e.getKicker().isOnline()) {
            Message.message(e.getKicker().getPlayer(), Message.format(Config.kickedKickee, Config.colorOne, Config.colorTwo, e.getTribe().getName(), e.getKickee().getName()));
        }
    }

}
