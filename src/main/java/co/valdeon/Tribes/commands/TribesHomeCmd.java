package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesHomeCmd {
    
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t home");
        }

        Tribe t = TribeLoader.getTribe((Player) sender);

        if(t == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        if(t.getHome() == null) {
            Message.message(sender, TribesCmd.err(), Config.homeNotSet);
        }

        ((Player)sender).teleport(t.getHome());
        Message.message(sender, Message.format(Config.teleportHome, Config.colorOne, Config.colorTwo));
    }
    
}
