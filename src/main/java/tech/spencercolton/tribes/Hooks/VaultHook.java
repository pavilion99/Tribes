package tech.spencercolton.tribes.Hooks;

import tech.spencercolton.tribes.Tribes;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private boolean success = false;
    private static Economy econ;

    public VaultHook(Tribes tribes) {
        if(tribes.getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        RegisteredServiceProvider<Economy> econService = tribes.getServer().getServicesManager().getRegistration(Economy.class);
        if(econService == null) {
            return;
        }

        Economy econo = econService.getProvider();
        if(econo == null) {
            return;
        }

        econ = econo;

        this.success = true;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public static Economy getEcon() {
        return econ;
    }

}
