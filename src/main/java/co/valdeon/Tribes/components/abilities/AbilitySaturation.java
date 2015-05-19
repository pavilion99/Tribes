package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilitySaturation extends Ability {

    public AbilitySaturation(Player p, int i) {
        super(p, i);
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.cancel();
            return;
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 5 * 20, multi));
    }

}
