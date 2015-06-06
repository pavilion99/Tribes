package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.components.TribeRank;
import co.valdeon.Tribes.storage.Database;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
class TribesPromoteCmd {

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t promote <user>");
        }

        Tribe tribey = TribeLoader.getTribe((Player) sender);

        if(tribey == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        if(!tribey.getMembers().keySet().contains(Bukkit.getOfflinePlayer(args[1]))) {
            Message.message(sender, TribesCmd.err(), Config.playerNotInTribe);
            return;
        }

        OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);

        if(tribey.getRank((Player)sender).getPower() <= tribey.getRank(p).getPower()) {
            Message.message(sender, TribesCmd.err(), Config.needMorePower);
            return;
        }

        int power = tribey.getRank(p).getPower();
        TribeRank newRank = TribeRank.getRankFromPower(power + 1);

        if(newRank == TribeRank.CHIEF) {
            Message.message(sender, TribesCmd.err(), Config.oneChief);
            return;
        }

        tribey.setRank(p, newRank);

        Database.setRank(p, newRank);

        if(p.isOnline()) {
            Message.message(p.getPlayer(), Message.format(Config.promotee, Config.colorOne, Config.colorTwo, newRank.getName(), tribey.getName(), sender.getName()));
        }

        Message.message(sender, Message.format(Config.promoter, Config.colorOne, Config.colorTwo, newRank.getName(), tribey.getName(), p.getName()));
    }

}
