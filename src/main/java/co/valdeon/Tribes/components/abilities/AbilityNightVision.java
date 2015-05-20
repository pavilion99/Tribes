package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityNightVision extends Ability {

    public AbilityNightVision(Player p, int i) {
        super(p, i, "nightvision");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            this.cancel();
            return;
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60 * 60 * 20, multi));
    }

}
