package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.command.TribeCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class TribesCmd extends TribeCommand {

    private final String[] acceptableFirstArgs = {"create", "invite", "kick", "destroy", "coins", "join", "info", "claim", "list", "reload", "upgrade", "sethome", "home", "getcoins", "leave", "ability", "removeability", "i", "unclaim", "map"};

    public TribesCmd(Tribes t) {
        super(t);
    }

    public boolean execute(CommandSender s, String[] args, Tribes tribes) {
        this.tribes = tribes;

        if(s instanceof ConsoleCommandSender) {
            Message.message(s, Config.consoleExecutionFailure);
            return false;
        }

        if(args.length == 0 || args[0].equals("help")) {
            if(args.length == 2) {
                try {
                    int i = Integer.parseInt(args[1]);
                    echoInfo(s, i);
                    return true;
                } catch(Exception e) {
                    echoInfo(s, 1);
                    return true;
                }
            }
            echoInfo(s, 1);
            return true;
        }

        for(String l : acceptableFirstArgs) {
            if(l.equals(args[0])) {
                run(s, args[0], args);
                return true;
            }
        }

        return false;
    }

    private void echoInfo(CommandSender s, int page) {
        switch(page) {
            case 1:
                Message.message(s, Config.header);
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorCoins, "1&e/&a3");
                Message.message(s, Config.colorOne, "/t &f-" + Config.colorTwo + " Main command for tribes.");
                Message.message(s, Config.colorOne, "/t create &f-" + Config.colorTwo + " Create a tribe.");
                Message.message(s, Config.colorOne, "/t invite &f-" + Config.colorTwo + " Invite someone to your tribe.");
                Message.message(s, Config.colorOne, "/t kick &f-" + Config.colorTwo + " Remove someone from your tribe.");
                Message.message(s, Config.colorOne, "/t destroy &f-" + Config.colorTwo + " Eliminate your tribe.");
                Message.message(s, Config.colorOne, "/t coins &f-" + Config.colorTwo + " Tribe currency information.");
                Message.message(s, Config.footer);
                break;
            case 2:
                Message.message(s, Config.header);
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorCoins, "2&e/&a3");
                Message.message(s, Config.colorOne, "/t join &f-" + Config.colorTwo + " Join a tribe.");
                Message.message(s, Config.colorOne, "/t info &f-" + Config.colorTwo + " View info about a tribe.");
                Message.message(s, Config.colorOne, "/t invite &f-" + Config.colorTwo + " Invite someone to your tribe.");
                Message.message(s, Config.colorOne, "/t claim &f-" + Config.colorTwo + " Claim land for your tribe.");
                Message.message(s, Config.colorOne, "/t list &f-" + Config.colorTwo + " Show a list of tribes.");
                Message.message(s, Config.colorOne, "/t reload &f-" + Config.colorTwo + " Reload configuration.");
                Message.message(s, Config.footer);
                break;
            case 3:
                Message.message(s, Config.header);
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorCoins, "3&e/&a3");
                Message.message(s, Config.colorOne, "/t leave &f-" + Config.colorTwo + " Leave a tribe.");
                Message.message(s, Config.colorOne, "/t help &f-" + Config.colorTwo + " Show this information.");
                Message.message(s, Config.footer);
                break;
            default:
                Message.message(s, Config.header);
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorCoins, "1&e/&a3");
                Message.message(s, Config.colorOne, "/t &f-" + Config.colorTwo + " Main command for tribes.");
                Message.message(s, Config.colorOne, "/t create &f-" + Config.colorTwo + " Create a tribe.");
                Message.message(s, Config.colorOne, "/t invite &f-" + Config.colorTwo + " Invite someone to your tribe.");
                Message.message(s, Config.colorOne, "/t kick &f-" + Config.colorTwo + " Remove someone from your tribe.");
                Message.message(s, Config.colorOne, "/t destroy &f-" + Config.colorTwo + " Eliminate your tribe.");
                Message.message(s, Config.colorOne, "/t coins &f-" + Config.colorTwo + " Tribe currency information.");
                Message.message(s, Config.footer);
                break;
        }
    }

    private void run(CommandSender sender, String s, String[] args) {
        switch(s) {
            case "create":
                new TribesCreateCmd().execute(sender, args);
                break;
            case "invite":
                new TribesInviteCmd().execute(sender, args);
                break;
            case "kick":
                new TribesKickCmd().execute(sender, args);
                break;
            case "destroy":
                new TribesDestroyCmd().execute(sender, args);
                break;
            case "coins":
                new TribesCoinsCmd().execute(sender, args);
                break;
            case "join":
                new TribesJoinCmd().execute(sender, args);
                break;
            case "i":
            case "info":
                new TribesInfoCmd().execute(sender, args);
                break;
            case "claim":
                new TribesClaimCmd().execute(sender, args);
                break;
            case "unclaim":
                new TribesUnclaimCmd().execute(sender, args);
                break;
            case "list":
                new TribesListCmd().execute(sender, args);
                break;
            case "reload":
                new TribesReloadCmd(tribes).execute(sender, args);
                break;
            case "upgrade":
                new TribesUpgradeCmd().execute(sender, args);
                break;
            case "ability":
                new TribesAbilityCmd(tribes).execute(sender, args);
                break;
            case "home":
                new TribesHomeCmd().execute(sender, args);
                break;
            case "sethome":
                new TribesSethomeCmd().execute(sender, args);
                break;
            case "getcoins":
                new TribesGetcoinsCmd().execute(sender, args);
                break;
            case "leave":
                new TribesLeaveCmd().execute(sender, args);
                break;
            case "members":
                new TribesMembersCmd().execute(sender, args);
                break;
            case "promote":
                new TribesPromoteCmd().execute(sender, args);
                break;
            case "setchief":
                new TribesSetchiefCmd().execute(sender, args);
                break;
            case "removeability":
                new TribesRemoveabilityCmd().execute(sender, args);
                break;
            case "map":
                new TribesMapCmd().execute(sender, args);
                break;
            default:
                Message.messageInvalidArgs(sender, this.getClass());
                break;
        }
    }

    public static String err() {
        return "&" + Config.errorColor;
    }

}
