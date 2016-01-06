package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeRank;
import tech.spencercolton.tribes.Events.TribeKickPlayerEvent;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
class TribesKickCmd {

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t kick <player>");
        }

        if (TribeLoader.getTribe((Player) sender) == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        Tribe tripe = TribeLoader.getTribe((Player) sender);

        if(tripe == null)
            // Shouldn't happen
            return;

        TribeRank tr = tripe.getRank((Player)sender);

        if(tr == null)
            // Shouldn't happen
            return;

        if (tr.getPower() < Config.kickPower) {
            Message.message(sender, TribesCmd.err(), Config.needMorePower);
            return;
        }

        if (!(tripe.getMembers().keySet()).contains(Bukkit.getOfflinePlayer(args[1]))) {
            Message.message(sender, TribesCmd.err(), Config.playerNotInTribe);
        }

        OfflinePlayer kickee = Bukkit.getOfflinePlayer(args[1]);

        if (tripe.getRank(kickee).getPower() > tripe.getRank((Player) sender).getPower()) {
            Message.message(sender, TribesCmd.err(), Config.playerHigherRank);
        }

        tripe.kick(kickee).push();

        Tribes.call(new TribeKickPlayerEvent((Player) sender, kickee, tripe));
    }

}
