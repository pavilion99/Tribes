package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.components.TribeRank;
import co.valdeon.Tribes.schedules.PushPlayer;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.storage.Set;
import co.valdeon.Tribes.storage.WhereType;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
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
