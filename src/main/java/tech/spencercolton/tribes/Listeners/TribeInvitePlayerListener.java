package tech.spencercolton.tribes.Listeners;

import tech.spencercolton.tribes.Events.TribeInvitePlayerEvent;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TribeInvitePlayerListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(TribeInvitePlayerEvent e) {
        if(!e.isCancelled())
            if(e.getInvitee().isOnline())
                Message.message(e.getInvitee().getPlayer(), Message.format(Config.invite, Config.colorOne, Config.colorTwo, e.getInviter().getName(), e.getTribe().getName()));
    }

}
