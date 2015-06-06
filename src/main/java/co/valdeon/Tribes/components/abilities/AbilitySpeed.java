package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import co.valdeon.Tribes.components.AbilityType;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilitySpeed extends Ability {

    public AbilitySpeed(Player p, int i) {
        super(p, i, "speed");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.SPEED);
            this.cancel();
            return;
        }

        AbilityType b = null;

        Tribe tee = TribeLoader.getTribe(this.p);

        if(tee == null)
            return;

        for(AbilityType ba : tee.getAbilities()) {
            if(ba.getText().equals(AbilityType.SPEED.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        for(PotionEffect pe : this.p.getActivePotionEffects()) {
            if(pe.getType().equals(PotionEffectType.SPEED) && pe.getAmplifier() != this.multi) {
                this.p.removePotionEffect(PotionEffectType.SPEED);
            }
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 60 * 20, multi));
    }

}
