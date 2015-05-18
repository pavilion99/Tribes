package co.valdeon.Tribes.components;

import co.valdeon.Tribes.components.abilities.AbilitySpeed;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Ability extends BukkitRunnable {

    public abstract void run();

    public static Class getAbility(String s) {
        switch(s) {
            case "speed":
                return AbilitySpeed.class;
            default:
                return null;
        }
    }

}
