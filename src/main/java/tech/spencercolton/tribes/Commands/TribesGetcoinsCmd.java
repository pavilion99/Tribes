package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesGetcoinsCmd {
    
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t getcoins <amount>");
            return;
        }

        int i;
        try {
            i = Integer.parseInt(args[1]);
        } catch(Exception e) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t getcoins <amount>");
            return;
        }

        if(i < 1) {
            Message.message(sender, TribesCmd.err(), Config.oneRequired);
            return;
        }

        Tribe tribeh = TribeLoader.getTribe((Player) sender);

        if(tribeh == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        if(!Tribes.getEcon().has((Player)sender, Config.coinPrice * i)) {
            Message.message(sender, TribesCmd.err(), Message.format(Config.lowBalance, Config.colorOne, Config.colorTwo, Integer.toString(Config.coinPrice * i), Double.toString(((Config.coinPrice * i) - Tribes.getEcon().getBalance((Player) sender))), Double.toString(Tribes.getEcon().getBalance((Player) sender))));
            return;
        }

        if(!Tribes.getEcon().withdrawPlayer((Player)sender, Config.coinPrice * i).transactionSuccess()) {
            Message.message(sender, TribesCmd.err(), Config.transactionError);
        } else {
            tribeh.addCoins(i).push();
            Message.message(sender, Message.format(Config.buyCoins, Config.colorOne, Config.colorTwo, Integer.toString(i), Integer.toString(Config.coinPrice * i), Double.toString(Tribes.getEcon().getBalance((Player)sender)), tribeh.getName(), Integer.toString(tribeh.getCoins()), Config.colorCoins));
        }
    }
    
}
