package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class TribesCmd extends TribeCommand {

    private final String[] acceptableFirstArgs = {""};

    public boolean execute(CommandSender s, String[] args) {
        // TODO: Add Tribes stuff!

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
                run(s, args[1], args);
            }
        }

        return false;
    }

    private void echoInfo(CommandSender s) {
        Message.message(s, "#----------------Tribes----------------#");
        Message.message(s, "/t              Main command for tribes.");
        Message.message(s, "/t create                Create a tribe.");
        Message.message(s, "/t invite  Invite someone to your tribe.");
        Message.message(s, "/t kick  Remove someone from your tribe.");
        Message.message(s, "/t destroy         Eliminate your tribe.");
        Message.message(s, "#--------------------------------------#");
    }

    private void run(CommandSender sender, String s, String[] args) {
        switch(s) {
            default:
                Message.messageInvalidArgs(sender, this.getClass());
        }
    }

}
