package co.valdeon.Tribes.components;

import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tribe {

    private int id;
    private String name;
    private TribeTier tier;
    private HashMap<OfflinePlayer, TribeRank> members = new HashMap<>();
    private List<Chunk> ownedChunks = new ArrayList<>();

    public Tribe(String name, OfflinePlayer creator) {
        this.name = name;
        this.tier = TribeTier.TIER_ONE;
        this.members.put(creator, TribeRank.CHIEF);
        this.ownedChunks.add(creator.getPlayer().getLocation().getChunk());

    }

    public Tribe make(String name, TribeTier tier) {
        return this;
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

}
