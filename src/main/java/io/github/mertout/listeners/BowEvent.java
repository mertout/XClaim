package io.github.mertout.listeners;

import io.github.mertout.core.ClaimManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class BowEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onBow(PlayerPickupArrowEvent e) {
        if (super.getChunkClaim(e.getPlayer().getLocation().getChunk()) != null && super.hasClaimAtLocation(e.getPlayer().getLocation(), e.getPlayer())) { e.setCancelled(true); }
    }
    @EventHandler
    public void onBow2(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Projectile projectile = e.getEntity();
            Location loc = new Location(projectile.getLocation().getWorld(), projectile.getLocation().getX(), projectile.getLocation().getY(), projectile.getLocation().getZ(), projectile.getLocation().getYaw(), projectile.getLocation().getPitch());
            if (super.getChunkClaim(loc.getChunk()) != null && super.hasClaimAtLocation(loc, (Player) e.getEntity().getShooter())) {
                projectile.remove();
            }
        }
    }
}

