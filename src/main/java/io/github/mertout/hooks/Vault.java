package io.github.mertout.hooks;

import io.github.mertout.Claim;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private Economy economy;

    public boolean setupEconomy() {
        if (Claim.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            Claim.getInstance().getLogger().warning("Vault not found, Please install Vault!");
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Claim.getInstance().getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return this.economy != null;
    }

    public Economy getEconomy() {
        return economy;
    }
}
