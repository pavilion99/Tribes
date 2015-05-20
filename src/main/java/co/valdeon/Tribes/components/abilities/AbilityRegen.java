package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityRegen extends Ability {

    public AbilityRegen(Player p, int i) {
        super(p, i, "regen");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.REGENERATION);
            this.cancel();
            return;
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60 * 60 * 20, multi));
    }

}
