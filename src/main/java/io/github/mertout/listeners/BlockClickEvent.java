package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.gui.GuiCreator;
import io.github.mertout.gui.GuiType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class BlockClickEvent extends ClaimManager implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (super.hasClaimAtLocation(e.getClickedBlock().getLocation(), e.getPlayer())) {
                if (e.getPlayer().hasPermission("xclaim.bypass")) {
                    e.getPlayer().sendMessage(MessagesFile.convertString("messages.admin.player-bypass-mode"));
                }
                e.setCancelled(true);
                return;
            }
            final DataHandler data = super.getChunkClaim(e.getClickedBlock().getLocation());
            Player p = e.getPlayer();
            if (data != null && data.getBlockLocation().equals(e.getClickedBlock().getLocation())) {
                if (!data.getOwner().equals(p.getName())) {
                    e.setCancelled(true);
                    return;
                }
                new GuiCreator(p, GuiType.CLAIMMANAGEMENT);
                if (!Claim.getInstance().getConfig().getBoolean("settings.sounds.OPEN-MANAGAMENT.enabled")) {
                    return;
                }
                String sound = Claim.getInstance().getConfig().getString("settings.sounds.OPEN-MANAGAMENT.type").toUpperCase();
                Float volume = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.OPEN-MANAGAMENT.volume");
                Float pitch = (float) Claim.getInstance().getConfig().getDouble("settings.sounds.OPEN-MANAGAMENT.pitch");
                p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
            }
        }
    }
}
