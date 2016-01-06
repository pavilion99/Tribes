package tech.spencercolton.tribes.Components.abilities;

import tech.spencercolton.tribes.Components.Ability;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityStrength extends Ability {

    public AbilityStrength(Player p, int i) {
        super(p, i, "strength");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            this.cancel();
            return;
        }

        AbilityType b = null;

        Tribe tee = TribeLoader.getTribe(this.p);

        if(tee == null)
            return;

        for(AbilityType ba : tee.getAbilities()) {
            if(ba.getText().equals(AbilityType.STRENGTH.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        for(PotionEffect pe : this.p.getActivePotionEffects()) {
            if(pe.getType().equals(PotionEffectType.INCREASE_DAMAGE) && pe.getAmplifier() != this.multi) {
                this.p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            }
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60 * 60 * 20, multi));
    }

}
