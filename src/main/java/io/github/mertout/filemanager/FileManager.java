package io.github.mertout.filemanager;

import io.github.mertout.Claim;
import io.github.mertout.filemanager.files.ClaimsFile;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.holograms.file.HologramFile;

import java.io.File;

public class FileManager {

    public FileManager() {
        ClaimsFile.loadClaimFiles();
        MessagesFile.loadMessagesFiles();
        MenusFile.loadMenusFiles();
        HologramFile.loadHologramFiles();
    }

    public static void saveResource(String resourcePath) {
        saveResource(resourcePath, resourcePath);
    }

    public static void saveResource(String destination, String resourcePath) {
        File file = new File(Claim.getInstance().getDataFolder(), resourcePath);
        Claim.getInstance().saveResource(resourcePath, true);
        if (!destination.equals(resourcePath)) {
            File dest = new File(Claim.getInstance().getDataFolder(), destination);
            file.renameTo(dest);
        }
    }
}
