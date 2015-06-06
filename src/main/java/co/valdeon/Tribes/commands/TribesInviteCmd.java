package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.events.TribeInvitePlayerEvent;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.storage.WhereType;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

class TribesInviteCmd {

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, "&c/t invite <name>");
            return;
        }

        if (TribeLoader.getTribe((Player) sender) == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        Query qa = new Query(QueryType.SELECT, "uuid", "`users`").where("name", WhereType.EQUALS, "'" + args[1] + "'");
        ResultSet rr = qa.query();

        try {
            if (rr.next()) {
                Tribe ta = TribeLoader.getTribe((Player)sender);

                if(ta == null)
                    return;

                Message.message(sender, Message.format(Config.inviteSender, Config.colorOne, Config.colorTwo, args[1], ta.getName()));
                Tribes.call(new TribeInvitePlayerEvent((Player) sender, Bukkit.getOfflinePlayer(UUID.fromString(rr.getString("uuid"))), TribeLoader.getTribe((Player) sender)));
                rr.close();
                qa.close();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Message.message(sender, TribesCmd.err(), Config.noPlayer);
    }

}
