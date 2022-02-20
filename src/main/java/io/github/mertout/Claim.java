package io.github.mertout;

import io.github.mertout.commands.TabComplete;
import io.github.mertout.commands.XClaim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.core.timer.MoveTimer;
import io.github.mertout.filemanager.ClaimsFile;
import io.github.mertout.filemanager.MessagesFile;
import io.github.mertout.holograms.HologramCore;
import io.github.mertout.holograms.timer.HologramTimer;
import io.github.mertout.listeners.*;
import io.github.mertout.placeholders.Placeholders;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Claim extends JavaPlugin {

    public static Claim instance;
    public final ArrayList<DataHandler> claims = new ArrayList<>();
    public final ArrayList<Player> memberadd = new ArrayList<>();
    private Economy economy = null;
    private ClaimManager cm;
    private MoveTimer mt;
    private HologramCore hc;

    @Override
    public void onEnable() {
        instance = this;
        loadClass();
        loadEvents();
        loadFiles();
        checkPlugins();
        getClaimManager().loadClaims();
    }
    public void loadClass() {
        cm = new ClaimManager();
        mt = new MoveTimer();
        new HologramTimer();
        hc = new HologramCore();
    }
    @Override
    public void onDisable() {
        getClaimManager().saveClaims();
    }
    private void checkPlugins() {
        if (!setupEconomy()) {
            System.out.println("Vault not found, shutting down server.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") == null) {
            System.out.println("Holographic Displays not found, shutting down server.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new Placeholders().register();
    }
    private void loadFiles() {
        saveDefaultConfig();
        ClaimsFile.loadClaimFiles();
        MessagesFile.loadMessagesFiles();
    }
    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(),this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(),this);
        getServer().getPluginManager().registerEvents(new BlockClickListener(),this);
        getServer().getPluginManager().registerEvents(new ClickEvent(),this);
        getServer().getPluginManager().registerEvents(new ChatEvent(),this);
        getServer().getPluginManager().registerEvents(new CommandEvent(),this);
        getServer().getPluginManager().registerEvents(new DamageEvent(),this);
        getCommand("xclaim").setExecutor(new XClaim());
        getCommand("xclaim").setTabCompleter(new TabComplete());
    }
    public static Claim getInstance() {
        return instance;
    }
    public Economy getEconomy() {
        return economy;
    }

    public ArrayList<DataHandler> getClaims() {

        return claims;
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        economy = (Economy)rsp.getProvider();
        return (economy != null);
    }

    public ClaimManager getClaimManager() {
        return cm;
    }

    public MoveTimer getMoveTimer() {
        return mt;
    }

    public HologramCore getHologramCore() {
        return hc;
    }
    public ArrayList<Player> getMembers() {
        return memberadd;
}

}
