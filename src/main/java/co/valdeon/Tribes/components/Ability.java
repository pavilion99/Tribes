package co.valdeon.Tribes.components;

import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Ability extends BukkitRunnable {

    protected Player p;
    protected int multi;
    protected String string;

    public Ability(Player p, int i, String s) {
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
