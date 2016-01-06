package tech.spencercolton.tribes.Listeners;

import tech.spencercolton.tribes.Events.TribeEarnCoinsEvent;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TribeEarnCoinsListener implements Listener {

    @EventHandler
    public void onEvent(TribeEarnCoinsEvent e) {
        e.getTribe().addCoins(e.getAmount());

        for(OfflinePlayer p : e.getTribe().getMembers().keySet()) {
            if(p.isOnline()) {
                Message.message(p.getPlayer(), Message.format(Config.earnCoinsMessage, p.getName(), Integer.toString(e.getAmount()), e.getTribe().getName()));
            }
        }
    }

}
