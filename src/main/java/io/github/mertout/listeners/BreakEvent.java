package io.github.mertout.listeners;

import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.event.EventHandler;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (super.hasClaimAtLocation(e.getBlock().getLocation(), e.getPlayer())) {
            if (e.getPlayer().hasPermission("xclaim.bypass") || e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(MessagesFile.convertString("messages.admin.player-bypass-mode"));
            }
            e.setCancelled(true);
        }
        else {
            final DataHandler data = super.getChunkClaim(e.getBlock().getChunk());
            if (data != null && data.getBlockLocation().equals(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
    }
}
