package io.github.mertout.listeners.api;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimMemberRemoveEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MemberRemoveEvent implements Listener {

    @EventHandler
    public void on(ClaimMemberRemoveEvent e) {
        if (e.isCancelled() || !Claim.getInstance().getConfig().getBoolean("settings.sounds.MEMBER-REMOVE.enabled")) {
            return;
        }
        Player p = e.getPlayer();
        String sound = Claim.getInstance().getConfig().getString("settings.sounds.MEMBER-REMOVE.type").toUpperCase();
        Float volume = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.MEMBER-REMOVE.volume");
        Float pitch = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.MEMBER-REMOVE.pitch");
        p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
    }
}
