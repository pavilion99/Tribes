package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import co.valdeon.Tribes.components.AbilityType;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityResistance extends Ability {

    public AbilityResistance(Player p, int i) {
        super(p, i, "resistance");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            this.cancel();
            return;
        }

        AbilityType b = null;
        for(AbilityType ba : TribeLoader.getTribe(this.p).getAbilities()) {
            if(ba.getText().equals(AbilityType.RESISTANCE.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        for(PotionEffect pe : this.p.getActivePotionEffects()) {
            if(pe.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) && pe.getAmplifier() != this.multi) {
                this.p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            }
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 60 * 20, multi));
    }

}
