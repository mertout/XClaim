package io.github.mertout.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.EventHandler;
import io.github.mertout.Claim;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ExplodeEvent extends ClaimManager implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityExplodeEvent(final EntityExplodeEvent e) {
        if (!Claim.getInstance().getConfig().getBoolean("settings.explosions")) {
            for (Block b : e.blockList()) {
                if (super.getChunkClaim(b.getLocation()) != null) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockExplodeEvent(final BlockExplodeEvent e) {
        if (!Claim.getInstance().getConfig().getBoolean("settings.explosions")) {
            for (Block b : e.blockList()) {
                if (super.getChunkClaim(b.getLocation()) != null) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockBurnEvent(final BlockBurnEvent e) {
        if (super.getChunkClaim(e.getIgnitingBlock().getLocation()) != null) {
            e.setCancelled(true);
        }
    }
}
