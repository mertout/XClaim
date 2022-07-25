// 
// Decompiled by Procyon v0.5.36
// 

package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ArmorStandEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onItemFrame(final PlayerArmorStandManipulateEvent e) {
        if (super.getChunk(e.getRightClicked().getLocation()) != null && super.hasClaimAtLoc(e.getRightClicked().getLocation(), e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
