package tech.spencercolton.tribes.Listeners;

import org.bukkit.OfflinePlayer;
import tech.spencercolton.tribes.Events.TribeEarnCoinsEvent;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TribeEarnCoinsListener implements Listener {

    @SuppressWarnings({"unused"})
    @EventHandler
    public void onEvent(TribeEarnCoinsEvent e) {
        e.getTribe().addCoins(e.getAmount());

        e.getTribe().getMembers().keySet().stream().filter(OfflinePlayer::isOnline).forEach(p -> Message.message(p.getPlayer(), Message.format(Config.earnCoinsMessage, p.getName(), Integer.toString(e.getAmount()), e.getTribe().getName())));
    }

}
