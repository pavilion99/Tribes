package tech.spencercolton.tribes.Components.abilities;

import tech.spencercolton.tribes.Components.Ability;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("unused")
public class AbilityWaterBreathing extends Ability {

    public AbilityWaterBreathing(Player p, int i) {
        super(p, i, "waterbreathing");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.WATER_BREATHING);
            this.cancel();
            return;
        }

        AbilityType b = null;

        Tribe tee = TribeLoader.getTribe(this.p);

        if(tee == null)
            return;

        for(AbilityType ba : tee.getAbilities()) {
            if(ba.getText().equals(AbilityType.WATERBREATHING.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        this.p.getActivePotionEffects().stream().filter(pe -> pe.getType().equals(PotionEffectType.WATER_BREATHING) && pe.getAmplifier() != this.multi).forEach(pe -> this.p.removePotionEffect(PotionEffectType.WATER_BREATHING));

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 60 * 60 * 20, multi));
    }

}
