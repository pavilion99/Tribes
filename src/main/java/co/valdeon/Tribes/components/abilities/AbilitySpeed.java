package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilitySpeed extends Ability {

    private Player p;

    @Override
    public void run() {
        Tribe t = TribeLoader.getChunkOwner(Bukkit.getWorld(Config.world).getChunkAt(p.getLocation()));

        if(t == null) {
            this.cancel();
            return;
        }

        if(TribeLoader.getTribe(p).equals(t))
            this.p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 1));
    }

    public AbilitySpeed(Player p) {
        this.p = p;
    }

}
