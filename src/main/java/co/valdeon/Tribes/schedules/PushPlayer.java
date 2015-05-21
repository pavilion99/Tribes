package co.valdeon.Tribes.schedules;

import co.valdeon.Tribes.storage.Query;
import org.bukkit.scheduler.BukkitRunnable;

public class PushPlayer extends BukkitRunnable {

    private Query q;
    private boolean setID = false;

    public PushPlayer(Query q) {
        this.q = q;
    }

    public PushPlayer(Query q, boolean b) {
        this.q = q;
        this.setID = b;
    }

    @Override
    public void run() {
        q.query(setID);
        q.close();
    }

}
