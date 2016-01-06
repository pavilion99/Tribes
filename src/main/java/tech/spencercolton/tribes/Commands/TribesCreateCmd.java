package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeRank;
import tech.spencercolton.tribes.Schedules.PushPlayer;
import tech.spencercolton.tribes.Storage.SQLite.Query;
import tech.spencercolton.tribes.Storage.SQLite.QueryType;
import tech.spencercolton.tribes.Storage.SQLite.Set;
import tech.spencercolton.tribes.Storage.SQLite.WhereType;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesCreateCmd {

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Message.message(sender, "&cProper usage:");
            Message.message(sender, "&c/t create <name>");
            return;
        }

        if (TribeLoader.getTribe((Player) sender) != null) {
            Message.message(sender, "&cYou are already in a tribe; you must destroy it before creating a new tribe.");
            return;
        }

        if (TribeLoader.tribeExists(args[1])) {
            Message.message(sender, "&cThat tribe already exists.");
            return;
        }

        Tribe g = new Tribe(args[1], (Player) sender).push(true);
        TribeLoader.tribesList.add(g);

        new PushPlayer(new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", Integer.toString(g.getId())), new Set("role", "'" + TribeRank.CHIEF.getName() + "'")).where("uuid", WhereType.EQUALS, "'" + ((Player) sender).getUniqueId().toString() + "'")).runTaskAsynchronously(TribeLoader.getTribes());

        Message.message(sender, "&9You have successfully created the tribe &e" + g.getName() + "&9.");
    }

}
