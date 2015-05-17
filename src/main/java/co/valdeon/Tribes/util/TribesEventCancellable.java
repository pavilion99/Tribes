package co.valdeon.Tribes.util;

import org.bukkit.event.Cancellable;

public class TribesEventCancellable extends TribesEvent implements Cancellable {

    private boolean cancelled;

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

}
