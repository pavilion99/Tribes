package tech.spencercolton.tribes.Listeners;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribeLoader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        String formatWithTribe = "<tribe> <%s> %s";
        String formatWithoutTribe = "<%s> %s";

        String tribe;
        Tribe t = TribeLoader.getTribe(e.getPlayer());
        if(!(t == null)) {
            tribe = t.getName();
            formatWithTribe = formatWithTribe.replace("<tribe>", "&9(&b" + tribe + "&9)&r");
            e.setFormat(ChatColor.translateAlternateColorCodes('&', formatWithTribe));
        } else {
            formatWithoutTribe = formatWithoutTribe.replace("<player>", "<" + p.getName() + ">");
            formatWithoutTribe = formatWithoutTribe.replace("<message>", e.getMessage());
            e.setFormat(ChatColor.translateAlternateColorCodes('&', formatWithoutTribe));
        }
    }

}
