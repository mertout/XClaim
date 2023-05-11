package io.github.mertout.listeners.api;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimMemberAddEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MemberAddEvent implements Listener {

    @EventHandler
    public void on(ClaimMemberAddEvent e) {
        if (e.isCancelled() || !Claim.getInstance().getConfig().getBoolean("settings.sounds.MEMBER-ADD.enabled")) {
            return;
        }
        Player p = e.getPlayer();
        String sound = Claim.getInstance().getConfig().getString("settings.sounds.MEMBER-ADD.type").toUpperCase();
        Float volume = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.MEMBER-ADD.volume");
        Float pitch = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.MEMBER-ADD.pitch");
        p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
    }
}
