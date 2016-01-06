package tech.spencercolton.tribes.Schedules;

import tech.spencercolton.tribes.Storage.SQLite.Query;
import org.bukkit.scheduler.BukkitRunnable;

public class PushPlayer extends BukkitRunnable {

    private final Query q;
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
