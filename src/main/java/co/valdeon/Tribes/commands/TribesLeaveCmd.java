package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.storage.Database;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
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
