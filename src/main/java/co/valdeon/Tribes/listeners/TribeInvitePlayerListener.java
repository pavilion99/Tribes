package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.events.TribeInvitePlayerEvent;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TribeInvitePlayerListener implements Listener {

    @EventHandler
    public void onEvent(TribeInvitePlayerEvent e) {
        if(!e.isCancelled())
            if(e.getInvitee().isOnline())
                Message.message(e.getInvitee().getPlayer(), Config.invite, Config.colorOne, Config.colorTwo, e.getInviter().getName(), e.getTribe().getName());
    }

}
