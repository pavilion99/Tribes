package tech.spencercolton.tribes.Events;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribesEventCancellable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TribeKickPlayerEvent extends TribesEventCancellable {

    private final Player kicker;
    private final OfflinePlayer kickee;
    private final Tribe tribe;

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
