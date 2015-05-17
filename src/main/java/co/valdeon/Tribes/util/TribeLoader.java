package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.TribeRank;
import co.valdeon.Tribes.components.TribeTier;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.storage.WhereType;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class TribeLoader {

    public static List<Tribe> tribesList = new ArrayList<>();
    public static HashMap<Tribe, List<Chunk>> ownedChunks = new HashMap<>();

    public static void load(Tribes tribes) {
        ResultSet r = new Query(QueryType.SELECT, "*", "`tribes`").query();

        try {
            int g = 0;
            while(r.next()) {
                Query q = new Query(QueryType.SELECT, "*", "`users`").where("`tribe`", WhereType.EQUALS, Integer.toString(r.getInt("id")));
                ResultSet mem = q.query();

                HashMap<OfflinePlayer, TribeRank> members = new HashMap<>();
                while(mem.next()) {
                    members.put(Bukkit.getOfflinePlayer(UUID.fromString(mem.getString("uuid"))), TribeRank.getRank(mem.getString("role")));
                }

                List<Chunk> chunks = new ArrayList<>();
                String chanks = r.getString("chunks");
                String[] chenks = chanks.split(";");
                for(String s : chenks) {
                    String[] chonks = s.split(",");
                    chunks.add(Bukkit.getWorld(Config.world).getChunkAt(Integer.parseInt(chonks[0]), Integer.parseInt(chonks[1])));
                }

                List<OfflinePlayer> invitees = new ArrayList<>();
                String invitiis = r.getString("invitees");
                String[] initaas = invitiis.split(";");
                for(String s : initaas) {
                    invitees.add(Bukkit.getOfflinePlayer(UUID.fromString(s)));
                }

                Tribe t = new Tribe(r.getString("name"), r.getInt("id"), TribeTier.getTier(r.getString("tier")), members, chunks, r.getInt("coins"), invitees);

                tribesList.add(t);

                ownedChunks.put(t, chunks);

                q.close();

                g++;
            }

            tribes.getLogger().info("Registered " + Integer.toString(g) + " tribes.");
        }catch(SQLException e) {
            tribes.getLogger().severe("Couldn't load tribes from the database!");
        }catch(NullPointerException e) {
            tribes.getLogger().info("Registered 0 tribes.");
        }
    }

    public static boolean tribeExists(String s) {
        for(Tribe t : tribesList)
            if(t.getName().equalsIgnoreCase(s))
                return true;
        return false;
    }

    public static Tribe getTribe(OfflinePlayer p) {
        for(Tribe t : tribesList) {
            if(t.getMembers().containsKey(p))
                return t;
        }
        return null;
    }

    public static Tribe getTribeFromString(String s) {
        for(Tribe x : tribesList) {
            if(x.getName().equals(s)) {
                return x;
            }
        }
        return null;
    }

}
