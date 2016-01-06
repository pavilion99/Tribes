package tech.spencercolton.tribes.Listeners;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tech.spencercolton.tribes.Components.abilities.*;

import java.util.List;

public class PlayerMoveListener implements Listener {

    @SuppressWarnings("unused")
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
                        new AbilitySpeed(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "JUMP":
                        new AbilityJump(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "REGEN":
                        new AbilityRegen(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "FIRERESISTANCE":
                        new AbilityFireResistance(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "HASTE":
                        new AbilityHaste(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "HEALTHBOOST":
                        new AbilityHealthBoost(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "INVISIBILITY":
                        new AbilityInvisibility(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "NIGHTVISION":
                        new AbilityNightVision(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "RESISTANCE":
                        new AbilityResistance(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "SATURATION":
                        new AbilitySaturation(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "STRENGTH":
                        new AbilityStrength(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    case "WATERBREATHING":
                        new AbilityWaterBreathing(e.getPlayer(), g.getMultiplier()).runTaskTimer(Tribes.getInstance(), 0, 20);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void announceTribe(PlayerMoveEvent e) {
        Tribe t = TribeLoader.getChunkOwner(e.getTo().getChunk());
        Tribe u = TribeLoader.getChunkOwner(e.getFrom().getChunk());

        if(t != null) {
            if(t.equals(u))
                return;

            Message.message(e.getPlayer(), Message.format(Config.enteringTerritory, Config.colorOne, Config.colorTwo, t.getName()));
        } else {
            if(u != null) {
                Message.message(e.getPlayer(), Message.format(Config.enteringNoTerritory));
            }
        }
    }

}
