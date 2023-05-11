package io.github.mertout;

import io.github.mertout.commands.CommandManager;
import io.github.mertout.core.MemberManager;
import io.github.mertout.core.data.task.DataTimer;
import io.github.mertout.filemanager.FileManager;
import io.github.mertout.listeners.*;
import io.github.mertout.listeners.api.*;
import io.github.mertout.utils.Metrics;
import io.github.mertout.utils.UpdateChecker;
import org.bukkit.plugin.RegisteredServiceProvider;
import io.github.mertout.commands.tabcomplete.TabComplete;
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
    public final ArrayList<DataHandler> claims = new ArrayList<>();
    public final ArrayList<Player> addList = new ArrayList<>();
    public final ArrayList<Player> adminbypass = new ArrayList<>();
    private Economy economy;
    private ClaimManager cm;
    private MoveTimer mt;
    private HologramCore hc;
    private MemberManager mn;

    public void onEnable() {
        Claim.instance = this;
        this.loadClass();
        this.loadEvents();
        this.loadFiles();
        this.checkPlugins();
        try {
            this.getClaimManager().loadClaims();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        checkUpdate();
        Metrics metrics = new Metrics(this, 13800);
        metrics.addCustomChart(new Metrics.SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
        metrics.addCustomChart(new Metrics.SingleLineChart("servers", () -> 1));
    }

    private void checkUpdate() {
        if (getConfig().getBoolean("settings.update-checker"))
            (new UpdateChecker(98880)).getVersion(version -> {
                if (getDescription().getVersion().equals(version)) {
                    getLogger().info("There is not a new update available.");
                } else {
                    getLogger().info("There is a new update available.");
                }
            });
    }

    public void loadClass() {
        this.cm = new ClaimManager();
        this.mt = new MoveTimer();
        new HologramTimer();
        this.hc = new HologramCore();
        this.mn = new MemberManager();
    }
    
    public void onDisable() {
        this.getClaimManager().saveClaims();
    }
    
    private void checkPlugins() {
        this.hc.setupHolograms();
        if (!this.setupEconomy()) {
            this.getLogger().warning("Vault not found, Please install Vault!");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders().register();
        }
    }
    
    private void loadFiles() {
        this.saveDefaultConfig();
        if (Claim.getInstance().getConfig().getInt("settings.data-save-tick") > -1) {
            new DataTimer(Claim.getInstance().getConfig().getInt("settings.data-save-tick"));
        }
        new FileManager();
    }
    
    private void loadEvents() {
        this.getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        this.getServer().getPluginManager().registerEvents(new BreakEvent(), this);
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
        this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new BlockMoveEvent(), this);
        this.getServer().getPluginManager().registerEvents(new DeleteEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MemberAddEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MemberRemoveEvent(), this);
        this.getServer().getPluginManager().registerEvents(new RenewDayEvent(), this);
        this.getServer().getPluginManager().registerEvents(new CreateEvent(), this);
        this.getCommand("xclaim").setExecutor(new CommandManager());
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
        this.economy = rsp.getProvider();
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
    
    public ArrayList<Player> getAddList() {
        return this.addList;
    }
    public ArrayList<Player> getAdminBypassList() {
        return adminbypass;
    }

    public MemberManager getMemberManager() {
        return mn;
    }
}
