package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import co.valdeon.Tribes.components.AbilityType;
import co.valdeon.Tribes.util.TribeLoader;
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

        AbilityType b = null;
        for(AbilityType ba : TribeLoader.getTribe(this.p).getAbilities()) {
            if(ba.getText().equals(AbilityType.HEALTHBOOST.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        for(PotionEffect pe : this.p.getActivePotionEffects()) {
            if(pe.getType().equals(PotionEffectType.HEALTH_BOOST) && pe.getAmplifier() != this.multi) {
                this.p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            }
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 60 * 60 * 20, multi));
    }

}
