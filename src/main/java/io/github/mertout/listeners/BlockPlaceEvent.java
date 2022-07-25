package io.github.mertout.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import io.github.mertout.api.events.ClaimCreateEvent;
import io.github.mertout.filemanager.MessagesFile;
import io.github.mertout.Claim;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class BlockPlaceEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onPlace(final org.bukkit.event.block.BlockPlaceEvent e) {
        if (!super.hasClaimAtLoc(e.getBlock().getLocation(), e.getPlayer())) {
            NBTItem nbti = new NBTItem(e.getItemInHand());
            if (nbti.getBoolean("claimblock")) {
                if (!super.hasClaim(e.getPlayer())) {
                    for (final String str : Claim.getInstance().getConfig().getStringList("settings.disabled-worlds")) {
                        if (e.getPlayer().getWorld().getName().equals(str)) {
                            e.getPlayer().sendMessage(MessagesFile.convertString("messages.disabled-worlds"));
                            e.setCancelled(true);
                            return;
                        }
                    }
                    final ClaimCreateEvent event = new ClaimCreateEvent(e.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        super.registerClaim(e.getBlockPlaced().getLocation(), e.getPlayer());
                        e.getPlayer().sendMessage(MessagesFile.convertString("messages.created-claims"));
                    }
                    else {
                        e.setCancelled(true);
                    }
                }
                else {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(MessagesFile.convertString("messages.already-have-claims"));
                }
            }
        }
        else {
            e.setCancelled(true);
        }
    }
}
