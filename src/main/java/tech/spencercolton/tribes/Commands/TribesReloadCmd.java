package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import org.bukkit.command.CommandSender;

class TribesReloadCmd {

    private final Tribes tribes;

    public TribesReloadCmd(Tribes t) {
        this.tribes = t;
    }

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t reload");
            return;
        }
        tribes.reloadCfg();
        Message.message(sender, Config.colorOne, "Tribes reloaded.");
    }

}
