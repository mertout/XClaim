package io.github.mertout.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.Claim;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class DamageEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
            if (super.getChunkClaim(e.getEntity().getLocation()) != null) {
                e.setCancelled(true);
            }
        }
        else if (e.getDamager() instanceof Player && e.getEntity() instanceof Player && super.getChunkClaim(e.getEntity().getLocation()) != null && Claim.getInstance().getClaimManager().getChunkClaim(e.getDamager().getLocation()) != null && !Claim.getInstance().getConfig().getBoolean("settings.pvp-in-claim")) {
            e.getDamager().sendMessage(MessagesFile.convertString("messages.disabled-pvp-in-claim"));
            e.setCancelled(true);
        }
        else if (e.getDamager() instanceof Player) {
            if (super.getChunkClaim(e.getEntity().getLocation()) != null && super.hasClaimAtLocation(e.getEntity().getLocation(), (Player)e.getDamager())) {
                e.setCancelled(true);
            }
        }
    }
}
