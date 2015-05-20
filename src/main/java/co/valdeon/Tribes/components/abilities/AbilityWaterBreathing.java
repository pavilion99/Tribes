package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityWaterBreathing extends Ability {

    public AbilityWaterBreathing(Player p, int i) {
        super(p, i, "waterbreathing");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.WATER_BREATHING);
            this.cancel();
            return;
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 60 * 60 * 20, multi));
    }

}
