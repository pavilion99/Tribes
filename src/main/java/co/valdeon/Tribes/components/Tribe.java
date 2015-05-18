package co.valdeon.Tribes.components;

import co.valdeon.Tribes.storage.*;
import co.valdeon.Tribes.util.BiMap;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tribe {

    private int id;
    private String name;
    private TribeTier tier;
    private HashMap<OfflinePlayer, TribeRank> members = new HashMap<>();
    private List<Chunk> ownedChunks = new ArrayList<>();
    private int coins;
    private OfflinePlayer chief = null;
    private List<OfflinePlayer> officers = new ArrayList<>();
    private List<OfflinePlayer> invitees = new ArrayList<>();

    public Tribe(String name, OfflinePlayer creator) {
        this.name = name;
        this.tier = TribeTier.TIER_ONE;
        this.members.put(creator, TribeRank.CHIEF);
        this.ownedChunks.add(creator.getPlayer().getLocation().getChunk());
    }

    public Tribe(String name, int id, HashMap<OfflinePlayer, TribeRank> members, List<Chunk> ownedChunks, int coins, List<OfflinePlayer> invitees) {
        this.name = name;
        this.tier = TribeTier.getTierFromCoins(coins);
        this.members = members;
        this.ownedChunks = ownedChunks;
        this.id = id;
        this.coins = coins;
        for(OfflinePlayer p : members.keySet()) {
            if(members.get(p) == TribeRank.CHIEF) {
                this.chief = p;
            }else if(members.get(p) == TribeRank.OFFICER) {
                this.officers.add(p);
            }
        }
        this.invitees = invitees;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public TribeTier getTier() {
        return this.tier;
    }

    public HashMap<OfflinePlayer, TribeRank> getMembers() {
        return this.members;
    }

    public boolean addMember(OfflinePlayer p) {
        if(!this.members.keySet().contains(p)) {
            this.members.put(p, TribeRank.MEMBER);
            return true;
        }
        return false;
    }

    public boolean addMember(String s) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(s);
        if(!(this.members.containsKey(p))) {
            this.members.put(p, TribeRank.MEMBER);
            return true;
        }
        return false;
    }

    public static Tribe getTribe(OfflinePlayer p) {
        return TribeLoader.getTribe(p);
    }

    public Tribe push() {
        this.id = Database.pushTribe(this);
        return this;
    }

    public String getChunkString() {
        Chunk[] x = ownedChunks.toArray(new Chunk[1]);
        String fin = "";
        for(int i = 0; i < x.length; i++) {
            fin += x[i].getX() + "," + x[i].getZ();
            if(!((i + 1) >= x.length))
                fin += ";";
        }
        return fin;
    }

    public int getCoins() {
        return this.coins;
    }

    public void addCoins(int a) {
        this.coins += a;
    }

    public void setCoins(int a) {
        this.coins = a;
    }

    public List<OfflinePlayer> getOfficers() {
        return this.officers;
    }

    public boolean isOfficer(OfflinePlayer p) {
        return this.officers.contains(p);
    }

    public OfflinePlayer getChief() {
        return this.chief;
    }

    public TribeRank getRank(OfflinePlayer p) {
        return this.getMembers().get(p);
    }

    public List<OfflinePlayer> getInvitees() {
        return this.invitees;
    }

    public String getInviteeString() {
        OfflinePlayer[] x = invitees.toArray(new OfflinePlayer[1]);
        String fin = "";
        for(int i = 0; i < x.length; i++) {
            if(x[i] != null) {
                fin += x[i].getUniqueId().toString();
                if (!((i + 1) >= x.length))
                    fin += ";";

            }
        }
        return fin;
    }

    public void invite(OfflinePlayer p) {
        this.invitees.add(p);
        Message.message(p.getPlayer(), "&8You've been invited to join &e" + this.name + "&8!");
        Message.message(p.getPlayer(), "Use [/t join " + this.name + "] to join");
    }

    public void join(OfflinePlayer p){
        this.members.put(p, TribeRank.MEMBER);
        Message.message(p.getPlayer(), "&8You have successfully joined &e" + this.name + "&8.");
    }

    public void join(OfflinePlayer p, TribeRank t){
        this.members.put(p, t);
    }

}
