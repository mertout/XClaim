package io.github.mertout.holograms.timer;

import io.github.mertout.Claim;
import io.github.mertout.holograms.HologramCore;
import org.bukkit.Bukkit;

public class HologramTimer implements Runnable {

    public HologramTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Claim.getInstance(), this, 0L, 20L);
    }
    @Override
    public void run() {
        new HologramCore().updateHolograms();
    }
}
