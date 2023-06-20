package io.github.mertout.core.backup.timer;

import io.github.mertout.Claim;
import org.bukkit.Bukkit;

import java.io.IOException;

public class BackupTimer implements Runnable {

    public BackupTimer() {
        int sec = Claim.getInstance().getConfig().getInt("settings.data-backup-time");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Claim.getInstance(), this, 0L, 20L * sec);
    }

    @Override
    public void run() {
        if (!Claim.getInstance().getClaims().isEmpty()) {
            try {
                Claim.getInstance().getBackupCore().backupData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
