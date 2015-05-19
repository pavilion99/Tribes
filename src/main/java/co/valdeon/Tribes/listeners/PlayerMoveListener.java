package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.AbilityType;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.components.abilities.AbilitySpeed;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class PlayerMoveListener implements Listener {

    private Tribes tribes;

    public PlayerMoveListener(Tribes t) {
        this.tribes = t;
    }

    @EventHandler
    public void onEvent(PlayerMoveEvent e) {
        Tribe t = TribeLoader.getChunkOwner(Bukkit.getWorld(Config.world).getChunkAt(e.getTo()));

        if(t == null)
            return;

        if(t.equals(TribeLoader.getTribe(e.getPlayer()))) {
            List<AbilityType> a = t.getAbilities();

            for(AbilityType g : a) {
                switch(g.getText()) {
                    case "SPEED":
                        new AbilitySpeed(e.getPlayer()).runTaskTimer(this.tribes, 0, 20);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
