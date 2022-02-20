package io.github.mertout.filemanager;

import io.github.mertout.Claim;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ClaimsFile {

    public static FileConfiguration clfc;
    public static File clfile;

    public static void loadClaimFiles() {
        clfile = new File(Claim.getInstance().getDataFolder(), "claims.yml");
        if (!clfile.exists()) {
            try {
                clfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clfc = YamlConfiguration.loadConfiguration(clfile);
            clfc.createSection("claims");
            try {
                clfc.save(clfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clfc = YamlConfiguration.loadConfiguration(clfile);

    }
    public static FileConfiguration getClaimsFile() {
        return clfc;
    }
    public static void saveClaimsFile() {
        try {
            clfc.save(clfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
