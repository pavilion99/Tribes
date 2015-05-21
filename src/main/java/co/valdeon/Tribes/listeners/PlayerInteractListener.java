package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.commands.TribesCmd;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

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

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        Action a = e.getAction();
        if(a != Action.RIGHT_CLICK_BLOCK)
            return;
    }

    @EventHandler
    public void physical(PlayerInteractEvent e) {
        Action a = e.getAction();
        if(a != Action.PHYSICAL)
            return;
    }

}
