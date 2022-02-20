package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {
                if (super.getChunk(e.getDamager().getLocation()) != null && Claim.getInstance().getClaimManager().getChunk(e.getEntity().getLocation()) != null) {
                    if (!Claim.getInstance().getConfig().getBoolean("settings.pvp-in-claim")) {
                        e.getDamager().sendMessage(MessagesFile.convertString("messages.disabled-pvp-in-claim"));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
