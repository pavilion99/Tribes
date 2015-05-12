package co.valdeon.Tribes;

import co.valdeon.Tribes.commands.TribesCmd;
import co.valdeon.Tribes.util.CommandLoader;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Tribes extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        this.config = new Config(this);

        registerCommands();
        registerListeners();
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

}
