package io.github.mertout.core.timer;

import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import io.github.mertout.Claim;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class MoveTimer implements Runnable
{
    private static final HashMap<Player, Integer> task;
    
    public void addMoveCooldown(final Player p) {
        MoveTimer.task.put(p, Claim.getInstance().getConfig().getInt("settings.move-block-cooldown"));
    }
    
    public MoveTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Claim.getInstance(), (Runnable)this, 0L, 20L);
    }
    
    @Override
    public void run() {
        if (!MoveTimer.task.isEmpty() && Bukkit.getOnlinePlayers().size() > 0) {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                final int time = MoveTimer.task.get(p) - 1;
                MoveTimer.task.remove(p);
                MoveTimer.task.put(p, time);
                if (time == 0) {
                    MoveTimer.task.remove(p);
                }
            }
        }
    }
    
    public static HashMap getMoveTask() {
        return MoveTimer.task;
    }
    
    static {
        task = new HashMap<Player, Integer>();
    }
}
