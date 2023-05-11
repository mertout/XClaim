package io.github.mertout.core.data.task;

import io.github.mertout.Claim;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
public class DataTimer implements Runnable {
    public DataTimer(int tick) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Claim.getInstance(), this, 0L, tick);
    }

    @Override
    public void run() {
        if (!Claim.getInstance().getClaims().isEmpty()) {
            Claim.getInstance().getClaimManager().saveClaims();
        }
    }
}
