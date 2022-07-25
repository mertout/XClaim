package io.github.mertout;

import io.github.mertout.listeners.*;
import org.bukkit.plugin.RegisteredServiceProvider;
import io.github.mertout.commands.TabComplete;
import io.github.mertout.commands.XClaim;
import io.github.mertout.filemanager.MessagesFile;
import io.github.mertout.filemanager.ClaimsFile;
import io.github.mertout.placeholders.Placeholders;
import org.bukkit.Bukkit;
import io.github.mertout.holograms.timer.HologramTimer;
import io.github.mertout.holograms.HologramCore;
import io.github.mertout.core.timer.MoveTimer;
import io.github.mertout.core.ClaimManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import io.github.mertout.core.data.DataHandler;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;

public class Claim extends JavaPlugin
{
    public static Claim instance;
    public final ArrayList<DataHandler> claims;
    public final ArrayList<Player> memberadd;
    private Economy economy;
    private ClaimManager cm;
    private MoveTimer mt;
    private HologramCore hc;

    public Claim() {
        this.claims = new ArrayList<DataHandler>();
        this.memberadd = new ArrayList<Player>();
        this.economy = null;
    }
    
    public void onEnable() {
        Claim.instance = this;
        this.loadClass();
        this.loadEvents();
        this.loadFiles();
        this.checkPlugins();
        this.getClaimManager().loadClaims();
    }
    
    public void loadClass() {
        this.cm = new ClaimManager();
        this.mt = new MoveTimer();
        new HologramTimer();
        this.hc = new HologramCore();
    }
    
    public void onDisable() {
        this.getClaimManager().saveClaims();
    }
    
    private void checkPlugins() {
        hc.installHologramPlugin();
        if (!this.setupEconomy()) {
            System.out.println("Vault not found, Please install Vault!");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders().register();
        }
    }
    
    private void loadFiles() {
        this.saveDefaultConfig();
        ClaimsFile.loadClaimFiles();
        MessagesFile.loadMessagesFiles();
    }
    
    private void loadEvents() {
        this.getServer().getPluginManager().registerEvents(new BlockPlaceEvent(), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this);
        this.getServer().getPluginManager().registerEvents(new BlockClickEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        this.getServer().getPluginManager().registerEvents(new CommandEvent(), this);
        this.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PistonEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ItemFrameEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ArmorStandEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MinecartEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ChangeBlockEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ChunkLoadEvent(), this);
        this.getServer().getPluginManager().registerEvents(new BowEvent(), this);
        this.getCommand("xclaim").setExecutor(new XClaim());
        this.getCommand("xclaim").setTabCompleter(new TabComplete());
    }
    
    public static Claim getInstance() {
        return Claim.instance;
    }
    
    public Economy getEconomy() {
        return this.economy;
    }
    
    public ArrayList<DataHandler> getClaims() {
        return this.claims;
    }
    
    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return false;
        }
        this.economy = (Economy)rsp.getProvider();
        return this.economy != null;
    }
    
    public ClaimManager getClaimManager() {
        return this.cm;
    }
    
    public MoveTimer getMoveTimer() {
        return this.mt;
    }
    
    public HologramCore getHologramCore() {
        return this.hc;
    }
    
    public ArrayList<Player> getMembers() {
        return this.memberadd;
    }

}
