package io.github.mertout.holograms.file;

import io.github.mertout.Claim;
import io.github.mertout.filemanager.FileManager;
import io.github.mertout.gui.GuiType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class HologramFile {

    public static FileConfiguration fc;
    public static File file;

    public static void loadHologramFiles() {
        file = new File(Claim.getInstance().getDataFolder(), "holograms.yml");
        if (!file.exists()) {
            FileManager.saveResource("holograms.yml");
        }
        fc = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration get() {
        return fc;
    }
    public static void reloadHologramsFile() {
        file = new File(Claim.getInstance().getDataFolder(), "holograms.yml");
        fc = YamlConfiguration.loadConfiguration(file);
    }
}