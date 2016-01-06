package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeRank;
import tech.spencercolton.tribes.Storage.SQLite.Database;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
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
