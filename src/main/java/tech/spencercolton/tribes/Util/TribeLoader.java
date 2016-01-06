package tech.spencercolton.tribes.Util;

import lombok.Getter;
import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Storage.SQLite.Database;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeRank;
import tech.spencercolton.tribes.Components.TribeTier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class TribeLoader {

    public static final List<Tribe> tribesList = new ArrayList<>();
    public static final HashMap<Tribe, List<Chunk>> ownedChunks = new HashMap<>();

    @Getter
    private static Tribes tribes;

    public static void load(Tribes tribe) {
        tribes = tribe;

        HashMap<String, HashMap<String, Object>> listTribes = Database.getTribes();

        int g = 0;

        if(listTribes == null)
            return;

        for(String s : listTribes.keySet()) {
            HashMap<String, Object> data = listTribes.get(s);

            String name = (String)data.get("name");
            int id = (int)data.get("id");
            HashMap<OfflinePlayer, TribeRank> members = (HashMap<OfflinePlayer, TribeRank>)data.get("members");
            List<Chunk> chunks = (List<Chunk>)data.get("chunks");
            int coins = (int)data.get("coins");
            ArrayList<OfflinePlayer> invitees = (ArrayList<OfflinePlayer>)data.get("invitees");
            TribeTier tier = (TribeTier)data.get("tier");
            List<AbilityType> abilities = (ArrayList<AbilityType>)data.get("abilities");
            Location home = (Location)data.get("home");

            Tribe t = new Tribe(name, id, members, chunks, coins, invitees, tier, abilities, home);

            tribesList.add(t);
            ownedChunks.put(t, chunks);

            g++;
        }

        tribe.getLogger().info("Registered " + Integer.toString(g) + " tribes.");
    }

    public static boolean tribeExists(String s) {
        for(Tribe t : tribesList)
            if(t.getName().equalsIgnoreCase(s))
                return true;
        return false;
    }

    public static Tribe getTribe(OfflinePlayer p) {
        for(Tribe t : tribesList)
            if(t.getMembers().containsKey(p))
                return t;
        return null;
    }

    public static Tribe getTribeFromString(String s) {
        for(Tribe x : tribesList)
            if(x.getName().equals(s))
                return x;
        return null;
    }

    public static Tribe getTribeFromStringIgnoreCase(String s) {
        for(Tribe x : tribesList)
            if(x.getName().equalsIgnoreCase(s))
                return x;
        return null;
    }

    public static Tribe getTribeFromId(int i) {
        for(Tribe x : tribesList)
            if(x.getId() == i)
                return x;
        return null;
    }

    public static Tribe getChunkOwner(Chunk c) {
        for(Tribe tribe : ownedChunks.keySet()) {
            if(ownedChunks.get(tribe).contains(c)) {
                return tribe;
            }
        }
        return null;
    }
}
