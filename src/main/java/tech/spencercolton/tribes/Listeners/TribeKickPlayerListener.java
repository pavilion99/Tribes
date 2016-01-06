package tech.spencercolton.tribes.Listeners;

import tech.spencercolton.tribes.Events.TribeKickPlayerEvent;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TribeKickPlayerListener implements Listener {

    @SuppressWarnings("unused")
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
