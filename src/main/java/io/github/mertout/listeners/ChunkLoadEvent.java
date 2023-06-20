package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimEnterEvent;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class ChunkLoadEvent extends ClaimManager implements Listener
{
    private HashMap<Player, String> playersInChunk = new HashMap<>();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onChunkLoad(PlayerMoveEvent e) {
        if (e.getFrom().getChunk() != e.getTo().getChunk() && super.getChunkClaim(e.getTo().getChunk()) != null && Claim.getInstance().getConfig().getBoolean("settings.claim-join-alerts") && super.hasClaimAtLocation(e.getTo(), e.getPlayer())) {
            DataHandler data = super.getChunkClaim(e.getTo().getChunk());
            if (playersInChunk.get(e.getPlayer()) != data.getOwner()) {
                playersInChunk.remove(e.getPlayer());
                playersInChunk.put(e.getPlayer(), data.getOwner());
                ClaimEnterEvent event = new ClaimEnterEvent(data, e.getPlayer());
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    String title = MessagesFile.convertString("messages.join-alerts.title").replace("{owner}", data.getOwner().toString());
                    String subtitle = MessagesFile.convertString("messages.join-alerts.sub-title").replace("{owner}", data.getOwner().toString());
                    e.getPlayer().sendTitle(title, subtitle, 20, 20 , 20);
                }
                else {
                    e.setCancelled(true);
                }
            }
        }
    }
}