package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesCoinsCmd {

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t coins");
            return;
        }

        if (TribeLoader.getTribe((Player) sender) == null) {
            Message.message(sender, Config.notInTribe);
            return;
        }

        Tribe tg = TribeLoader.getTribe((Player) sender);

        if(tg == null)
            // Shouldn't happen
            return;

        Message.message(sender, Message.format(Config.coins, Config.colorOne, Config.colorTwo, tg.getName(), Integer.toString(tg.getCoins())));
    }

}
