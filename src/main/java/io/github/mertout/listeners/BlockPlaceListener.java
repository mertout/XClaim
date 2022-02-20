package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.api.ClaimCreateEvent;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends ClaimManager implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!super.hasClaimAtLoc(e.getBlock().getLocation(), e.getPlayer())) {
            if (e.getBlock().getType() == Material.BEDROCK) {
                if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(Claim.getInstance().getConfig().getString("settings.claim-block-name").replaceAll("&", "§"))) {
                    if (!super.hasClaim(e.getPlayer())) {
                        if (super.PlayerToClaim(e.getPlayer()) == null) {
                            for (String str : Claim.getInstance().getConfig().getStringList("settings.disabled-worlds")) {
                                if (e.getPlayer().getWorld().getName().equals(str)) {
                                    e.getPlayer().sendMessage(MessagesFile.convertString("messages.disabled-worlds"));
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                            ClaimCreateEvent event = new ClaimCreateEvent();
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                super.registerClaim(e.getBlockPlaced().getLocation(), e.getPlayer());
                                e.getPlayer().sendMessage(MessagesFile.convertString("messages.created-claims"));
                            }
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
        }
        else {
            e.setCancelled(true);
        }
    }
}
