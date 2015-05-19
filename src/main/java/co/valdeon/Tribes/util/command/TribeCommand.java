package co.valdeon.Tribes.util.command;

import co.valdeon.Tribes.Tribes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class TribeCommand implements CommandExecutor {

    protected Tribes tribes;

    public TribeCommand(Tribes t) {
        this.tribes = t;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return execute(sender, args, tribes);
    }

    public abstract boolean execute(CommandSender sender, String[] args, Tribes tribes);

}
