package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityHaste extends Ability {

    public AbilityHaste(Player p, int i) {
        super(p, i, "haste");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.FAST_DIGGING);
            this.cancel();
            return;
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60 * 60 * 20, multi));
    }

}
