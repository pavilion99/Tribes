package tech.spencercolton.tribes;

import lombok.Getter;
import tech.spencercolton.tribes.Commands.TribesCmd;
import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Hooks.VaultHook;
import tech.spencercolton.tribes.Listeners.*;
import tech.spencercolton.tribes.Schedules.PushTribesSchedule;
import tech.spencercolton.tribes.Storage.SQLite.Database;
import tech.spencercolton.tribes.Util.TribeLoader;
import tech.spencercolton.tribes.Util.Command.CommandLoader;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Message;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tribes extends JavaPlugin {

    @Getter
    private static Database db;

    @Getter
    private static File dataDir;

    @Getter
    private static Economy econ;

    private static Logger log;

    @Getter
    private static Tribes instance;

    @Override
    public void onEnable() {
        instance = this;
        log = this.getLogger();

        new Config(this).init();
        new Config.Prices().init();

        dataDir = getDataFolder();

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
        TribeLoader.tribesList.forEach(Tribe::pushSync);
    }

    private void registerListeners() {
        getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getPluginManager().registerEvents(new TribeEarnCoinsListener(), this);
        getPluginManager().registerEvents(new TribeInvitePlayerListener(), this);
        getPluginManager().registerEvents(new TribeKickPlayerListener(), this);
        if(Config.chatFeatures)
            getPluginManager().registerEvents(new PlayerChatListener(), this);
        getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    private void registerCommands() {
        CommandLoader.init();
        Message.init();

        CommandLoader.cmds.stream().forEach(c -> c.setExecutor(new TribesCmd()));
        for(PluginCommand c : CommandLoader.cmds)
            c.setExecutor(new TribesCmd());
    }

    private void loadSchedules() {
        new PushTribesSchedule().runTaskTimerAsynchronously(this, 0, Config.saveFrequency * 60 * 20);
        getLogger().info("Saving to database automatically every " + Config.saveFrequency + " minutes.");
    }

    private void load() {
        saveDefaultConfig();
        Bukkit.getOnlinePlayers().forEach(Database::loadPlayer);
    }

    public PluginManager getPluginManager() {
        return getServer().getPluginManager();
    }

    public FileConfiguration getBukkitCfg() {
        return getConfig();
    }

    public static void log(Level l, String s) {
        log.log(l, s);
    }

    public static void call(Event e) {
        Bukkit.getPluginManager().callEvent(e);
    }

    public void reloadCfg() {
        reloadConfig();
        new Config(this).init();
        new Config.Prices().init();
    }
}
