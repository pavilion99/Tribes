package co.valdeon.Tribes.events;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.TribesEventCancellable;
import org.bukkit.entity.Player;

public class TribeEarnCoinsEvent extends TribesEventCancellable {

    private int amount;
    private Tribe tribe;
    private Player p;

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