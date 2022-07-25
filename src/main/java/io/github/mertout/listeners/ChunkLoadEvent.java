package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ChunkLoadEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onChunkLoad(final PlayerMoveEvent e) {
        if (e.getFrom().getChunk() != e.getTo().getChunk()) {
            if (super.getChunk(e.getTo().getChunk()) != null) {
                if (Claim.getInstance().getConfig().getBoolean("settings.claim-join-alerts")) {
                    if (super.hasClaimAtLoc(e.getTo(), e.getPlayer())) {
                        DataHandler data = super.getChunk(e.getTo().getChunk());
                        String title = MessagesFile.convertString("messages.join-alerts.title").replace("{owner}", data.getOwner());
                        String subtitle = MessagesFile.convertString("messages.join-alerts.sub-title").replace("{owner}", data.getOwner());
                        e.getPlayer().sendTitle(title, subtitle, 1, 20 , 1);
                    }
                }
            }
        }
    }
}