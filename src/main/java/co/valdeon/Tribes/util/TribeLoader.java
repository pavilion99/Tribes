package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.*;
import co.valdeon.Tribes.storage.Database;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TribeLoader {

    public static List<Tribe> tribesList = new ArrayList<>();
    public static HashMap<Tribe, List<Chunk>> ownedChunks = new HashMap<>();

    public static void load(Tribes tribes) {
        HashMap<String, HashMap<String, Object>> listTribes = Database.getTribes();

        int g = 0;

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

        tribes.getLogger().info("Registered " + Integer.toString(g) + " tribes.");
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

    public static int getAllowedChunks(Tribe t) {
        switch(t.getTier().getValue()) {
            case 1:
                return Config.tierOneChunks;
            case 2:
                return Config.tierTwoChunks;
            case 3:
                return Config.tierThreeChunks;
            case 4:
                return Config.tierFourChunks;
            case 5:
                return Config.tierFiveChunks;
            default:
                return Config.tierOneChunks;
        }
    }
}
