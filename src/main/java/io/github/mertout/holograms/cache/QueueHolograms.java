package io.github.mertout.holograms.cache;

import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import io.github.mertout.Claim;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueueHolograms {

    private List<DataHandler> waitingHologramCreating = new ArrayList<>();

    private int task;

    public QueueHolograms() {
        task = Bukkit.getScheduler().runTaskTimer(Claim.getInstance(), this::checkHologramCreation, 40L, 8 * 20L).getTaskId();
    }

    public void addQueueHolograms(DataHandler data) {
        waitingHologramCreating.add(data);

    }
    public void checkHologramCreation() {
        if (waitingHologramCreating.isEmpty()) {
            stopHologramCreationCheck();
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null && !DecentHologramsAPI.isRunning()) {
            return;
        }
        Iterator<DataHandler> iterator = waitingHologramCreating.iterator();
        while (iterator.hasNext()) {
            DataHandler data = iterator.next();
            Claim.getInstance().getHologramCore().createHologram(data.getBlockLocation(), data);
            iterator.remove();
        }
    }
    public void stopHologramCreationCheck() {
        Bukkit.getScheduler().cancelTask(task);
    }
}
