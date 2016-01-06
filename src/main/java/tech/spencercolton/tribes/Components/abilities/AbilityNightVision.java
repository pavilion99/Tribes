package tech.spencercolton.tribes.Components.abilities;

import tech.spencercolton.tribes.Components.Ability;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribeLoader;
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

        Tribe tee = TribeLoader.getTribe(this.p);

        if(tee == null)
            return;

        for(AbilityType ba : tee.getAbilities()) {
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
