package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.storage.Database;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesJoinCmd {
    
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t join <name>");
            return;
        }

        Tribe ta = TribeLoader.getTribe((Player)sender);

        if(ta == null)
            // Shouldn't happen
            return;

        if (Tribe.getTribe((Player) sender) != null) {
            Message.message(sender, TribesCmd.err(), Message.format(Config.inExistingTribe, ta.getName()));
            return;
        }

        Tribe tribe = TribeLoader.getTribeFromString(args[1]);

        if (tribe != null) {
            tribe.join((Player) sender);
            Database.setPlayerMemberOfTribe((Player) sender, tribe, tribe.getRank((Player) sender));
        } else {
            Message.message(sender, TribesCmd.err(), Config.noExist);
        }
    }
    
}
