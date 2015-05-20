package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityHealthBoost extends Ability {

    public AbilityHealthBoost(Player p, int i) {
        super(p, i, "healthboost");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            this.cancel();
            return;
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 60 * 60 * 20, multi));
    }

}
