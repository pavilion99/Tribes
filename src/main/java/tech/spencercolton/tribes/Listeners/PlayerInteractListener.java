package tech.spencercolton.tribes.Listeners;

import tech.spencercolton.tribes.Commands.TribesCmd;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void leftClick(PlayerInteractEvent e) {
        Action a = e.getAction();
        if(a != Action.LEFT_CLICK_BLOCK)
            return;

        if(!e.hasBlock())
            return;

        Chunk c = e.getPlayer().getLocation().getChunk();

        Tribe t = TribeLoader.getChunkOwner(c);
        if(!(t != null && t.equals(TribeLoader.getTribe(e.getPlayer())))) {
            e.setUseInteractedBlock(Event.Result.DENY);
            Message.message(e.getPlayer(), TribesCmd.err(), Config.errBuild);
        }
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        Action a = e.getAction();
        if(a != Action.RIGHT_CLICK_BLOCK) {
            if(!e.hasBlock())
                return;

            Chunk c = e.getPlayer().getLocation().getChunk();

            Tribe t = TribeLoader.getChunkOwner(c);
            if(!(t != null && t.equals(TribeLoader.getTribe(e.getPlayer())))) {
                e.setUseInteractedBlock(Event.Result.DENY);
                Message.message(e.getPlayer(), TribesCmd.err(), Config.errBuild);
            }
        }
    }

    @SuppressWarnings({"unused", "EmptyMethod"})
    @EventHandler
    public void physical(PlayerInteractEvent e) {
        // TODO Figure out what I was going to do with this.
        /* Action a = e.getAction();
        if(a != Action.PHYSICAL) {

        } */
    }

}
