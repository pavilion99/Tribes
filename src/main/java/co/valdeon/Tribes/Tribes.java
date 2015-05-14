package co.valdeon.Tribes;

import co.valdeon.Tribes.commands.TribesCmd;
import co.valdeon.Tribes.storage.Database;
import co.valdeon.Tribes.util.TribeLoader;
import co.valdeon.Tribes.util.command.CommandLoader;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.logging.Level;

public class Tribes extends JavaPlugin {

    private Config config;
    private Database db;

    @Override
    public void onEnable() {
        this.config = new Config(this);

        registerCommands();
        registerListeners();

        Database.verifyDBConnection();

        TribeLoader.load(this);
    }

    @Override
    public void onDisable() {

    }

    public void registerListeners() {

    }

    public void registerCommands() {
        CommandLoader.init(this);
        Message.init();

        for(PluginCommand c : CommandLoader.cmds) {
            c.setExecutor(new TribesCmd());
        }
    }

    public void load() {

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

    public Connection getCon() {
        return this.db.getConnection();
    }

    public Database getDB() {
        return this.db;
    }

}
