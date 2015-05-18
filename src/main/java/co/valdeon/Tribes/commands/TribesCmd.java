package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.components.TribeRank;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.storage.Set;
import co.valdeon.Tribes.storage.WhereType;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import co.valdeon.Tribes.util.command.TribeCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class TribesCmd extends TribeCommand {

    private final String[] acceptableFirstArgs = {"create", "invite", "kick", "destroy", "coins", "join"};

    public boolean execute(CommandSender s, String[] args) {
        if(s instanceof ConsoleCommandSender) {
            Message.message(s, Config.consoleExecutionFailure);
            return false;
        }

        if(args.length == 0) {
            echoInfo(s);
            return true;
        }

        for(String l : acceptableFirstArgs) {
            if(l.equals(args[0])) {
                return run(s, args[0], args);
            }
        }

        return false;
    }

    private void echoInfo(CommandSender s) {
        Message.message(s, "#-----------------Tribes-----------------#");
        Message.message(s, "/t - Main command for tribes.");
        Message.message(s, "/t create - Create a tribe.");
        Message.message(s, "/t invite - Invite someone to your tribe.");
        Message.message(s, "/t kick - Remove someone from your tribe.");
        Message.message(s, "/t destroy - Eliminate your tribe.");
        Message.message(s, "/t coins - Tribe currency information.");
        Message.message(s, "#----------------------------------------#");
    }

    private boolean run(CommandSender sender, String s, String[] args) {
        switch(s) {
            case "create":
                if(args.length != 2) {
                    Message.message(sender, "&cProper usage:");
                    Message.message(sender, "&c/t create <name>");
                    return true;
                }

                if(TribeLoader.tribeExists(args[1])) {
                    Message.message(sender, "&cThat tribe already exists.");
                    return true;
                }

                if(TribeLoader.getTribe((Player)sender) != null) {
                    Message.message(sender, "&cYou are already in a tribe; you must destroy it before creating a new tribe.");
                    return true;
                }


                Tribe g = new Tribe(args[1], (Player)sender).push();
                TribeLoader.tribesList.add(g);

                Query q = new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", Integer.toString(g.getId())), new Set("role", "'" + TribeRank.CHIEF.getName() + "'")).where("id", WhereType.EQUALS, Integer.toString((int)Tribes.Players.get((Player)sender, "id")));
                Tribes.log(Level.INFO, Integer.toString((int)Tribes.Players.get((Player)sender, "id")));
                Tribes.log(Level.INFO, Integer.toString(g.getId()));
                q.query();
                q.close();

                Message.message(sender, "&9You have successfully created the tribe &e" + g.getName() + "&9.");

                break;
            case "invite":
                if(args.length != 2) {
                    Message.message(sender, "&cProper usage:");
                    Message.message(sender, "&c/t invite <name>");
                    return true;
                }

                if(TribeLoader.getTribe((Player)sender) == null) {
                    Message.message(sender, "&cYou must be in a tribe before you can invite someone else.");
                    return true;
                }

                Query qa = new Query(QueryType.SELECT, "uuid", "`users`").where("name", WhereType.EQUALS, "'" + args[1] + "'");
                ResultSet rr = qa.query();

                try {
                    if (rr.next()) {
                        Message.message(sender, "&8Successfully invited &e" + args[1] + "&8 to join &e" + TribeLoader.getTribe((Player)sender).getName() + "&8.");
                        rr.close();
                        qa.close();
                        return true;
                    }
                }catch(SQLException e) {
                    e.printStackTrace();
                }

                Message.message(sender, "&cUnable to find a player with that name.");

                break;
            case "kick":
                break;
            case "destroy":
                Tribe t = Tribe.getTribe((Player)sender);

                if(t == null) {
                    Message.message(sender, "&cYou are not in a tribe.");
                    return true;
                }

                if(t.getRank((Player)sender) != TribeRank.CHIEF) {
                    Message.message(sender, "&You must be the chief to destroy the tribe.");
                    return true;
                }

                TribeLoader.tribesList.remove(t);

                Query h = new Query(QueryType.DELETE, "`tribes`").where("id", WhereType.EQUALS, Integer.toString(t.getId()));
                h.query();
                h.close();

                h = new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", "0"), new Set("role", "''")).where("tribe", WhereType.EQUALS, Integer.toString(t.getId()));
                h.query();
                h.close();

                break;
            case "coins":
                break;
            case "join":
                if(args.length != 2) {
                    Message.message(sender, "&c/t join <name>");
                    return true;
                }

                if(Tribe.getTribe((Player)sender) != null) {
                    Message.message(sender, "&cYou are already a member of " + Tribe.getTribe((Player)sender).getName() + ", please leave before attempting to join a new tribe.");
                    return true;
                }

                Tribe tribe = TribeLoader.getTribeFromString(args[1]);

                if(tribe != null) {
                    tribe.join((Player)sender);
                }else {
                    Message.message(sender, "&cThat tribe does not exist.");
                    return true;
                }

                break;
            default:
                Message.messageInvalidArgs(sender, this.getClass());
                return true;
        }
        return true;
    }

}
