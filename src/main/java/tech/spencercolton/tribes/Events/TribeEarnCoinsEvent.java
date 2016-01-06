package tech.spencercolton.tribes.Events;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribesEventCancellable;
import org.bukkit.entity.Player;

public class TribeEarnCoinsEvent extends TribesEventCancellable {

    private final int amount;
    private final Tribe tribe;
    private final Player p;

    public TribeEarnCoinsEvent(int amount, Tribe tribe, Player p) {
        this.amount = amount;
        this.tribe = tribe;
        this.p = p;
    }

    public TribeEarnCoinsEvent(int amount, Tribe tribe) {
        this.amount = amount;
        this.tribe = tribe;
        this.p = null;
    }

    public int getAmount() {
        return this.amount;
    }

    public Tribe getTribe() {
        return this.tribe;
    }

    public Player getPlayer() {
        return this.p;
    }

}
