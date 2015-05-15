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
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

public class TribesCmd extends TribeCommand {

    private final String[] acceptableFirstArgs = {"create", "invite", "kick", "destroy", "coins"};

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
        Message.message(s, "/t                Main command for tribes.");
        Message.message(s, "/t create                  Create a tribe.");
        Message.message(s, "/t invite    Invite someone to your tribe.");
        Message.message(s, "/t kick    Remove someone from your tribe.");
        Message.message(s, "/t destroy           Eliminate your tribe.");
        Message.message(s, "/t coins       Tribe currency information.");
        Message.message(s, "#----------------------------------------#");
    }

    private boolean run(CommandSender sender, String s, String[] args) {
        switch(s) {
            case "create":
                if(args.length != 2) {
                    Message.message(sender, "&c/t create <name>");
                    return true;
                }
                if(TribeLoader.tribeExists(args[1])) {
                    Message.message(sender, "&cThat tribe already exists.");
                    return true;
                }
                Tribe g = new Tribe(args[1], (Player)sender).push();
                TribeLoader.tribesList.add(g);

                Query q = new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", Integer.toString(g.getId()))).where("id", WhereType.EQUALS, Integer.toString((int)Tribes.Players.get(Bukkit.getPlayer(sender.getName()), "id")));
                q.close();

                break;
            case "invite":
                break;
            case "kick":
                break;
            case "destroy":
                Tribe t = Tribe.getTribe((Player)sender);
                if(t == null) {
                    Message.message(sender, "&cYou are not in a tribe.");
                    return true;
                }

                if(t.getMembers().get(sender) != TribeRank.CHIEF) {
                    Message.message(sender, "&You must be the chief to destroy the tribe.");
                    return true;
                }

                TribeLoader.tribesList.remove(t);

                Query h = new Query(QueryType.DELETE, "`tribes`").where("id", WhereType.EQUALS, Integer.toString(t.getId())).limit(1);
                h.query();
                h.close();

                h = new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", "''")).where("tribe", WhereType.EQUALS, Integer.toString(t.getId()));
                h.query();
                h.close();

                break;
            case "coins":
                break;
            default:
                Message.messageInvalidArgs(sender, this.getClass());
                return true;
        }
        return true;
    }

}
