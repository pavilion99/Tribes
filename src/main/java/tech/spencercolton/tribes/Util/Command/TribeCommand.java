package tech.spencercolton.tribes.Util.Command;

import tech.spencercolton.tribes.Tribes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class TribeCommand implements CommandExecutor {

    protected Tribes tribes;

    protected TribeCommand(Tribes t) {
        this.tribes = t;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return execute(sender, args, tribes);
    }

    protected abstract boolean execute(CommandSender sender, String[] args, Tribes tribes);

}
