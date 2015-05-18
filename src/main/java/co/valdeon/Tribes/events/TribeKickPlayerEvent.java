package co.valdeon.Tribes.events;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.TribesEventCancellable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TribeKickPlayerEvent extends TribesEventCancellable {

    private Player kicker;
    private OfflinePlayer kickee;
    private Tribe tribe;

    public TribeKickPlayerEvent(Player kicker, OfflinePlayer kickee, Tribe tribe) {
        this.kicker = kicker;
        this.kickee = kickee;
        this.tribe =tribe;
    }

    public Player getKicker() {
        return this.kicker;
    }

    public OfflinePlayer getKickee() {
        return this.kickee;
    }

    public Tribe getTribe() {
        return this.tribe;
    }

}
