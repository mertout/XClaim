package io.github.mertout.borders.timer;

import io.github.mertout.Claim;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BorderTimer implements Runnable {

    private static Map<Player, Integer> task = new HashMap<>();
    private final int sec;

    public BorderTimer() {
        Plugin plugin = Claim.getInstance();
        sec = plugin.getConfig().getInt("settings.look-chunk-seconds");
        Bukkit.getScheduler().runTaskTimer(plugin, this, 0L, 20L);
    }

    public void addTimer(@NotNull Player p) {
        Claim.getInstance().getBorderCore().caches.put(p, true);
        task.put(p, sec);
    }
    public void removeTimer(@NotNull Player p) {
        Claim.getInstance().getBorderCore().caches.remove(p);
        task.remove(p);
    }

    @Override
    public void run() {
        for (final Map.Entry<Player, Integer> entry : task.entrySet()) {
            final Player p = entry.getKey();
            final int time = entry.getValue() - 1;
            if (time <= 0) {
                task.remove(p);
                Claim.getInstance().getBorderCore().caches.remove(p);
                Claim.getInstance().getClaimBorder().resetBorder(p);
                p.sendMessage(MessagesFile.convertString("messages.look-chunk-expired").replace("{seconds}", sec + ""));
            } else {
                task.put(p, time);
            }
        }
    }
}
