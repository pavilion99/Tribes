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
class TribesSetchiefCmd {

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t setchief <user>");
            return;
        }

        Tribe tribew = TribeLoader.getTribe((Player) sender);

        if(tribew == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        TribeRank senderRank = tribew.getRank((Player)sender);

        if(senderRank != TribeRank.CHIEF) {
            Message.message(sender, TribesCmd.err(), Config.notChief);
            return;
        }

        OfflinePlayer gPlayer = Bukkit.getOfflinePlayer(args[1]);

        if(!tribew.getMembers().containsKey(gPlayer)) {
            Message.message(sender, TribesCmd.err(), Config.playerNotInTribe);
            return;
        }

        tribew.getMembers().put((Player)sender, TribeRank.MEMBER);
        tribew.getMembers().put(gPlayer, TribeRank.CHIEF);

        Database.setRank((Player) sender, TribeRank.MEMBER);
        Database.setRank(gPlayer, TribeRank.CHIEF);

        Message.message(sender, Message.format(Config.chiefResign, Config.colorOne, Config.colorTwo, tribew.getName()));
        if(gPlayer.isOnline()) {
            Message.message(gPlayer.getPlayer(), Message.format(Config.promotee, senderRank.getName(), tribew.getName(), sender.getName()));
        }
    }

}
