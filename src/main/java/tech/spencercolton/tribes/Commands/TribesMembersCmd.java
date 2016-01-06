package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

class TribesMembersCmd {

    public void execute(CommandSender sender, String[] args) {
        Tribe tribek = TribeLoader.getTribe((Player) sender);

        if (tribek == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        if(args.length == 1) {
            displayMembers(sender, tribek, 1);
        } else if(args.length == 2) {
            try {
                int ia = Integer.parseInt(args[1]);
                displayMembers(sender, tribek, ia);
            } catch(Exception e) {
                displayMembers(sender, tribek, 1);
            }
        } else {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t members [page]");
        }
    }

    private void displayMembers(CommandSender sender, Tribe t, int page) {
        List<OfflinePlayer> chiefs = new ArrayList<>();
        List<OfflinePlayer> officers = new ArrayList<>();
        List<OfflinePlayer> members = new ArrayList<>();

        List<OfflinePlayer> all = new ArrayList<>();

        for(OfflinePlayer p : t.getMembers().keySet()) {
            switch(t.getMembers().get(p).getName()) {
                case "CHIEF":
                    chiefs.add(p);
                    break;
                case "OFFICER":
                    officers.add(p);
                    break;
                case "MEMBER":
                    members.add(p);
                    break;
                default:
                    members.add(p);
            }
        }

        all.addAll(chiefs);
        all.addAll(officers);
        all.addAll(members);

        int totalSize = all.size();

        int totalPages = (int)Math.ceil(totalSize/6.0d);

        if(page > totalPages)
            page = 1;

        Message.message(sender, Message.format(Config.listMembers, Config.colorOne, Config.colorTwo, Integer.toString(page), Integer.toString(totalPages)));
        for(int i = 6 * (page - 1); i < 6 * page; i++) {
            if(i >= totalSize)
                break;

            Message.message(sender, Config.colorTwo, all.get(i).getName(), Config.colorOne, ", ", Config.colorTwo, t.getRank(all.get(i)).getName());
        }
    }

}
