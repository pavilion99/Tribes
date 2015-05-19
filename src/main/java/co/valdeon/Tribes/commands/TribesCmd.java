package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.components.TribeRank;
import co.valdeon.Tribes.components.TribeTier;
import co.valdeon.Tribes.events.TribeInvitePlayerEvent;
import co.valdeon.Tribes.events.TribeKickPlayerEvent;
import co.valdeon.Tribes.storage.*;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import co.valdeon.Tribes.util.command.TribeCommand;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class TribesCmd extends TribeCommand {

    private final String[] acceptableFirstArgs = {"create", "invite", "kick", "destroy", "coins", "join", "info", "claim", "list", "reload", "upgrade", "sethome", "home", "getcoins", "leave"};

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
                return run(s, args[0], args);
            }
        }

        return false;
    }

    private void echoInfo(CommandSender s, int page) {
        switch(page) {
            case 1:
                Message.message(s, Config.header);
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorTwo, "1");
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
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorTwo, "2");
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
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorTwo, "3");
                Message.message(s, Config.colorOne, "/t leave &f-" + Config.colorTwo + " Leave a tribe.");
                Message.message(s, Config.colorOne, "/t help &f-" + Config.colorTwo + " Show this information.");
                Message.message(s, Config.footer);
                break;
            default:
                Message.message(s, Config.header);
                Message.message(s, Config.colorOne, "Tribes Help - Page ", Config.colorTwo, "1");
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

    private boolean run(CommandSender sender, String s, String[] args) {
        switch(s) {
            case "create":
                if (args.length != 2) {
                    Message.message(sender, "&cProper usage:");
                    Message.message(sender, "&c/t create <name>");
                    return true;
                }

                if (TribeLoader.tribeExists(args[1])) {
                    Message.message(sender, "&cThat tribe already exists.");
                    return true;
                }

                if (TribeLoader.getTribe((Player) sender) != null) {
                    Message.message(sender, "&cYou are already in a tribe; you must destroy it before creating a new tribe.");
                    return true;
                }


                Tribe g = new Tribe(args[1], (Player) sender).push(true);
                TribeLoader.tribesList.add(g);

                Query q = new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", Integer.toString(g.getId())), new Set("role", "'" + TribeRank.CHIEF.getName() + "'")).where("id", WhereType.EQUALS, Integer.toString((int) Tribes.Players.get((Player) sender, "id")));
                Tribes.log(Level.INFO, Integer.toString((int) Tribes.Players.get((Player) sender, "id")));
                Tribes.log(Level.INFO, Integer.toString(g.getId()));
                q.query();
                q.close();

                Message.message(sender, "&9You have successfully created the tribe &e" + g.getName() + "&9.");

                break;
            case "invite":
                if (args.length != 2) {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, "&c/t invite <name>");
                    return true;
                }

                if (TribeLoader.getTribe((Player) sender) == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                Query qa = new Query(QueryType.SELECT, "uuid", "`users`").where("name", WhereType.EQUALS, "'" + args[1] + "'");
                ResultSet rr = qa.query();

                try {
                    if (rr.next()) {
                        Message.message(sender, Message.format(Config.inviteSender, Config.colorOne, Config.colorTwo, args[1], TribeLoader.getTribe((Player) sender).getName()));
                        Tribes.call(new TribeInvitePlayerEvent((Player) sender, Bukkit.getOfflinePlayer(UUID.fromString(rr.getString("uuid"))), TribeLoader.getTribe((Player) sender)));
                        rr.close();
                        qa.close();
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Message.message(sender, err(), Config.noPlayer);

                break;
            case "kick":
                if(args.length != 2) {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, err(), "/t kick <player>");
                }

                if(TribeLoader.getTribe((Player)sender) == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                Tribe tripe = TribeLoader.getTribe((Player)sender);

                if(tripe.getRank((Player)sender).getPower() < Config.kickPower) {
                    Message.message(sender, err(), Config.needMorePower);
                    return true;
                }

                if(!(tripe.getMembers().keySet()).contains(Bukkit.getOfflinePlayer(args[1]))) {
                    Message.message(sender, err(), Config.playerNotInTribe);
                }

                OfflinePlayer kickee = Bukkit.getOfflinePlayer(args[1]);

                if(tripe.getRank(kickee).getPower() > tripe.getRank((Player)sender).getPower()) {
                    Message.message(sender, err(), Config.playerHigherRank);
                }

                tripe.kick(kickee).push();

                Tribes.call(new TribeKickPlayerEvent((Player)sender, kickee, tripe));

                break;
            case "destroy":
                Tribe t = Tribe.getTribe((Player) sender);

                if (t == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                if (t.getRank((Player) sender) != TribeRank.CHIEF) {
                    Message.message(sender, err(), Config.notChief);
                    return true;
                }

                TribeLoader.tribesList.remove(t);

                Query h = new Query(QueryType.DELETE, "`tribes`").where("id", WhereType.EQUALS, Integer.toString(t.getId()));
                h.query();
                h.close();

                h = new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", "0"), new Set("role", "''")).where("tribe", WhereType.EQUALS, Integer.toString(t.getId()));
                h.query();
                h.close();

                Message.message(sender, Message.format(Config.destroy, Config.colorOne, Config.colorTwo, t.getName()));

                break;
            case "coins":
                if (args.length != 1) {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, err(), "/t coins");
                    return true;
                }

                if (TribeLoader.getTribe((Player) sender) == null) {
                    Message.message(sender, Config.notInTribe);
                    return true;
                }

                Tribe tg = TribeLoader.getTribe((Player) sender);

                Message.message(sender, Message.format(Config.coins, Config.colorOne, Config.colorTwo, tg.getName(), Integer.toString(tg.getCoins())));

                break;
            case "join":
                if (args.length != 2) {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, err(), "/t join <name>");
                    return true;
                }

                if (Tribe.getTribe((Player) sender) != null) {
                    Message.message(sender, err(), Message.format(Config.inExistingTribe, TribeLoader.getTribe((Player) sender).getName()));
                    return true;
                }

                Tribe tribe = TribeLoader.getTribeFromString(args[1]);

                if (tribe != null) {
                    tribe.join((Player) sender);
                    Database.setPlayerMemberOfTribe((Player) sender, tribe, tribe.getRank((Player) sender));
                } else {
                    Message.message(sender, err(), Config.noExist);
                    return true;
                }

                break;
            case "info":
                if (args.length == 1) {
                    Tribe ta = TribeLoader.getTribe((Player) sender);

                    if (ta != null) {
                        Message.message(sender, Config.header);
                        Message.message(sender, Config.colorOne + "Name: " + Config.colorTwo + ta.getName());
                        Message.message(sender, Config.colorOne + "Claimed land: " + Config.colorTwo + ta.getChunks().size() + "&9 chunks");
                        Message.message(sender, Config.colorOne + "Tribe tier: " + Config.colorTwo + ta.getTier().name());
                        Message.message(sender, Config.colorOne + "Members: " + Config.colorTwo + ta.getMembers().size());
                        Message.message(sender, Config.colorOne + "Coins: " + Config.colorTwo + ta.getCoins());
                        Message.message(sender, Config.colorOne + "Abilities: " + Config.colorTwo + ta.getAbilityString());
                        Message.message(sender, Config.footer);
                    } else {
                        Message.message(sender, "&cYou are not currently in a tribe.");
                    }
                } else if (args.length == 2) {
                    Tribe ta = TribeLoader.getTribeFromStringIgnoreCase(args[1]);

                    if (ta != null) {
                        Message.message(sender, "&9Tribe Information");
                        Message.message(sender, "&9Name: &e" + ta.getName());
                        Message.message(sender, "&9Claimed land: &e" + ta.getChunks().size() + "&9 chunks");
                        Message.message(sender, "&9Tribe tier: &e" + ta.getTier().name());
                        Message.message(sender, "&9Members: &e" + ta.getMembers().size());
                        Message.message(sender, "&9Coins: &e" + ta.getCoins());
                    } else {
                        Message.message(sender, "&cThat tribe doesn't exist.");
                    }
                } else {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, "&c/t info [name]");
                    return true;
                }
                break;
            case "claim":
                if (args.length != 1) {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, err(), "/t claim");
                    return true;
                }

                if (TribeLoader.getTribe((Player) sender) == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                Tribe th = TribeLoader.getTribe((Player) sender);

                if (!(th.getRank((Player) sender).getPower() >= 2)) {
                    Message.message(sender, err(), Config.noPower);
                    return true;
                }

                Chunk x = ((Player)sender).getWorld().getChunkAt(((Player)sender).getLocation());

                // Make sure this chunk isn't already owned
                if (th.getChunks().contains(((Player) sender).getWorld().getChunkAt(((Player) sender).getLocation()))) {
                    Message.message(sender, err(), Config.alreadyOwned);
                    return true;
                }

                for (List<Chunk> chonk : TribeLoader.ownedChunks.values()) {
                    if(chonk.contains(x)) {
                        Message.message(sender, err(), Config.ownedByOtherTribe);
                        return true;
                    }
                }

                if(th.getChunks().size() >= TribeLoader.getAllowedChunks(th)) {
                    Message.message(sender, err(), Config.noMoreLand);
                    return true;
                }

                th.addChunk(x).push();

                break;
            case "list":
                int page;
                if(args.length == 1)
                    page = 1;
                else {
                    if(args[1] == "0") {
                        Message.message(sender, err(), Config.nonZero);
                        return true;
                    }
                    page = Integer.parseInt(args[1]);
                }

                int totalPages = (int)Math.ceil(TribeLoader.tribesList.size()/6.0d);

                if(page > totalPages) {
                    Message.message(sender, err(), Config.tooManyPages);
                    return true;
                }

                Message.message(sender, Message.format(Config.listTribes, Config.colorOne, Config.colorTwo, Integer.toString(page), Integer.toString(totalPages)));

                for(int i = (6 * (page - 1)); i < (6 * page); i++) {
                    if(i >= TribeLoader.tribesList.size())
                        break;
                    Message.message(sender, Config.colorOne, TribeLoader.tribesList.get(i).getName(), Config.colorTwo);
                }

                return true;
            case "reload":
                tribes.reloadCfg();
                Message.message(sender, Config.colorOne, "Tribes reloaded.");
                return true;
            case "upgrade":
                Tribe playerTribe = TribeLoader.getTribe((Player)sender);

                if(playerTribe == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                int coins = playerTribe.getCoins();
                TribeTier tier = playerTribe.getTier();

                switch(tier.getValue()) {
                    case 1:
                        if(coins < 2) {
                            Message.message(sender, err(), Message.format(Config.moreCoins, Integer.toString(2), Integer.toString(2 - coins), Integer.toString(coins)));
                            return true;
                        }

                        playerTribe.subtractCoins(2).push();
                        playerTribe.setTier(TribeTier.TIER_TWO).push();
                        break;
                    case 2:
                        if(coins < 4) {
                            Message.message(sender, err(), Message.format(Config.moreCoins, Integer.toString(4), Integer.toString(4 - coins), Integer.toString(coins)));
                            return true;
                        }

                        playerTribe.subtractCoins(4).push();
                        playerTribe.setTier(TribeTier.TIER_THREE).push();
                        break;
                    case 3:
                        if(coins < 6) {
                            Message.message(sender, err(), Message.format(Config.moreCoins, Integer.toString(6), Integer.toString(6 - coins), Integer.toString(coins)));
                            return true;
                        }

                        playerTribe.subtractCoins(6).push();
                        playerTribe.setTier(TribeTier.TIER_FOUR).push();
                        break;
                    case 4:
                        if(coins < 8) {
                            Message.message(sender, err(), Message.format(Config.moreCoins, Integer.toString(8), Integer.toString(8 - coins), Integer.toString(coins)));
                            return true;
                        }

                        playerTribe.subtractCoins(8).push();
                        playerTribe.setTier(TribeTier.TIER_FIVE).push();
                        break;
                    case 5:
                        Message.message(sender, err(), Config.fullyUpgraded);
                        break;
                }
                return true;
            case "home":
                Tribe tribee = TribeLoader.getTribe((Player)sender);

                if(tribee == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                if(tribee.getHome() == null) {

                }

                ((Player)sender).teleport(tribee.getHome());

                Message.message(sender, Message.format(Config.teleportHome, Config.colorOne, Config.colorTwo));
                return true;
            case "sethome":
                Tribe triber = TribeLoader.getTribe((Player)sender);

                if(triber == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                if(triber.getMembers().get(sender).getPower() <= 2) {
                    Message.message(sender, err(), Config.needMorePower);
                    return true;
                }

                triber.setHome(((Player)sender).getLocation());
                Message.message(sender, Message.format(Config.setHome, Config.colorOne, Config.colorTwo, triber.getName()));
                return true;
            case "getcoins":
                if(args.length != 2) {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, err(), "/t getcoins <amount>");
                    return true;
                }

                int i;
                try {
                    i = Integer.parseInt(args[1]);
                } catch(Exception e) {
                    Message.message(sender, err(), Config.invalidSubargs);
                    Message.message(sender, err(), "/t getcoins <amount>");
                    return true;
                }

                if(i < 1) {
                    Message.message(sender, err(), Config.oneRequired);
                    return true;
                }

                Tribe tribeh = TribeLoader.getTribe((Player)sender);

                if(tribeh == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                if(!Tribes.getEcon().has((Player)sender, Config.coinPrice * i)) {
                    Message.message(sender, err(), Message.format(Config.lowBalance, Config.colorOne, Config.colorTwo, Integer.toString(Config.coinPrice * i), Double.toString(((Config.coinPrice * i) - Tribes.getEcon().getBalance((Player) sender))), Double.toString(Tribes.getEcon().getBalance((Player) sender))));
                    return true;
                }

                if(!Tribes.getEcon().withdrawPlayer((Player)sender, Config.coinPrice * i).transactionSuccess()) {
                    Message.message(sender, err(), Config.transactionError);
                    return true;
                } else {
                    tribeh.addCoins(i).push();
                    Message.message(sender, Message.format(Config.buyCoins, Config.colorOne, Config.colorTwo, Integer.toString(i), Integer.toString(Config.coinPrice * i), Double.toString(Tribes.getEcon().getBalance((Player)sender)), tribeh.getName(), Integer.toString(tribeh.getCoins())));
                    return true;
                }
            case "leave":
                Tribe tribel = TribeLoader.getTribe((Player)sender);

                if(tribel == null) {
                    Message.message(sender, err(), Config.notInTribe);
                    return true;
                }

                if(tribel.getRank((Player)sender).getName().equals("CHIEF")) {
                    Message.message(sender, err(), Config.chiefLeave);
                    return true;
                }

                tribel.removeMember((Player)sender);
                Database.setPlayerMemberOfNoTribe((Player) sender);

                Message.message(sender, Message.format(Config.leave, Config.colorOne, Config.colorTwo, tribel.getName()));

                return true;
            default:
                Message.messageInvalidArgs(sender, this.getClass());
                return true;
        }
        return true;
    }

    private static String err() {
        return "&" + Config.errorColor;
    }

}
