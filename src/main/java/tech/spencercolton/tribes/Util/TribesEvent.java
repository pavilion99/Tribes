package tech.spencercolton.tribes.Util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

class TribesEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
