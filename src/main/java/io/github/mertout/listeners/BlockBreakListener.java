package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;

public class BlockBreakListener extends ClaimManager implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (super.hasClaimAtLoc(e.getBlock().getLocation(),e.getPlayer())) {
            e.setCancelled(true);
        }
        else {
            if (e.getBlock().getType() == Material.BEDROCK) {
                DataHandler data = super.PlayerToClaim(e.getPlayer());
                if (data != null) {
                    if (data.getBlockLocation().equals(e.getBlock().getLocation())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
