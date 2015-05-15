package co.valdeon.Tribes;

import co.valdeon.Tribes.commands.TribesCmd;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.listeners.PlayerJoinListener;
import co.valdeon.Tribes.storage.*;
import co.valdeon.Tribes.util.TribeLoader;
import co.valdeon.Tribes.util.command.CommandLoader;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

public class Tribes extends JavaPlugin {

    private Config config;
    private static Database db;
    private static File dDir;

    @Override
    public void onEnable() {
        this.config = new Config(this);
        dDir = getDataFolder();

        registerCommands();
        registerListeners();

        if(!Database.verifyDBConnection())
            getPluginManager().disablePlugin(this);
        db = new Database();

        TribeLoader.load(this);

        load();
    }

    @Override
    public void onDisable() {

    }

    public void registerListeners() {
        getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    public void registerCommands() {
        CommandLoader.init(this);
        Message.init();

        for(PluginCommand c : CommandLoader.cmds) {
            c.setExecutor(new TribesCmd());
        }
    }

    public void load() {
        saveDefaultConfig();

        for(Player p : Bukkit.getOnlinePlayers()) {
            loadPlayer(p);
        }
    }

    public PluginManager getPluginManager() {
        return getServer().getPluginManager();
    }

    public Config getCfg() {
        return this.config;
    }

    public FileConfiguration getBukkitCfg() {
        return getConfig();
    }

    public static void log(Level l, String s) {
        Bukkit.getLogger().log(l, s);
    }

    public static Connection getCon() {
        return db.getConnection();
    }

    public static Database getDB() {
        return db;
    }

    public static class Players {

        private static HashMap<Player, HashMap<String, Object>> playerData = new HashMap<>();

        public static Object get(Player p, String s) {
            return playerData.get(p).get(s);
        }

        public static void put(Player p, String s, Object t) {
            if(!containsKey(p))
                playerData.put(p, new HashMap<String, Object>());
            playerData.get(p).put(s, t);
        }

        public static void remove(Player p) {
            playerData.remove(p);
        }

        public static boolean containsKey(Player player) {
            return playerData.containsKey(player);
        }

    }

    public static File getDataDir() {
        return dDir;
    }



    public static void loadPlayer(Player p) {
        ResultSet rs = new Query(QueryType.SELECT, "*", "`users`").query();

        try {
            while (rs.next()) {

                String dbUUID = rs.getString("uuid");
                Tribes.log(Level.INFO, dbUUID);
                String playerUUID = p.getUniqueId().toString();
                if(dbUUID.equalsIgnoreCase(playerUUID)) {

                    if(!(rs.getString("name").equalsIgnoreCase(p.getName())))
                        new Query(QueryType.UPDATE, "`users`").set(new Set("name", p.getName())).where("`uuid`", WhereType.EQUALS, p.getUniqueId().toString()).limit(1).query();

                    Tribe t = null;

                    for(Tribe x : TribeLoader.tribesList) {
                        if(x.getId() == rs.getInt("tribe"))
                            t = x;
                    }

                    Tribes.Players.put(p, "tribe", t);
                    if(t != null)
                        Tribes.Players.put(p, "tribeRank", t.getMembers().get(p));
                    else
                        Tribes.Players.put(p, "tribeRank", null);
                    Tribes.Players.put(p, "id", rs.getInt("id"));

                    Tribes.log(Level.INFO, "Loaded player " + p.getName() + " with UUID " + p.getUniqueId().toString());

                    return;
                }

            }

            Query q = new Query(QueryType.INSERTINTO, "`users`").columns("uuid", "name").values("'" + p.getUniqueId().toString() + "'", "'" + p.getName() + "'");
            ResultSet r = q.query(true);

            Tribes.Players.put(p, "id", r.getInt(1));

            r.close();
            rs.close();
            q.close();
        }catch(SQLException ex) {
            ex.printStackTrace();
            Tribes.log(Level.SEVERE, "SQL failed!");
        }
    }

}
