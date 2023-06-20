package io.github.mertout.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class PistonEvent extends ClaimManager implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPistonExtendEvent(final BlockPistonExtendEvent e) {
        final Block targetBlock = e.getBlock().getRelative(e.getDirection(), e.getLength() + 1);
        if (super.getChunkClaim(targetBlock.getLocation()) != null && super.getChunkClaim(targetBlock.getLocation()) != super.getChunkClaim(e.getBlock().getLocation())) { e.setCancelled(true); }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPistonRetractEvent(final BlockPistonRetractEvent e) {
        for (Block b : e.getBlocks()) {
            if (super.getChunkClaim(b.getLocation()) != null && super.getChunkClaim(b.getLocation()) != super.getChunkClaim(e.getBlock().getLocation())) { e.setCancelled(true); }
            if (super.getChunkClaim(b.getLocation()) != null && super.getChunkClaim(b.getLocation()).getBlockLocation().toString().equals(b.getLocation().toString())) { e.setCancelled(true); }
        }
        if (super.getChunkClaim(e.getRetractLocation().getBlock().getLocation()) != null && super.getChunkClaim(e.getRetractLocation().getBlock().getLocation()) != super.getChunkClaim(e.getBlock().getLocation())) { e.setCancelled(true); }
    }
}
