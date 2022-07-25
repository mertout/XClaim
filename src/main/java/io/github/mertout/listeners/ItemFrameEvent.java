package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ItemFrameEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onItemFrame(final HangingBreakByEntityEvent e) {
        if (super.getChunk(e.getEntity().getLocation()) != null && super.hasClaimAtLoc(e.getEntity().getLocation(), (Player)e.getRemover())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemFrame2(final HangingPlaceEvent e) {
        if (super.getChunk(e.getEntity().getLocation()) != null && super.hasClaimAtLoc(e.getEntity().getLocation(), e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemFrame3(final PlayerInteractEntityEvent e) {
        if (super.getChunk(e.getRightClicked().getLocation()) != null && super.hasClaimAtLoc(e.getRightClicked().getLocation(), (Player)e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
