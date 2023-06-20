package io.github.mertout.core.timer;

import io.github.mertout.Claim;
import org.bukkit.Bukkit;
public class DataTimer implements Runnable {

    public DataTimer() {
        int sec = Claim.getInstance().getConfig().getInt("settings.data-save-time");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Claim.getInstance(), this, 0L, 20L * sec);
    }

    @Override
    public void run() {
        if (!Claim.getInstance().getClaims().isEmpty()) {
            Claim.getInstance().getClaimManager().saveClaims();
        }
    }
}
