package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesInfoCmd {

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Tribe ta = TribeLoader.getTribe((Player) sender);

            if (ta != null) {
                Message.message(sender, Config.header);
                Message.message(sender, Config.colorOne + "Name: " + Config.colorTwo + ta.getName());
                Message.message(sender, Config.colorOne + "Claimed land: " + Config.colorTwo + ta.getChunks().size() + Config.colorOne + " chunks");
                Message.message(sender, Config.colorOne + "Tribe tier: " + Config.colorTwo + ta.getTier().getValue());
                Message.message(sender, Config.colorOne + "Members: " + Config.colorTwo + ta.getMembers().size());
                Message.message(sender, Config.colorOne + "Coins: " + Config.colorCoins + ta.getCoins());
                Message.message(sender, Config.colorOne + "Abilities: " + Config.colorTwo + ta.getAbilityString(true));
                Message.message(sender, Config.footer);
            } else {
                Message.message(sender, "&cYou are not currently in a tribe.");
            }
        } else if (args.length == 2) {
            Tribe ta = TribeLoader.getTribeFromStringIgnoreCase(args[1]);

            if (ta != null) {
                Message.message(sender, Config.header);
                Message.message(sender, Config.colorOne + "Name: " + Config.colorTwo + ta.getName());
                Message.message(sender, Config.colorOne + "Claimed land: " + Config.colorTwo + ta.getChunks().size() + Config.colorOne + " chunks");
                Message.message(sender, Config.colorOne + "Tribe tier: " + Config.colorTwo + ta.getTier().getValue());
                Message.message(sender, Config.colorOne + "Members: " + Config.colorTwo + ta.getMembers().size());
                Message.message(sender, Config.colorOne + "Coins: " + Config.colorCoins + ta.getCoins());
                Message.message(sender, Config.colorOne + "Abilities: " + Config.colorTwo + ta.getAbilityString(true));
                Message.message(sender, Config.footer);
            } else {
                Message.message(sender, TribesCmd.err(), "That tribe doesn't exist.");
            }
        } else {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, "&c/t info [name]");
        }
    }

}
