package co.valdeon.Tribes.storage;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.components.TribeRank;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
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
            return DriverManager.getConnection("jdbc:sqlite:" + Tribes.getDataDir().toString() + "\\" +Config.dbName + ".db");
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

                HashMap<String, Object> internal = new HashMap<>();

                internal.put("name", name);
                internal.put("id", id);
                internal.put("chunks", chunks);
                internal.put("invitees", invitees);
                internal.put("coins", coins);
                internal.put("members", members);

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
        String[] chenks = s.split(";");
        for(String ss : chenks) {
            String[] chonks = ss.split(",");
            chunks.add(Bukkit.getWorld(Config.world).getChunkAt(Integer.parseInt(chonks[0]), Integer.parseInt(chonks[1])));
        }
        return chunks;
    }

    private static List<OfflinePlayer> getInviteesFromString(String s) {
        List<OfflinePlayer> invitees = new ArrayList<>();
        if(!s.equals("")) {
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

        if(id == 0) {
            Query q = new Query(QueryType.INSERTINTO, "`tribes`").columns("name", "coins", "invitees", "chunks").values("'" + name + "'", Integer.toString(coins), "'" + invitees + "'", "'" + chunks + "'");
            ResultSet r = q.query(true);

            try {
                if(r.next()) {
                    return r.getInt(1);
                } else {
                    // Shouldn't happen
                    return r.getInt(0);
                }
            } catch(SQLException e) {
                Tribes.log(Level.SEVERE, "Failed to push tribe " + name + " to the database.");
                e.printStackTrace();
                return 0;
            }
        } else {
            Query q = new Query(QueryType.UPDATE, "`tribes`").set(new Set("chunks", "'" + chunks + "'"), new Set("invitees", "'" + invitees + "'"), new Set("name", "'" + name + "'"), new Set("coins", Integer.toString(coins))).where("id", WhereType.EQUALS, Integer.toString(id)).limit(1);
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

                String role = r.getString("role");

                int id = r.getInt("id");

                if(!dbName.equals(playerName))
                    new Query(QueryType.UPDATE, "`users`").set(new Set("name", "'" + playerName + "'")).where("uuid", WhereType.EQUALS, "'" + uuid + "'").query(true).close();

                Tribe t = TribeLoader.getTribeFromId(tribeId);

                Tribes.Players.put(p, "tribe", t);
                Tribes.Players.put(p, "tribeRank", role);
                Tribes.Players.put(p, "id", id);
            } else {
                Query qa = new Query(QueryType.INSERTINTO, "`users`").columns("uuid", "name").values("'" + uuid + "'", "'" + playerName + "'");
                ResultSet ra = qa.query(true);

                int id = 0;

                try {
                    if(ra.next())
                        id = ra.getInt(1);
                } catch(NullPointerException e) {
                    Tribes.log(Level.WARNING, "Failed to insert player with uuid " + uuid + " and name " + playerName + " into the database");
                    e.printStackTrace();
                }

                Tribes.Players.put(p, "id", id);

                ra.close();
                qa.close();
            }

            r.close();
            q.close();
        } catch(SQLException e) {
            Tribes.log(Level.SEVERE, "Failed to load player with UUID " + p.getUniqueId().toString() + " and name " + p.getName() + " from the database.");
        }
    }

}
