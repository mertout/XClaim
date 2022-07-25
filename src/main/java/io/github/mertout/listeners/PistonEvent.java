package io.github.mertout.listeners;

import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class PistonEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onPistonExtendEvent(final BlockPistonExtendEvent e) {
        final Block targetBlock = e.getBlock().getRelative(e.getDirection(), e.getLength() + 1);
        if (super.getChunk(targetBlock.getLocation()) != null && super.getChunk(targetBlock.getLocation()) != super.getChunk(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPistonRetractEvent(final BlockPistonRetractEvent e) {
        for (Block b : e.getBlocks()) {
            if (super.getChunk(b.getLocation()) != null && super.getChunk(b.getLocation()) != super.getChunk(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
        if (super.getChunk(e.getRetractLocation().getBlock().getLocation()) != null && super.getChunk(e.getRetractLocation().getBlock().getLocation()) != super.getChunk(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }
}
