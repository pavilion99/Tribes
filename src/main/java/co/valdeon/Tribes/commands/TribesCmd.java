package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.command.TribeCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

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
            if(l.equals(args[1])) {
                return run(s, args[1], args);
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
                break;
            case "invite":
                break;
            case "kick":
                break;
            case "destroy":
                break;
            case "coins":
                break;
            default:
                Message.messageInvalidArgs(sender, this.getClass());
                return false;
        }
        return true;
    }

}
