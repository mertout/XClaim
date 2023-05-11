package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ArmorStandEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onItemFrame(final PlayerArmorStandManipulateEvent e) {
        if (super.getChunkClaim(e.getRightClicked().getLocation()) != null && super.hasClaimAtLocation(e.getRightClicked().getLocation(), e.getPlayer())) { e.setCancelled(true); }
    }
}
