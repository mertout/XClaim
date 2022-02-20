package io.github.mertout.core.timer;

import io.github.mertout.Claim;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MoveTimer implements Runnable {

    private static final HashMap<Player, Integer> task = new HashMap<>();

    public void addMoveCooldown(Player p) {
        task.put(p,Claim.getInstance().getConfig().getInt("settings.move-block-cooldown"));
    }
    public MoveTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Claim.getInstance(), this, 0L, 20L);
    }
    @Override
    public void run() {
        if (!task.isEmpty()) {
            if (Bukkit.getOnlinePlayers().size() > 0) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    int time = task.get(p) - 1;
                    task.remove(p);
                    task.put(p,time);
                    if (time == 0) {
                        task.remove(p);
                    }
                }
            }
        }
    }
    public static HashMap getMoveTask() {
        return task;
    }
}
