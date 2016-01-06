package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesSethomeCmd {

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t sethome");
            return;
        }

        Tribe t = TribeLoader.getTribe((Player) sender);

        if(t == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        if(t.getMembers().get(sender).getPower() <= 2) {
            Message.message(sender, TribesCmd.err(), Config.needMorePower);
            return;
        }

        t.setHome(((Player)sender).getLocation());
        Message.message(sender, Message.format(Config.setHome, Config.colorOne, Config.colorTwo, t.getName()));
    }

}
