package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ChangeBlockEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onChange(final EntityChangeBlockEvent e) {
        if(e.getEntity() instanceof Player) {
            if (super.getChunk(e.getBlock().getLocation()) != null && super.hasClaimAtLoc(e.getBlock().getLocation(), (Player)e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }
}