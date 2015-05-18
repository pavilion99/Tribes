package co.valdeon.Tribes.listeners;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        String formatWithTribe = "<tribe><player>: <message>";
        String formatWithoutTribe = "<player>: <message>";

        String tribe = "";
        Tribe t = TribeLoader.getTribe(e.getPlayer());
        if(!(t == null)) {
            tribe = t.getName();
            formatWithTribe = formatWithTribe.replace("<tribe>", "[" + tribe + "]");
            formatWithTribe = formatWithTribe.replace("<player>", "<" + p.getName() + ">");
            formatWithTribe = formatWithTribe.replace("<message>", "<" + e.getMessage() + ">");
            e.setFormat(formatWithTribe);
        } else {
            formatWithoutTribe = formatWithoutTribe.replace("<player>", "<" + p.getName() + ">");
            formatWithoutTribe = formatWithoutTribe.replace("<message>", e.getMessage());
            e.setFormat(formatWithoutTribe);
        }
    }

}
