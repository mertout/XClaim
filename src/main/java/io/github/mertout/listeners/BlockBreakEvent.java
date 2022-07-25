// 
// Decompiled by Procyon v0.5.36
// 

package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class BlockBreakEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onBreak(final org.bukkit.event.block.BlockBreakEvent e) {
        if (super.hasClaimAtLoc(e.getBlock().getLocation(), e.getPlayer())) {
            e.setCancelled(true);
        }
        else {
            final DataHandler data = super.getChunk(e.getBlock().getChunk());
            if (data != null && data.getBlockLocation().equals(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
    }
}
