package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class TribesAbilityCmd {

    private final Tribes tribes;

    public TribesAbilityCmd(Tribes t) {
        this.tribes = t;
    }

    public void execute(CommandSender sender, String[] args) {
        if(args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t ability <ability>");
            return;
        }

        Tribe t = TribeLoader.getTribe((Player) sender);

        if(t == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        String ability = args[1];

        double price = Config.Prices.getPriceMap().get(ability.toLowerCase());

        if(price == 0) {
            Message.message(sender, TribesCmd.err(), "Valid Abilities:");
            Message.message(sender, TribesCmd.err(), "fireresistance, haste, healthboost, invisibility, jump, nightvision, regen, resistance, saturation, speed, strength, waterbreathing");
            return;
        }

        AbilityType a = AbilityType.getAbilityTypeFromString(ability.toUpperCase());

        if(a == null) {
            Message.message(sender, TribesCmd.err(), "Valid Abilities:");
            Message.message(sender, TribesCmd.err(), "fireresistance, haste, healthboost, invisibility, jump, nightvision, regen, resistance, saturation, speed, strength, waterbreathing");
            return;
        }

        for(AbilityType ab : t.getAbilities()) {
            if(ab.getText().equals(a.getText())) {
                price = Math.pow(price, ab.getMultiplier() + 1);
            }
        }

        int cons = t.getCoins();

        if(cons < price) {
            Message.message(sender, TribesCmd.err(), Message.format(Config.moreCoins, TribesCmd.err(), Config.colorTwo, Integer.toString((int)price), Integer.toString((int)price - cons), Integer.toString(cons), Config.colorCoins));
            return;
        }

        for(AbilityType gy : t.getAbilities()) {
            if(gy.getText().equals(a.getText())) {
                gy.setMultiplier(gy.getMultiplier() + 1);
                t.subtractCoins((int)price).push();
                Message.message(sender, Message.format(Config.buyAbility, Config.colorOne, Config.colorTwo, a.getText(), Integer.toString(a.getMultiplier() + 1), Integer.toString((int)price), Config.colorCoins));
                return;
            }
        }

        t.addAbility(a, tribes);
        t.subtractCoins((int)price).push();

        Message.message(sender, Message.format(Config.buyAbility, Config.colorOne, Config.colorTwo, a.getText(), Integer.toString(a.getMultiplier() + 1), Integer.toString((int)price), Config.colorCoins));
    }

}
