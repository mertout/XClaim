package io.github.mertout.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.EventHandler;
import io.github.mertout.Claim;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ExplodeEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onEntityExplodeEvent(final EntityExplodeEvent e) {
        if (!Claim.getInstance().getConfig().getBoolean("settings.explosions")) {
            for (Block b : e.blockList()) {
                if (super.getChunk(b.getLocation()) != null) {
                    e.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockExplodeEvent(final BlockExplodeEvent e) {
        if (!Claim.getInstance().getConfig().getBoolean("settings.explosions")) {
            for (Block b : e.blockList()) {
                if (super.getChunk(b.getLocation()) != null) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onBlockIgniteEvent(final BlockIgniteEvent e) {
        if (super.getChunk(e.getIgnitingBlock().getLocation()) != null && super.hasClaimAtLoc(e.getIgnitingBlock().getLocation(), (Player) e.getIgnitingEntity())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockBurnEvent(final BlockBurnEvent e) {
        if (super.getChunk(e.getIgnitingBlock().getLocation()) != null) {
            e.setCancelled(true);
        }
    }
}
