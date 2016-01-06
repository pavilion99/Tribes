package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeTier;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesUpgradeCmd {
    
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t upgrade");
            return;
        }

        Tribe playerTribe = TribeLoader.getTribe((Player) sender);

        if(playerTribe == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        int coins = playerTribe.getCoins();
        TribeTier tier = playerTribe.getTier();

        switch(tier.getValue()) {
            case 1:
                if(coins < 2) {
                    Message.message(sender, TribesCmd.err(), Message.format(Config.moreCoins, Integer.toString(2), Integer.toString(2 - coins), Integer.toString(coins), Config.colorCoins));
                    return;
                }

                playerTribe.subtractCoins(2).push();
                playerTribe.setTier(TribeTier.TIER_TWO).push();
                break;
            case 2:
                if(coins < 4) {
                    Message.message(sender, TribesCmd.err(), Message.format(Config.moreCoins, Integer.toString(4), Integer.toString(4 - coins), Integer.toString(coins), Config.colorCoins));
                    return;
                }

                playerTribe.subtractCoins(4).push();
                playerTribe.setTier(TribeTier.TIER_THREE).push();
                break;
            case 3:
                if(coins < 6) {
                    Message.message(sender, TribesCmd.err(), Message.format(Config.moreCoins, Integer.toString(6), Integer.toString(6 - coins), Integer.toString(coins), Config.colorCoins));
                    return;
                }

                playerTribe.subtractCoins(6).push();
                playerTribe.setTier(TribeTier.TIER_FOUR).push();
                break;
            case 4:
                if(coins < 8) {
                    Message.message(sender, TribesCmd.err(), Message.format(Config.moreCoins, Integer.toString(8), Integer.toString(8 - coins), Integer.toString(coins), Config.colorCoins));
                    return;
                }

                playerTribe.subtractCoins(8).push();
                playerTribe.setTier(TribeTier.TIER_FIVE).push();
                break;
            case 5:
                Message.message(sender, TribesCmd.err(), Config.fullyUpgraded);
                break;
        }
    }
    
}
