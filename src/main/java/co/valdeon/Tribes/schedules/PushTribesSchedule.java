package co.valdeon.Tribes.schedules;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.scheduler.BukkitRunnable;

public class PushTribesSchedule extends BukkitRunnable {

    @Override
    public void run() {
        for(Tribe t : TribeLoader.tribesList) {
            t.push();
        }
    }

}
