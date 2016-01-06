package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeRank;
import tech.spencercolton.tribes.Schedules.DeleteTribe;
import tech.spencercolton.tribes.Schedules.PushPlayer;
import tech.spencercolton.tribes.Storage.SQLite.Query;
import tech.spencercolton.tribes.Storage.SQLite.QueryType;
import tech.spencercolton.tribes.Storage.SQLite.Set;
import tech.spencercolton.tribes.Storage.SQLite.WhereType;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesDestroyCmd {

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t destroy");
        }

        Tribe t = Tribe.getTribe((Player) sender);

        if (t == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        if (t.getRank((Player) sender) != TribeRank.CHIEF) {
            Message.message(sender, TribesCmd.err(), Config.notChief);
            return;
        }

        TribeLoader.tribesList.remove(t);
        TribeLoader.ownedChunks.remove(t);

        new DeleteTribe(t).runTaskAsynchronously(TribeLoader.getTribes());

        new PushPlayer(new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", "0"), new Set("role", "''")).where("tribe", WhereType.EQUALS, Integer.toString(t.getId()))).runTaskAsynchronously(TribeLoader.getTribes());

        Message.message(sender, Message.format(Config.destroy, Config.colorOne, Config.colorTwo, t.getName()));
    }

}
