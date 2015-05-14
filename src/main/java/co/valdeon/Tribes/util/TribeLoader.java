package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.storage.Query;
import co.valdeon.Tribes.storage.QueryType;
import co.valdeon.Tribes.components.Tribe;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TribeLoader {

    public static List<Tribe> tribesList = new ArrayList<>();
    public static HashMap<Chunk, Tribe> ownedChunks = new HashMap<>();

    public static void load(Tribes tribes) {
        ResultSet r = new Query(QueryType.SELECT, tribes.getDB(), "*", "'tribes'").query();

        try {
            while(r.next()) {
                Tribe t = new Tribe(r.getString("name"), )
            }
        }catch(SQLException e) {
            tribes.getLogger().severe("Couldn't load tribes from the database!");
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

}
