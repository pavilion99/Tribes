package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.components.TribeRank;
import co.valdeon.Tribes.schedules.DeleteTribe;
import co.valdeon.Tribes.schedules.PushPlayer;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.storage.Set;
import co.valdeon.Tribes.storage.WhereType;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
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
