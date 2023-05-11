package io.github.mertout.filemanager.files;

import io.github.mertout.Claim;
import io.github.mertout.filemanager.FileManager;
import io.github.mertout.gui.GuiType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MenusFile {
    public static FileConfiguration fc;
    public static File file;

    public static void loadMenusFiles() {
        file = new File(Claim.getInstance().getDataFolder(), "menus/claim-management.yml");
        if (!file.exists()) {
            FileManager.saveResource("menus/claim-management.yml");
        }
        file = new File(Claim.getInstance().getDataFolder(), "menus/members.yml");
        if (!file.exists()) {
            FileManager.saveResource("menus/members.yml");
        }
        fc = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(GuiType guiType) {
        FileConfiguration result = null;
        for (File file : new File(Claim.getInstance().getDataFolder(), "menus").listFiles()) {
            if (guiType == GuiType.CLAIMMANAGEMENT && file.getName().equals("claim-management.yml")) {
                fc = YamlConfiguration.loadConfiguration(file);
                result = fc;
            }
            else if (guiType == GuiType.MEMBERS && file.getName().equals("members.yml")) {
                fc = YamlConfiguration.loadConfiguration(file);
                result = fc;
            }
        }
        return result;
    }
    public static void reloadMenusFiles() {
        for (File file : new File(Claim.getInstance().getDataFolder(), "menus").listFiles()) {
            fc = YamlConfiguration.loadConfiguration(file);
        }
    }
}