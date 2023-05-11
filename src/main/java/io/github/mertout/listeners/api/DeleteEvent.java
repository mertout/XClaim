package io.github.mertout.listeners.api;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimDeleteEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DeleteEvent implements Listener {

    @EventHandler
    public void on(ClaimDeleteEvent e) {
        if (e.isCancelled() || !Claim.getInstance().getConfig().getBoolean("settings.sounds.CLAIM-DELETE.enabled")) {
            return;
        }
        Player p = e.getPlayer();
        String sound = Claim.getInstance().getConfig().getString("settings.sounds.CLAIM-DELETE.type").toUpperCase();
        Float volume = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.CLAIM-DELETE.volume");
        Float pitch = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.CLAIM-DELETE.pitch");
        p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
    }
}
