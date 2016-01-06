package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesRemoveabilityCmd {

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t removeability <ability>");
            return;
        }

        Tribe tb = TribeLoader.getTribe((Player) sender);

        if(tb == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        AbilityType tability = AbilityType.getAbilityTypeFromString(args[1].toUpperCase());

        if(tability == null) {
            Message.message(sender, TribesCmd.err(), "Valid Abilities:");
            Message.message(sender, TribesCmd.err(), "fireresistance, haste, healthboost, invisibility, jump, nightvision, regen, resistance, saturation, speed, strength, waterbreathing");
            return;
        }

        for(AbilityType tc : tb.getAbilities()) {
            if(tc.getText().equals(tability.getText())) {
                tb.addCoins(1);
                if(tc.getMultiplier() == 0) {
                    String name = tc.getText();
                    tb.getAbilities().remove(tc);
                    Message.message(sender, Message.format(Config.removeAbility, Config.colorOne, Config.colorTwo, name));
                    return;
                } else {
                    String name = tc.getText();
                    tc.setMultiplier(tc.getMultiplier() - 1);
                    int multi = tc.getMultiplier();
                    Message.message(sender, Message.format(Config.lowerAbility, Config.colorOne, Config.colorTwo, name, Integer.toString(multi + 1)));
                    return;
                }
            }
        }
    }

}
