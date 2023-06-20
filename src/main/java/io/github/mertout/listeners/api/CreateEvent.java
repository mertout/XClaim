package io.github.mertout.listeners.api;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimCreateEvent;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CreateEvent implements Listener {

    @EventHandler
    public void on(ClaimCreateEvent e) {
        Player p = e.getPlayer();
        if (!Claim.getInstance().getClaimManager().canCreateClaim(p)) {
            p.sendMessage(MessagesFile.convertString("messages.claim-create-max")
                    .replace("{max}", Claim.getInstance().getClaimManager().getMaxClaimsSize(p) + ""));
            e.setCancelled(true);
            return;
        }
        if (Claim.getInstance().getCreateTimer().getTask().containsKey(p) && !p.hasPermission("xclaim.bypass.cooldown")) {
            int hour = 0;
            int minute = 0;
            int second;
            for (second = Claim.getInstance().getCreateTimer().getTask().get(p); second > 3600; second -= 3600, ++hour) {}
            while (second > 60) {
                second -= 60;
                ++minute;
            }
            p.sendMessage(MessagesFile.convertString("messages.claim-create-cooldown").replace("{hour}", hour + "").replace("{minute}", minute + "").replace("{second}", second + ""));
            e.setCancelled(true);
            return;
        }
        else{
            Claim.getInstance().getCreateTimer().addTimer(p);
        }
        if (e.isCancelled() || !Claim.getInstance().getConfig().getBoolean("settings.sounds.CLAIM-CREATE.enabled")) {
            return;
        }
        String sound = Claim.getInstance().getConfig().getString("settings.sounds.CLAIM-CREATE.type").toUpperCase();
        Float volume = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.CLAIM-CREATE.volume");
        Float pitch = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.CLAIM-CREATE.pitch");
        p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
    }
}
