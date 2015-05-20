package co.valdeon.Tribes.components.abilities;

import co.valdeon.Tribes.components.Ability;
import co.valdeon.Tribes.components.AbilityType;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityJump extends Ability {

    public AbilityJump(Player p, int i) {
        super(p, i, "jump");
    }

    @Override
    public void run() {
        if(!chunkOwned()) {
            this.p.removePotionEffect(PotionEffectType.JUMP);
            this.cancel();
            return;
        }

        AbilityType b = null;
        for(AbilityType ba : TribeLoader.getTribe(this.p).getAbilities()) {
            if(ba.getText().equals(AbilityType.JUMP.getText())) {
                b = ba;
            }
        }

        if(b != null) {
            this.multi = b.getMultiplier();
        }

        for(PotionEffect pe : this.p.getActivePotionEffects()) {
            if(pe.getType().equals(PotionEffectType.JUMP) && pe.getAmplifier() != this.multi) {
                this.p.removePotionEffect(PotionEffectType.JUMP);
            }
        }

        this.p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60 * 60 * 20, multi));
    }

}
