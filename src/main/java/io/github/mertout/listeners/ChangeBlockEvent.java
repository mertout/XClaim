package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ChangeBlockEvent extends ClaimManager implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onChange(final EntityChangeBlockEvent e) {
        if(e.getEntity() instanceof Player) {
            if (super.getChunkClaim(e.getBlock().getLocation()) != null && super.hasClaimAtLocation(e.getBlock().getLocation(), (Player)e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }
}
