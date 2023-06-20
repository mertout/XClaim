package io.github.mertout;

import io.github.mertout.borders.BorderCore;
import io.github.mertout.borders.ClaimBorder;
import io.github.mertout.borders.timer.BorderTimer;
import io.github.mertout.commands.CommandManager;
import io.github.mertout.core.MemberManager;
import io.github.mertout.core.backup.BackupCore;
import io.github.mertout.core.backup.timer.BackupTimer;
import io.github.mertout.core.timer.CreateTimer;
import io.github.mertout.core.timer.DataTimer;
import io.github.mertout.filemanager.FileManager;
import io.github.mertout.holograms.cache.QueueHolograms;
import io.github.mertout.hooks.Vault;
import io.github.mertout.listeners.*;
import io.github.mertout.listeners.api.*;
import io.github.mertout.hooks.Metrics;
import io.github.mertout.utils.UpdateChecker;
import io.github.mertout.commands.tabcomplete.TabComplete;
import io.github.mertout.hooks.Placeholders;
import org.bukkit.Bukkit;
import io.github.mertout.holograms.timer.HologramTimer;
import io.github.mertout.holograms.HologramCore;
import io.github.mertout.core.timer.MoveTimer;
import io.github.mertout.core.ClaimManager;
import org.bukkit.entity.Player;
import io.github.mertout.core.data.DataHandler;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

public class Claim extends JavaPlugin
{
    public static Claim instance;
    private final ArrayList<DataHandler> claims = new ArrayList<>();
    private final HashMap<Player, DataHandler> addList = new HashMap<>();
    private final ArrayList<Player> adminbypass = new ArrayList<>();
    private ClaimManager cm;
    private MoveTimer mt;
    private HologramCore hc;
    private MemberManager mn;
    private BorderTimer bt;
    private ClaimBorder cb;
    private BorderCore bc;
    private QueueHolograms qh;
    private CreateTimer ct;
    private BackupCore backupCore;
    private BackupTimer backupTimer;
    private Vault v;

    @Override
    public void onEnable() {
        Claim.instance = this;

        // Loading Class
        this.loadClass();

        //Loading Events
        this.loadEvents();

        //Loading Files
        this.loadFiles();

        //Checking Depends Plugins
        this.checkPlugins();

        //Load Claims
        this.getClaimManager().loadClaims();

        //Check Update
        this.checkUpdate();

        //Bstats
        Metrics metrics = new Metrics(this, 13800);
        metrics.addCustomChart(new Metrics.SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
        metrics.addCustomChart(new Metrics.SingleLineChart("servers", () -> 1));

        //Console Message
        getLogger().info("XClaim Activated! V1.5.2");
        getLogger().info("Thank you for using!");
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

    private void loadClass() {
        this.cm = new ClaimManager();
        new HologramTimer();
        this.hc = new HologramCore();
        this.mn = new MemberManager();
        this.bt = new BorderTimer();
        this.cb = new ClaimBorder();
        this.bc = new BorderCore();
        this.qh = new QueueHolograms();
        this.backupCore = new BackupCore();
        this.backupTimer = new BackupTimer();
        this.v = new Vault();
        if (Claim.getInstance().getConfig().getInt("settings.data-backup-time") > -1) {
            this.backupTimer = new BackupTimer();
        }
        if (Claim.getInstance().getConfig().getInt("settings.data-save-time") > -1) {
            new DataTimer();
        }
        if (Claim.getInstance().getConfig().getInt("settings.move-block-cooldown") > -1) {
            this.mt = new MoveTimer();
        }
        if (Claim.getInstance().getConfig().getInt("settings.claim-create-cooldown") > -1) {
            this.ct = new CreateTimer();
        }
    }
    
    public void onDisable() {
        this.getClaimManager().saveClaims();
    }
    
    private void checkPlugins() {
        this.hc.setupHolograms();
        this.v.setupEconomy();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders().register();
        }
    }

    private boolean isSpigotVersionCompatible() {
        String version = getServer().getVersion();
        String[] versionParts = version.split(" ");
        String spigotVersion = versionParts[1];

        // Spigot sürüm kontrolü
        String[] spigotParts = spigotVersion.split("\\.");
        int major = Integer.parseInt(spigotParts[0]);
        int minor = Integer.parseInt(spigotParts[1]);
        int patch = Integer.parseInt(spigotParts[2]);

        if (major > 1 || (major == 1 && minor > 16) || (major == 1 && minor == 16 && patch >= 5)) {
            return true;
        } else {
            return false;
        }
    }
    
    private void loadFiles() {
        this.saveDefaultConfig();
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

    public ArrayList<DataHandler> getClaims() {
        return this.claims;
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

    public HashMap<Player, DataHandler> getAddList() {
        return addList;
    }

    public ArrayList<Player> getAdminBypassList() {
        return adminbypass;
    }

    public MemberManager getMemberManager() {
        return mn;
    }

    public BorderTimer getBorderTimer() {
        return bt;
    }

    public ClaimBorder getClaimBorder() {
        return cb;
    }

    public BorderCore getBorderCore() {
        return bc;
    }

    public QueueHolograms getQueueHolograms() {
        return qh;
    }

    public CreateTimer getCreateTimer() {
        return ct;
    }

    public BackupCore getBackupCore() {
        return backupCore;
    }

    public BackupTimer getBackupTimer() {
        return backupTimer;
    }

    public Vault getVault() {
        return v;
    }
}
