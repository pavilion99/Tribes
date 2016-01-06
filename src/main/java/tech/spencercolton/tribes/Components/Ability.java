package tech.spencercolton.tribes.Components;

import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted"})
public abstract class Ability extends BukkitRunnable {

    protected final Player p;
    protected int multi;
    private final String string;

    protected Ability(Player p, int i, String s) {
        this.p = p;
        this.multi = i;
        this.string = s;
    }

    public abstract void run();

    protected boolean chunkOwned() {
        Tribe t = TribeLoader.getChunkOwner(Bukkit.getWorld(Config.world).getChunkAt(p.getLocation()));

        boolean hasAbility = false;
        if (t != null) {
            for (AbilityType a : t.getAbilities()) {
                if(a.getText().equalsIgnoreCase(this.string)) {
                    hasAbility = true;
                    break;
                }
            }
        }

        return t != null && t.equals(TribeLoader.getTribe(p)) && hasAbility;
    }

}
