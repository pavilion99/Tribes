package co.valdeon.Tribes.util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

class TribesEvent extends Event {

    public boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
