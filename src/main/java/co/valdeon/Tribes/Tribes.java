package co.valdeon.Tribes;

import co.valdeon.Tribes.commands.TribesCmd;
import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.events.TribeEarnCoinsEvent;
import co.valdeon.Tribes.hooks.VaultHook;
import co.valdeon.Tribes.listeners.*;
import co.valdeon.Tribes.schedules.PushTribesSchedule;
import co.valdeon.Tribes.storage.*;
import co.valdeon.Tribes.util.TribeLoader;
import co.valdeon.Tribes.util.command.CommandLoader;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class Tribes extends JavaPlugin {

    private static Database db;
    private static File dDir;
    private static Economy econ;
    private static List<BukkitTask> tasks = new ArrayList<>();

    @Override
    public void onEnable() {
        new Config(this).init();
        new Config.Prices().init();

        dDir = getDataFolder();

        if(!Database.verifyDBConnection())
            getPluginManager().disablePlugin(this);
        db = new Database();

        TribeLoader.load(this);

        if(new VaultHook(this).getSuccess()) {
            econ = VaultHook.getEcon();
            getLogger().log(Level.INFO, "Tribes successfully connected to Vault.");
        }

        registerCommands();
        registerListeners();
        loadSchedules();

        load();
    }

    @Override
    public void onDisable() {
        for(Tribe t : TribeLoader.tribesList) {
            t.push();
        }
    }

    public void registerListeners() {
        getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getPluginManager().registerEvents(new TribeEarnCoinsListener(), this);
        getPluginManager().registerEvents(new TribeInvitePlayerListener(), this);
        getPluginManager().registerEvents(new TribeKickPlayerListener(), this);
        if(Config.chatFeatures) {
            getPluginManager().registerEvents(new PlayerChatListener(), this);
        }
        getPluginManager().registerEvents(new PlayerMoveListener(this), this);
    }

    public void registerCommands() {
        CommandLoader.init(this);
        Message.init();

        for(PluginCommand c : CommandLoader.cmds) {
            c.setExecutor(new TribesCmd(this));
        }
    }

    public void loadSchedules() {
        tasks.add(new PushTribesSchedule().runTaskTimerAsynchronously(this, 0, Config.saveFrequency * 60 * 20));
        getLogger().info("Saving to database automatically every " + Config.saveFrequency + " minutes.");
    }

    public void load() {
        saveDefaultConfig();

        for(Player p : Bukkit.getOnlinePlayers()) {
            Database.loadPlayer(p);
        }
    }

    public PluginManager getPluginManager() {
        return getServer().getPluginManager();
    }

    public FileConfiguration getBukkitCfg() {
        return getConfig();
    }

    public static void log(Level l, String s) {
        Bukkit.getLogger().log(l, s);
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

    public static void call(Event e) {
        Bukkit.getPluginManager().callEvent(e);
    }

    public void reloadCfg() {
        reloadConfig();
        new Config(this).init();
        new Config.Prices().init();
    }

    public static Economy getEcon() {
        return econ;
    }

}
