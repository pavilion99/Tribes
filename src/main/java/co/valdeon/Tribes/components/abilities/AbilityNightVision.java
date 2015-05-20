package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import co.valdeon.Tribes.components.AbilityType;
import co.valdeon.Tribes.util.TribeLoader;
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

        AbilityType b = null;
        for(AbilityType ba : TribeLoader.getTribe(this.p).getAbilities()) {
            if(ba.getText().equals(AbilityType.NIGHTVISION.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        for(PotionEffect pe : this.p.getActivePotionEffects()) {
            if(pe.getType().equals(PotionEffectType.NIGHT_VISION) && pe.getAmplifier() != this.multi) {
                this.p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60 * 60 * 20, multi));
    }

}
