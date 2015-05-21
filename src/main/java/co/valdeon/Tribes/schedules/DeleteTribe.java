package co.valdeon.Tribes.schedules;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.storage.Set;
import co.valdeon.Tribes.storage.WhereType;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteTribe extends BukkitRunnable {

    private Tribe t;

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
