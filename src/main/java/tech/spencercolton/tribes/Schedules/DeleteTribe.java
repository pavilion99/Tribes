package tech.spencercolton.tribes.Schedules;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Storage.SQLite.Query;
import tech.spencercolton.tribes.Storage.SQLite.QueryType;
import tech.spencercolton.tribes.Storage.SQLite.WhereType;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteTribe extends BukkitRunnable {

    private final Tribe t;

    public DeleteTribe(Tribe t) {
        this.t = t;
    }

    @Override
    public void run() {
        Query q = new Query(QueryType.DELETE, "`tribes`").where("id", WhereType.EQUALS, Integer.toString(t.getId()));
        q.query();
        q.close();
    }

}
