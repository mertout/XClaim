package io.github.mertout.filemanager.files;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import io.github.mertout.Claim;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class ClaimsFile
{
    public static FileConfiguration clfc;
    public static File clfile;
    
    public static void loadClaimFiles() {
        ClaimsFile.clfile = new File(Claim.getInstance().getDataFolder(), "claims.yml");
        if (!ClaimsFile.clfile.exists()) {
            try {
                ClaimsFile.clfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            (ClaimsFile.clfc = YamlConfiguration.loadConfiguration(ClaimsFile.clfile)).createSection("claims");
            try {
                ClaimsFile.clfc.save(ClaimsFile.clfile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ClaimsFile.clfc = YamlConfiguration.loadConfiguration(ClaimsFile.clfile);
    }
    
    public static FileConfiguration getClaimsFile() {
        return ClaimsFile.clfc;
    }
    
    public static void saveClaimsFile() {
        try {
            ClaimsFile.clfc.save(ClaimsFile.clfile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
