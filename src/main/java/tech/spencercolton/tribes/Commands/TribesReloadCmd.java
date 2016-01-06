package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import org.bukkit.command.CommandSender;

class TribesReloadCmd {

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t reload");
            return;
        }
        Tribes.getInstance().reloadCfg();
        Message.message(sender, Config.colorOne, "Tribes reloaded.");
    }

}
