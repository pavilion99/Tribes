package co.valdeon.Tribes.components;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.storage.Set;
import co.valdeon.Tribes.storage.WhereType;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

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

    public Tribe(String name, int id, TribeTier tier, HashMap<OfflinePlayer, TribeRank> members, List<Chunk> ownedChunks) {
        this.name = name;
        this.tier = tier;
        this.members = members;
        this.ownedChunks = ownedChunks;
        this.id = id;
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
        if(getId() != 0) {
            new Query(QueryType.UPDATE, "`tribes`").set(new Set("chunks", "'" + getChunkString() + "'"), new Set("tier", "'" + getTier().tierString + "'")).where("id", WhereType.EQUALS, Integer.toString(getId())).limit(1).query();
            return this;
        }else {

            ResultSet z = new Query(QueryType.INSERTINTO, "`tribes`").columns("name", "chunks", "tier").values("'" + this.name + "'", "'" + this.getChunkString() + "'", "'" + this.getTier().tierString + "'").query(true);

            try {
                if (z.next()) {
                    this.id = z.getInt(1);
                    z.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return this;
            }

        }

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

}
