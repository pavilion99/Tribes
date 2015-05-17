package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.events.TribeEarnCoinsEvent;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
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
