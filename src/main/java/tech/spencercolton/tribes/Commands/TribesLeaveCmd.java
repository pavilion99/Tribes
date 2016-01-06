package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Storage.SQLite.Database;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesLeaveCmd {
    
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t leave");
        }

        Tribe t = TribeLoader.getTribe((Player) sender);

        if(t == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        if(t.getRank((Player)sender).getName().equals("CHIEF")) {
            Message.message(sender, TribesCmd.err(), Config.chiefLeave);
            return;
        }

        t.removeMember((Player)sender);
        Database.setPlayerMemberOfNoTribe((Player) sender);

        Message.message(sender, Message.format(Config.leave, Config.colorOne, Config.colorTwo, t.getName()));
    }
    
}
