package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import co.valdeon.Tribes.components.AbilityType;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.TribeLoader;
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

        AbilityType b = null;

        Tribe tee = TribeLoader.getTribe(this.p);

        if(tee == null)
            return;

        for(AbilityType ba : tee.getAbilities()) {
            if(ba.getText().equals(AbilityType.HASTE.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        for(PotionEffect pe : this.p.getActivePotionEffects()) {
            if(pe.getType().equals(PotionEffectType.FAST_DIGGING) && pe.getAmplifier() != this.multi) {
                this.p.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60 * 60 * 20, multi));
    }

}
