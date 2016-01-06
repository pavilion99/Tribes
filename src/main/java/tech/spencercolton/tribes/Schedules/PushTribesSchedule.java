package tech.spencercolton.tribes.Schedules;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

@SuppressWarnings("unused")
public class PushTribesSchedule extends BukkitRunnable {

    @Override
    public void run() {
        TribeLoader.tribesList.forEach(Tribe::push);
        Tribes.log(Level.INFO, "Saved tribes to the database.");
    }

}
