package co.valdeon.Tribes.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class TribeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return execute(sender, args);
    }

    public abstract boolean execute(CommandSender sender, String[] args);

}
