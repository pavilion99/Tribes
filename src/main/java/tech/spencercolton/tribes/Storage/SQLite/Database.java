package tech.spencercolton.tribes.Storage.SQLite;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Components.AbilityType;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeRank;
import tech.spencercolton.tribes.Components.TribeTier;
import tech.spencercolton.tribes.Schedules.PushPlayer;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Database {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + Tribes.getDataDir().toString() + "\\" + Config.dbName + ".db");
        }catch(SQLException e) {
            return null;
        }
    }

    public static boolean verifyDBConnection() {
        try {
            DriverManager.getConnection("jdbc:sqlite:" + Tribes.getDataDir().toString() + "\\" + Config.dbName + ".db");
            Tribes.log(Level.INFO, "Connected to database at jdbc:sqlite:" + Tribes.getDataDir().toString() + "\\" + Config.dbName + ".db");
        }catch(SQLException e) {
            Tribes.log(Level.SEVERE, "Failed to connect to database!");
            return false;
        }

        return true;
    }

    public static HashMap<String, HashMap<String, Object>> getTribes() {
        Query q = new Query(QueryType.SELECT, "*", "`tribes`");
        ResultSet r = q.query();

        HashMap<String, HashMap<String, Object>> tribes = new HashMap<>();

        try {
            while(r.next()) {
                String name = r.getString("name");
                int id = r.getInt("id");
                List<Chunk> chunks = getChunksFromString(r.getString("chunks"));
                List<OfflinePlayer> invitees = getInviteesFromString(r.getString("invitees"));
                int coins = r.getInt("coins");
                HashMap<OfflinePlayer, TribeRank> members = getTribeMembers(id);
                TribeTier tier = TribeTier.getTier(r.getString("tier"));
                List<AbilityType> abilities = getAbilitiesFromString(r.getString("abilities"));
                Location home = getHomeFromString(r.getString("home"));

                HashMap<String, Object> internal = new HashMap<>();

                internal.put("name", name);
                internal.put("id", id);
                internal.put("chunks", chunks);
                internal.put("invitees", invitees);
                internal.put("coins", coins);
                internal.put("members", members);
                internal.put("tier", tier);
                internal.put("abilities", abilities);
                internal.put("home", home);

                tribes.put(name, internal);
            }

            r.close();
            q.close();

            return tribes;
        } catch(SQLException e) {
            q.close();
            Tribes.log(Level.SEVERE, "Failed to retrieve tribes from the database!");
            e.printStackTrace();
            return null;
        }
    }

    private static List<Chunk> getChunksFromString(String s) {
        List<Chunk> chunks = new ArrayList<>();
        if(s == null || s.equals(""))
            return chunks;
        String[] chenks = s.split(";");
        for(String ss : chenks) {
            String[] chonks = ss.split(",");
            chunks.add(Bukkit.getWorld(Config.world).getChunkAt(Integer.parseInt(chonks[0]), Integer.parseInt(chonks[1])));
        }
        return chunks;
    }

    private static List<OfflinePlayer> getInviteesFromString(String s) {
        List<OfflinePlayer> invitees = new ArrayList<>();
        if(s != null && !s.equals("")) {
            String[] initaas = s.split(";");
            for (String ss : initaas) {
                if(!ss.equals("")) {
                    invitees.add(Bukkit.getOfflinePlayer(UUID.fromString(s)));
                }
            }
        }
        return invitees;
    }

    private static HashMap<OfflinePlayer, TribeRank> getTribeMembers(int tribe) {
        Query q = new Query(QueryType.SELECT, "*", "`users`").where("tribe", WhereType.EQUALS, Integer.toString(tribe));
        ResultSet r = q.query();

        HashMap<OfflinePlayer, TribeRank> members = new HashMap<>();

        try {
            while(r.next()) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(r.getString("uuid")));
                TribeRank t = TribeRank.getRank(r.getString("role"));

                members.put(p, t);
            }

            return members;
        } catch(SQLException e) {
            Tribes.log(Level.SEVERE, "Failed to list players in tribe with id " + Integer.toString(tribe));
            e.printStackTrace();
            return null;
        } catch(NullPointerException e) {
            Tribes.log(Level.SEVERE, "Failed to receive tribe ranks for players in tribe " + Integer.toString(tribe));
            e.printStackTrace();
            return null;
        }
    }

    public static int pushTribe(Tribe t) {
        int id = t.getId();
        int coins = t.getCoins();
        String name = t.getName();
        String invitees = t.getInviteeString();
        String chunks = t.getChunkString();
        String tier = t.getTier().tierString;
        String abilities = t.getAbilityString();
        String home = t.getHomeString();

        if(id == 0) {
            Query q = new Query(QueryType.INSERTINTO, "`tribes`").columns("name", "coins", "invitees", "chunks", "tier", "abilities", "home").values("'" + name + "'", Integer.toString(coins), "'" + invitees + "'", "'" + chunks + "'", "'" + tier + "'", "'" + abilities, "'", "'" + home + "'");
            ResultSet r = q.query(true);

            try {
                if(r.next()) {
                    int changed = r.getInt(1);
                    q.close();
                    r.close();
                    return changed;
                } else {
                    // Shouldn't happen
                    q.close();
                    r.close();
                    return 0;
                }
            } catch(SQLException e) {
                Tribes.log(Level.SEVERE, "Failed to push tribe " + name + " to the database.");
                e.printStackTrace();
                return 0;
            }
        } else {
            Query q = new Query(QueryType.UPDATE, "`tribes`").set(new Set("chunks", "'" + chunks + "'"), new Set("invitees", "'" + invitees + "'"), new Set("name", "'" + name + "'"), new Set("coins", Integer.toString(coins)), new Set("tier", "'" + tier + "'"), new Set("abilities", "'" + abilities + "'"), new Set("home", "'" + home + "'")).where("id", WhereType.EQUALS, Integer.toString(id));
            q.query();
            q.close();
            return 0;
        }
    }

    public static void loadPlayer(Player p) {
        UUID u = p.getUniqueId();
        String uuid = u.toString();

        String playerName = p.getName();

        Query q = new Query(QueryType.SELECT, "*", "`users`").where("uuid", WhereType.EQUALS, "'" + uuid + "'");
        ResultSet r = q.query();

        try {
            if(r.next()) {
                String dbName = r.getString("name");

                int tribeId = r.getInt("tribe");

                if(!dbName.equals(playerName))
                    new PushPlayer(new Query(QueryType.UPDATE, "`users`").set(new Set("name", "'" + playerName + "'")).where("uuid", WhereType.EQUALS, "'" + uuid + "'"), true);

                Tribe t = TribeLoader.getTribeFromId(tribeId);
                if(t != null)
                    t.addMember(p);
            } else {
                Query qa = new Query(QueryType.INSERTINTO, "`users`").columns("uuid", "name").values("'" + uuid + "'", "'" + playerName + "'");
                ResultSet ra = qa.query(true);

                try {
                    ra.next();
                } catch(NullPointerException e) {
                    Tribes.log(Level.WARNING, "Failed to insert player with uuid " + uuid + " and name " + playerName + " into the database");
                    e.printStackTrace();
                }

                ra.close();
                qa.close();
            }

            r.close();
            q.close();
        } catch(SQLException e) {
            Tribes.log(Level.SEVERE, "Failed to load player with UUID " + p.getUniqueId().toString() + " and name " + p.getName() + " from the database.");
        }
    }

    public static void setPlayerMemberOfTribe(Player p, Tribe t, TribeRank r) {
        String uuid = p.getUniqueId().toString();
        int id = t.getId();
        String rank = r.getName();

        new PushPlayer(new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", Integer.toString(id)), new Set("role", "'" + rank + "'")).where("uuid", WhereType.EQUALS, "'" + uuid + "'")).runTaskAsynchronously(TribeLoader.getTribes());
    }

    public static void setPlayerMemberOfNoTribe(Player p) {
        String uuid = p.getUniqueId().toString();
        int id = 0;

        new PushPlayer(new Query(QueryType.UPDATE, "`users`").set(new Set("tribe", Integer.toString(id)), new Set("role", "''")).where("uuid", WhereType.EQUALS, "'" + uuid + "'")).runTaskAsynchronously(TribeLoader.getTribes());
    }

    private static List<AbilityType> getAbilitiesFromString(String s) {
        List<AbilityType> abilities = new ArrayList<>();
        if(s == null)
            return abilities;
        String streng[] = s.split(";");
        for(String strang : streng) {
            if(!strang.equals("")) {
                String[] strung = strang.split(":");
                AbilityType type = AbilityType.getAbilityTypeFromString(strung[0]);
                int multiplier = Integer.parseInt(strung[1]);
                if (type != null) {
                    type.setMultiplier(multiplier);
                    abilities.add(type);
                }
            }
        }
        return abilities;
    }

    private static Location getHomeFromString(String s) {
        if(s == null || s.equals(""))
            return null;

        String[] locationParts = s.split(";");

        double x = Double.parseDouble(locationParts[0]);
        double y = Double.parseDouble(locationParts[1]);
        double z = Double.parseDouble(locationParts[2]);

        float yaw = Float.parseFloat(locationParts[3]);
        float pitch = Float.parseFloat(locationParts[4]);

        return new Location(Bukkit.getWorld(Config.world), x, y, z, yaw, pitch);
    }

    public static void setRank(OfflinePlayer p, TribeRank t) {
        new PushPlayer(new Query(QueryType.UPDATE, "`users`").set(new Set("role", "'" + t.getName() + "'")).where("uuid", WhereType.EQUALS, "'" + p.getUniqueId() + "'"));
    }

}
