package co.valdeon.Tribes.schedules;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.storage.Database;
import org.bukkit.scheduler.BukkitRunnable;

public class PushTribe extends BukkitRunnable {

    private Tribe t;

    public PushTribe(Tribe t) {
        this.t = t;
    }

    @Override
    public void run() {
        Database.pushTribe(t);
    }

}
