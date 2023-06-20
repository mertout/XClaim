package io.github.mertout.core.backup;

import io.github.mertout.Claim;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BackupCore {

    public BackupCore() {
        File dataFolder = new File(Claim.getInstance().getDataFolder() + "/backups");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }
    public void backupData() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String timestamp = dateFormat.format(new Date());

        File dataFolder = new File(Claim.getInstance().getDataFolder() + "/backups");
        File claimsFile = new File(dataFolder, "data." + timestamp + ".yml");
        if (!claimsFile.exists()) {
            claimsFile.createNewFile();
        }
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(claimsFile);
        long start = System.currentTimeMillis();
        for (DataHandler data : Claim.getInstance().getClaims()) {
            cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".block-location", data.getBlockLocation());
            cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".owner", data.getOwner());
            cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".day", data.getDays());
            cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".hour", data.getHours());
            cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".minutes", data.getMinutes());
            cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".seconds", data.getSeconds());
            cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".creation-date", data.getCreationDate());
            if (data.getMembers() != null) {
                List<String> list = cfg.getStringList("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".members");
                data.getMembers().forEach(mem -> list.add(mem));
                cfg.set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".members", list);
            }
            cfg.save(claimsFile);
        }
        Claim.getInstance().getLogger().info("Backed All Data! " + (System.currentTimeMillis() - start) + "ms");
    }
}
