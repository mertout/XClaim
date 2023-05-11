package io.github.mertout.listeners.api;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimDayRenewEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RenewDayEvent implements Listener {

    @EventHandler
    public void on(ClaimDayRenewEvent e) {
        if (e.isCancelled() || !Claim.getInstance().getConfig().getBoolean("settings.sounds.RENEW-DAY.enabled")) {
            return;
        }
        Player p = e.getPlayer();
        String sound = Claim.getInstance().getConfig().getString("settings.sounds.RENEW-DAY.type").toUpperCase();
        Float volume = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.RENEW-DAY.volume");
        Float pitch = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.RENEW-DAY.pitch");
        p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
    }
}
