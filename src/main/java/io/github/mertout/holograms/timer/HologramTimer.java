package io.github.mertout.holograms.timer;

import io.github.mertout.holograms.HologramCore;
import org.bukkit.plugin.Plugin;
import io.github.mertout.Claim;
import org.bukkit.Bukkit;

public class HologramTimer implements Runnable
{
    public HologramTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Claim.getInstance(), this, 40L, 20L);
    }
    
    @Override
    public void run() {
        new HologramCore().updateHolograms();
    }
}
