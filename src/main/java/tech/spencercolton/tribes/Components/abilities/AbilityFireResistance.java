package tech.spencercolton.tribes.Components.abilities;

import tech.spencercolton.tribes.Components.Ability;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("unused")
public class AbilityFireResistance extends Ability {

    public AbilityFireResistance(Player p, int i) {
        super(p, i, "fireresistance");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            this.cancel();
            return;
        }

        AbilityType b = null;

        Tribe tee = TribeLoader.getTribe(this.p);

        if(tee == null)
            return;

        for(AbilityType ba : tee.getAbilities()) {
            if(ba.getText().equals(AbilityType.FIRERESISTANCE.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        this.p.getActivePotionEffects().stream().filter(pe -> pe.getType().equals(PotionEffectType.FIRE_RESISTANCE) && pe.getAmplifier() != this.multi).forEach(pe -> this.p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE));

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60 * 60 * 20, multi));
    }

}
