package co.valdeon.Tribes.components;

import co.valdeon.Tribes.components.abilities.AbilitySpeed;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Ability extends BukkitRunnable {

    protected Player p;
    protected int multi;

    public Ability(Player p, int i) {
        this.p = p;
        this.multi = i;
    }

    public abstract void run();

    protected boolean chunkOwned() {
        Tribe t = TribeLoader.getChunkOwner(Bukkit.getWorld(Config.world).getChunkAt(p.getLocation()));

        return t != null && t.equals(TribeLoader.getTribe(p));
    }

}
