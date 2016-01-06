package tech.spencercolton.tribes.Components.abilities;

import tech.spencercolton.tribes.Components.Ability;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("unused")
public class AbilityInvisibility extends Ability {

    public AbilityInvisibility(Player p, int i) {
        super(p, i, "invisibility");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.INVISIBILITY);
            this.cancel();
            return;
        }

        AbilityType b = null;

        Tribe tee = TribeLoader.getTribe(this.p);

        if(tee == null)
            return;

        for(AbilityType ba : tee.getAbilities()) {
            if(ba.getText().equals(AbilityType.INVISIBILITY.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        this.p.getActivePotionEffects().stream().filter(pe -> pe.getType().equals(PotionEffectType.INVISIBILITY) && pe.getAmplifier() != this.multi).forEach(pe -> this.p.removePotionEffect(PotionEffectType.INVISIBILITY));

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60 * 60 * 20, multi));
    }

}
