package tech.spencercolton.tribes.Schedules;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Storage.SQLite.Database;
import org.bukkit.scheduler.BukkitRunnable;

public class PushTribe extends BukkitRunnable {

    private final Tribe t;

    public PushTribe(Tribe t) {
        this.t = t;
    }

    @Override
    public void run() {
        Database.pushTribe(t);
    }

}
